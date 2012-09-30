package fr.xebia.xke.neo4j;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import fr.xebia.xke.neo4j.domains.nodes.Product;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.helpers.collection.IteratorUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BiDAO {

    protected GraphDatabaseService graphDb;

    public BiDAO (GraphDatabaseService graphDb){
        this.graphDb = graphDb;
    }

    public List<String> getRecommendedProductsFor(String shoppingCartName){
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        Map params = ImmutableMap.of("shoppingCartName", shoppingCartName);
        ExecutionResult result = engine.execute("start sc=node:node_auto_index(name='{shoppingCartName}') " +
                "MATCH sc-[:contient]->prod1<-[:contient]-otherSc-[:contient]->prod2 " +
                "WHERE not (prod1 = prod2) AND not (sc-[:contient]->prod2) " +
                "RETURN prod2.name", params);

        List<String> recommendedProducts = Lists.newArrayList();
        Iterator<Node> n_column = result.columnAs( "n" );
        for ( Node node : IteratorUtil.asIterable(n_column) ){
            recommendedProducts.add((String)node.getProperty("name"));
        }
        return recommendedProducts;
    }
}
