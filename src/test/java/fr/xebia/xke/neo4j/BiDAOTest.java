package fr.xebia.xke.neo4j;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

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
        assertThat(result).containsExactly("SacHermes");
    }
}
