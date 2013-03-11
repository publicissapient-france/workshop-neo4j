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
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.Traversal;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GraphDAO {

    protected GraphDatabaseService graphDb;

    public GraphDAO(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    /**
     * @param productName Nom du produit pour lequel on veut des recommandations
     * @return La liste des noms de produit recommandés
     */
    public List<String> getRecommendedProductsFor(String productName) {
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        Map params = ImmutableMap.of("productName", productName);
        ExecutionResult result = engine.execute("start product=node:node_auto_index(name={productName}) " +
                "MATCH product<-[:CONTAINS]-shoppingCart-[:CONTAINS]->recommendedProducts " +
                "WHERE not (product = recommendedProducts) " +
                "RETURN recommendedProducts", params);

        List<String> recommendedProducts = Lists.newArrayList();
        Iterator<Node> recommendedProductsColumn = result.columnAs("recommendedProducts");
        for (Node node : IteratorUtil.asIterable(recommendedProductsColumn)) {
            recommendedProducts.add((String) node.getProperty("name"));
        }
        return recommendedProducts;

    }

    /**
     * @param clientName Nom du client pour lequel on veut connaitre les fieulles de façon récusive
     * @return Les noms de tous les fieulles
     */
    public List<String> getRecursiveSponsoredClient(String clientName) {
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        Map params = ImmutableMap.of("clientName", clientName);
        ExecutionResult result = engine.execute("start client=node:node_auto_index(name={clientName}) RETURN client", params);
        Node client = (Node) IteratorUtil.asIterable(result.columnAs("client")).iterator().next();

        Traverser traverser = Traversal.description()
                .depthFirst()//
                .relationships(RelTypes.HAS_SPONSORED, Direction.OUTGOING)//
                .evaluator(Evaluators.excludeStartPosition())//
                .traverse(client);

        List<String> sponsored = Lists.newArrayList();
        for (Path path : traverser) {
            sponsored.add(path.endNode().getProperty("name").toString());
        }
        return sponsored;
    }

    /**
     * @param existingClientName Le nom du client existant qui veut parrainer le filleul
     * @param sponsoredClientName Le nom du filleul qui est créé
     */
    public void addNewSponsoredClient(String existingClientName, String sponsoredClientName) {
        Transaction tx = graphDb.beginTx();
        try {
            ReadableIndex<Node> index = graphDb.index()
                    .getNodeAutoIndexer()
                    .getAutoIndex();

            Node clientNode = index.get("name", existingClientName).getSingle();

            Node sponsoredNode = graphDb.createNode();
            sponsoredNode.setProperty("name", sponsoredClientName);

            clientNode.createRelationshipTo(sponsoredNode, RelTypes.HAS_SPONSORED);

            tx.success();
        } catch (Exception e) {
            tx.failure();
        } finally {
            tx.finish();
        }
    }

    /**
     * @param productName Nom du produit à compter
     * @param date        Date à la qu'elle le produit a été acheté
     * @return nombre de vente du produit, de cette couleur à cette date
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
