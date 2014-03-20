package fr.xebia.xke.neo4j;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import fr.xebia.xke.neo4j.relations.RelTypes;
import org.apache.commons.lang.time.DateFormatUtils;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.ReadableIndex;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.Traverser;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * *******************************************
 * Complete each method of this class in order to pass the tests
 * ********************************************
 */

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
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        Map params = ImmutableMap.of("shoppingCartName", shoppingCartName);
        ExecutionResult result = engine.execute(
                "MATCH (shoppingCart:ShoppingCart)-[:CONTAINS]->product " +
                        "WHERE shoppingCart.name={shoppingCartName} " +
                        "RETURN product.name as productName", params
        );

        products = Lists.newArrayList(result.<String>columnAs("productName"));
        return products;
    }

    /**
     * @param productName The name of the products for which you want recommendations
     * @return The names of the recommended products
     */
    public List<String> getRecommendedProductsFor(String productName) {
        List<String> recommendedProducts;
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        Map params = ImmutableMap.of("productName", productName);
        ExecutionResult result = engine.execute(
                "MATCH (product:Product)<-[:CONTAINS]-(shoppingCart:ShoppingCart)-[:CONTAINS]->(recommendedProducts:Product) " +
                        "WHERE product.name = {productName}" +
                        "RETURN recommendedProducts.name as recommendedProductsName", params
        );
        recommendedProducts = Lists.newArrayList(result.<String>columnAs("recommendedProductsName"));
        return recommendedProducts;
    }

    /**
     * @param clientName The names of the client for whom you want to know the sponsored clients recursively
     * @return Names of the sponsored clients
     */
    public List<String> getRecursiveSponsoredClient(String clientName) {
        List<String> sponsored = Lists.newArrayList();

        try (Transaction tx = graphDb.beginTx()) {
            ResourceIterable<Node> nodes = graphDb.findNodesByLabelAndProperty(DynamicLabel.label("Client"), "name", clientName);
            Node client = Iterables.getOnlyElement(nodes);
            Traverser traverser = graphDb.traversalDescription()
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
        Map params = ImmutableMap.of("productName", productName, "formattedDate", formattedDate);

        ExecutionResult result = engine.execute(
                "MATCH (date:Date)<-[:DATE]-shoppingCart-[:CONTAINS]->(product:Product) " +
                        "WHERE date.name={formattedDate} AND product.name={productName}" +
                        "RETURN count(distinct shoppingCart) as shoppingCartCount", params
        );

        return Integer.parseInt(Iterables.getOnlyElement(result).get("shoppingCartCount").toString());
    }

    /**
     * @param productName The name of the product for which we want the fasted way to buy it
     * @return The list of categories than we must cross to see the product
     */
    public List<String> getTheShortestPathToBuy(String productName) {
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        List<List<String>> paths;
        Map params = ImmutableMap.of("productName", productName);

        ExecutionResult result = engine.execute(
                "MATCH p=shortestPath((root:Category{name:'Racine'})-[:SUB_CAT*]->(bag:Product{name:{productName} }))\n" +
                        "RETURN extract(n IN nodes(p) | n.name) as categoriesName", params
        );

        paths = Lists.newArrayList(result.<List<String>>columnAs("categoriesName"));
        return paths.get(0);
    }
}
