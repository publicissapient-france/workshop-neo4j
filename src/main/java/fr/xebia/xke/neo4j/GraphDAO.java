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

import java.util.*;

public class GraphDAO {

    protected GraphDatabaseService graphDb;

    public GraphDAO(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    /**
     * @param shoppingCartName Nom du panier pour le quel on veut des produits
     * @return La liste des noms de produit de ce panier
     */
    public List<String> getProductsFor(String shoppingCartName) {
        List<String> products = Lists.newArrayList();
        return products;
    }

    /**
     * @param productName Nom du produit pour lequel on veut des recommandations
     * @return La liste des noms de produit recommandés
     */
    public List<String> getRecommendedProductsFor(String productName) {
       List<String> recommendedProducts = Lists.newArrayList();
       return recommendedProducts;
    }

    /**
     * @param clientName Nom du client pour lequel on veut connaitre les fieulles de façon récusive
     * @return Les noms de tous les fieulles
     */
    public List<String> getRecursiveSponsoredClient(String clientName) {
        ExecutionEngine engine = new ExecutionEngine(graphDb);
        List<String> sponsored = Lists.newArrayList();
        return sponsored;
    }

    /**
     * @param existingClientName Le nom du client existant qui veut parrainer le filleul
     * @param sponsoredClientName Le nom du filleul qui est créé
     */
    public void addNewSponsoredClient(String existingClientName, String sponsoredClientName) {
    }

    /**
     * @param productName Nom du produit à compter
     * @param date        Date à la qu'elle le produit a été acheté
     * @return nombre de vente du produit à cette date
     */

    public int getNumberOfSales(String productName, Date date) {
        return 0;
    }
}
