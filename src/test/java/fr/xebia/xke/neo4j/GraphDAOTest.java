package fr.xebia.xke.neo4j;

import java.util.Calendar;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class GraphDAOTest extends AbstractTest {

    private GraphDAO graphDAO;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        graphDAO = new GraphDAO(graphDb);
    }

    @Test
    public void givenShoppingCartName_testProductFor() {
        List<String> result = graphDAO.getProductsFor("ShoppingCart1");
        assertThat(result).containsOnly("SacHermes", "EscarppinsJinny");

        result = graphDAO.getProductsFor("ShoppingCart2");
        assertThat(result).containsOnly("EscarppinsJinny", "ChaussureLouboutin");
    }

    @Test
    public void givenShoppingCartName_testProductRecommendation() {
        List<String> result = graphDAO.getRecommendedProductsFor("EscarppinsJinny");
        assertThat(result).containsOnly("SacHermes", "ChaussureLouboutin");

        result = graphDAO.getRecommendedProductsFor("SacLouisVitton");
        assertThat(result).containsOnly("ChaussureLouboutin", "SacHermes");
    }

    @Test
    public void givenClientName_testSponsored() {
        List<String> sponsored = graphDAO.getRecursiveSponsoredClient("client1");
        assertThat(sponsored).containsOnly("client2", "client3", "client4", "client5");

        sponsored = graphDAO.getRecursiveSponsoredClient("client3");
        assertThat(sponsored).containsOnly("client4");
    }

    @Test
    public void givenClientName_testAddSponsoredClient() {
        graphDAO.addNewSponsoredClient("client5", "client6");

        List<String> sponsored = graphDAO.getRecursiveSponsoredClient("client5");
        assertThat(sponsored).containsOnly("client6");
    }

    @Test
    public void givenDateProductNameAndColor_testNbSales() {
        Calendar cal = Calendar.getInstance();
        cal.set(2012, Calendar.JANUARY, 15);

        int nbSell = graphDAO.getNumberOfSales("EscarppinsJinny", cal.getTime());
        assertThat(nbSell).isEqualTo(2);

        cal.set(2000, Calendar.JANUARY, 2);
        nbSell = graphDAO.getNumberOfSales("ChaussureLouboutin", cal.getTime());
        assertThat(nbSell).isEqualTo(1);

        nbSell = graphDAO.getNumberOfSales("ChaussureLouboutin", cal.getTime());
        assertThat(nbSell).isEqualTo(1);
    }
}
