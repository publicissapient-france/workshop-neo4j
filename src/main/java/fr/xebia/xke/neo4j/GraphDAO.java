package fr.xebia.xke.neo4j;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import fr.xebia.xke.neo4j.domains.relations.RelTypes;
import org.apache.commons.lang.time.DateFormatUtils;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.helpers.collection.IteratorUtil;
import org.neo4j.kernel.Traversal;

import java.util.*;

public class GraphDAO {

    protected GraphDatabaseService graphDb;

    public GraphDAO(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    /**
     * @param productName Nom du produit du quel on veut les recommandations
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
     * @param productName Nom du produit à compter
     * @param color Couleur du produit à compter
     * @param date Date à la qu'elle le produit à été acheté
     * @return nombre de vente du produit, de cette couleur à cette date
     */
    public int getNumberOfSales(String productName, String color, Date date) {
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        String formattedDate = "Date"+DateFormatUtils.format(date, "dd_MM_yyyy");
        Map params = ImmutableMap.of("productName", productName,
                                    "date", formattedDate,
                                    "colorName", color);

        ExecutionResult result = engine.execute("start date=node:node_auto_index(name={date}), " +
                "product=node:node_auto_index(name={productName}), " +
                "color=node:node_auto_index(name={colorName}) " +
                "MATCH date<-[:DATE]-sc-[:CONTAINS]->product-[:COLOR]->color " +
                "RETURN count(*)", params);

        return Integer.parseInt(Iterables.getOnlyElement(result).get("count(*)").toString());
    }

    /**
     *
     * @param clientName Nom du client à qui on veut connaitre les fieulles de façon récusive
     * @return Les noms de tous les fieulles
     */
    public List<String> getRecursiveSponsoredClient(String clientName) {
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        Map params = ImmutableMap.of("clientName", clientName);
        ExecutionResult result = engine.execute("start client=node:node_auto_index(name={clientName}) RETURN client", params);
        Node client = (Node) IteratorUtil.asIterable(result.columnAs("client")).iterator().next();

        Traverser traverser = Traversal.description()
                .depthFirst()//
                .relationships(RelTypes.SPONSORED, Direction.OUTGOING)//
                .evaluator(Evaluators.excludeStartPosition())//
                .traverse(client);

        List<String> sponsored = Lists.newArrayList();
        for (Path path : traverser){
            sponsored.add(path.endNode().getProperty("name").toString());
        }
        return sponsored;
    }
}
