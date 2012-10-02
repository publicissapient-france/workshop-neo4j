package fr.xebia.xke.neo4j;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import fr.xebia.xke.neo4j.domains.relations.RelTypes;
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

import java.text.SimpleDateFormat;
import java.util.*;

public class BiDAO {

    protected GraphDatabaseService graphDb;

    public BiDAO(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    public List<String> getRecommendedProductsFor(String productName) {
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        Map params = ImmutableMap.of("productName", productName);
        ExecutionResult result = engine.execute("start product=node:node_auto_index(name={productName})\n" +
                "MATCH product<-[:CONTAINS]-shoppingCart-[:CONTAINS]->recommendedProducts\n" +
                "WHERE not (product = recommendedProducts)\n" +
                "RETURN recommendedProducts", params);

        List<String> recommendedProducts = Lists.newArrayList();
        Iterator<Node> recommendedProductsColumn = result.columnAs("recommendedProducts");
        for (Node node : IteratorUtil.asIterable(recommendedProductsColumn)) {
            recommendedProducts.add((String) node.getProperty("name"));
        }
        return recommendedProducts;
    }


    public int getNbSell(String productName, String color, Date date) {
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy");
        Map params = ImmutableMap.of("productName", productName,
                                    "date", "Date"+sdf.format(date),
                                    "colorName", color);

        ExecutionResult result = engine.execute("start date=node:node_auto_index(name={date}), " +
                "product=node:node_auto_index(name={productName}), " +
                "color=node:node_auto_index(name={colorName}) " +
                "MATCH date<-[:DATE]-sc-[:CONTAINS]->product-[:COLOR]->color " +
                "RETURN count(*)", params);

        int nbProducts = Integer.parseInt(result.iterator().next().get("count(*)").toString());
        return nbProducts;
    }

    public List<String> getSponsored(String clientName) {
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
