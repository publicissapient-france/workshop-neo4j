package fr.xebia.xke.neo4j;

import static junit.framework.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class BiDAOTest extends AbstractTest {

    private BiDAO biDAO;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        biDAO = new BiDAO(graphDb);
    }

    @Test
    public void givenShoppingCartName_testProductRecommendation(){
        List<String> result = biDAO.getRecommendedProductsFor("ShoppingCart2");
        List<String> expected = Arrays.asList("SacHermes");
        assertEquals(expected, result);
    }
}
