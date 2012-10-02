package fr.xebia.xke.neo4j;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
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
        List<String> result = biDAO.getRecommendedProductsFor("EscarppinsJinny");
        assertThat(result).containsOnly("SacHermes", "ChaussureLouboutin");

        result = biDAO.getRecommendedProductsFor("SacLouisVitton");
        assertThat(result).containsOnly("ChaussureLouboutin", "SacHermes");
    }

    @Test
    public void givenDateProductNameAndColor_testNbSales(){
        Calendar cal = Calendar.getInstance();
        cal.set(2012,0, 15);

        int nbSell = biDAO.getNbSales("EscarppinsJinny", "Noir", cal.getTime());
        assertThat(nbSell).isEqualTo(2);

        cal.set(2000,0, 02);
        nbSell = biDAO.getNbSales("ChaussureLouboutin", "Noir", cal.getTime());
        assertThat(nbSell).isEqualTo(1);

        nbSell = biDAO.getNbSales("ChaussureLouboutin", "Jaune", cal.getTime());
        assertThat(nbSell).isEqualTo(1);
    }

    @Test
    public void givenClientName_testSponsored(){

        List<String> sponsored = biDAO.getSponsored("client1");
        assertThat(sponsored).containsOnly("client2", "client3", "client4", "client5");

        sponsored = biDAO.getSponsored("client3");
        assertThat(sponsored).containsOnly("client4");
    }
}
