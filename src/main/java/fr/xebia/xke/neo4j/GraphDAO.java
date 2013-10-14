package fr.xebia.xke.neo4j;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.time.DateFormatUtils;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.ReadableIndex;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.Traversal;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import fr.xebia.xke.neo4j.relations.RelTypes;

/**********************************************
 * Complete each method of this class in order to pass the tests
 **********************************************/

public class GraphDAO {

    protected GraphDatabaseService graphDb;

    public GraphDAO(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    /**
     * @param shoppingCartName The name of the shopping cart which products you want
     * @return Products names
     */
    public List<String> getProductsFor(String shoppingCartName) {
        List<String> products;

        try (Transaction tx = graphDb.beginTx()) {
            ExecutionEngine engine = new ExecutionEngine(graphDb);
            Map params = ImmutableMap.of("shoppingCartName", shoppingCartName);
            ExecutionResult result = engine.execute(
                    "MATCH shoppingCart:ShoppingCart-[:CONTAINS]->product " +
                            "WHERE shoppingCart.name={shoppingCartName} " +
                            "RETURN product.name as productName", params);

            products = Lists.newArrayList(result.<String>columnAs("productName"));

            tx.success();
        }
        return products;
    }

    /**
     * @param productName The name of the products for which you want recommendations
     * @return The names of the recommended products
     */
    public List<String> getRecommendedProductsFor(String productName) {
        List<String> recommendedProducts;
        try (Transaction tx = graphDb.beginTx()) {
            ExecutionEngine engine = new ExecutionEngine(graphDb);
            Map params = ImmutableMap.of("productName", productName);
            ExecutionResult result = engine.execute(
                    "MATCH product:Product<-[:CONTAINS]-shoppingCart:ShoppingCart-[:CONTAINS]->recommendedProducts:Product " +
                    "WHERE product.name = {productName} AND product <> recommendedProducts " +
                    "RETURN recommendedProducts.name as recommendedProductsName", params);

            recommendedProducts = Lists.newArrayList(result.<String>columnAs("recommendedProductsName"));

            tx.success();

        }
        return recommendedProducts;
    }

    /**
     * @param clientName The names of the client for whom you want to know the sponsored clients recursively
     * @return Names of the sponsored clients
     */
    public List<String> getRecursiveSponsoredClient(String clientName) {
        List<String> sponsored = Lists.newArrayList();

        try (Transaction tx = graphDb.beginTx()) {
            ExecutionEngine engine = new ExecutionEngine(graphDb);
            Map params = ImmutableMap.of("clientName", clientName);
            ExecutionResult result = engine.execute("start client=node:node_auto_index(name={clientName}) RETURN client", params);
            Node client = (Node) IteratorUtil.asIterable(result.columnAs("client")).iterator().next();
            Traverser traverser = Traversal.description()
                    .depthFirst()//
                    .relationships(RelTypes.HAS_SPONSORED, Direction.OUTGOING)//
                    .evaluator(Evaluators.excludeStartPosition())//
                    .traverse(client);

            for (Path path : traverser) {
                sponsored.add(path.endNode().getProperty("name").toString());
            }
            tx.success();
        }
        return sponsored;
    }

    /**
     * @param existingClientName  The name of the client who wants to sponsor the new one
     * @param sponsoredClientName The name of the new sponsored client
     */
    public void addNewSponsoredClient(String existingClientName, String sponsoredClientName) {
        try (Transaction tx = graphDb.beginTx()) {
            ReadableIndex<Node> index = graphDb.index()
                    .getNodeAutoIndexer()
                    .getAutoIndex();

            Node clientNode = index.get("name", existingClientName).getSingle();

            Node sponsoredNode = graphDb.createNode();
            sponsoredNode.setProperty("name", sponsoredClientName);

            clientNode.createRelationshipTo(sponsoredNode, RelTypes.HAS_SPONSORED);

            tx.success();
        }
    }

    /**
     * @param productName The name of the product for which we wantes the number of sales
     * @param date        The sales date
     * @return The number of product sales
     */
    public int getNumberOfSales(String productName, Date date) {
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        String formattedDate = "Date" + DateFormatUtils.format(date, "dd_MM_yyyy");
        Map params = ImmutableMap.of("productName", productName,
                "formattedDate", formattedDate);

        ExecutionResult result = engine.execute("start date=node:node_auto_index(name={formattedDate}), " +
                "product=node:node_auto_index(name={productName}) " +
                "MATCH date<-[:DATE]-shoppingCart-[:CONTAINS]->product " +
                "RETURN count(distinct shoppingCart) as shoppingCartCount", params);

        return Integer.parseInt(Iterables.getOnlyElement(result).get("shoppingCartCount").toString());
    }
}
