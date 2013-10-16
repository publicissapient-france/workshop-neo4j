package fr.xebia.xke.neo4j;

import com.google.common.collect.Lists;
import org.neo4j.graphdb.GraphDatabaseService;

import java.util.Date;
import java.util.List;

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
     * Make pass the test givenShoppingCartName_testProductFor
     * @param shoppingCartName The name of the shopping cart which products you want
     * @return Products names
     */
    public List<String> getProductsFor(String shoppingCartName) {
        List<String> products = Lists.newArrayList();
        // Your implementation goes here
        return products;
    }

    /**
     * Make pass the test givenShoppingCartName_testProductRecommendation
     * @param productName The name of the products for which you want recommendations
     * @return The names of the recommended products
     */
    public List<String> getRecommendedProductsFor(String productName) {
        List<String> recommendedProducts = Lists.newArrayList();
        // Your implementation goes here
        return recommendedProducts;
    }

    /**
     * Make pass the test givenClientName_testSponsored
     * @param clientName The names of the client for whom you want to know the sponsored clients recursively
     * @return Names of the sponsored clients
     */
    public List<String> getRecursiveSponsoredClient(String clientName) {
        List<String> sponsored = Lists.newArrayList();
        // Your implementation goes here
        return sponsored;
    }

    /**
     * Make pass the test givenClientName_testAddSponsoredClient
     * @param existingClientName  The name of the client who wants to sponsor the new one
     * @param sponsoredClientName The name of the new sponsored client
     */
    public void addNewSponsoredClient(String existingClientName, String sponsoredClientName) {
        // Your implementation goes here
    }

    /**
     * Make pass the test givenDateProductNameAndColor_testNbSales
     * @param productName The name of the product for which we wantes the number of sales
     * @param date        The sales date
     * @return The number of product sales
     */
    public int getNumberOfSales(String productName, Date date) {
        // Your implementation goes here
        return 0;
    }
}
