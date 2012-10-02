package fr.xebia.xke.neo4j;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
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
    public void givenDateProductNameAndColor_testProductNbSell(){
        Calendar cal = Calendar.getInstance();
        cal.set(2012,0, 15);
        Date date15Janvier2012 = cal.getTime();

        int nbSell = biDAO.getNbSell("EscarppinsJinny", "Noir", date15Janvier2012);
        assertThat(nbSell).isEqualTo(2);
    }

    @Test
    public void givenClientName_testSponsored(){

        List<String> sponsored = biDAO.getSponsored("client1");
        assertThat(sponsored).containsOnly("client2", "client3", "client4", "client5");

        sponsored = biDAO.getSponsored("client3");
        assertThat(sponsored).containsOnly("client4");
    }
}
