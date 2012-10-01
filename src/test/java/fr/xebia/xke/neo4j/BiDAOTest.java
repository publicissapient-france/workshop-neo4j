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
        List<String> result = biDAO.getRecommendedProductsFor("ShoppingCart2");
        assertThat(result).containsExactly("SacHermes");
    }

    @Test
    public void givenDateProductAndColorName_testProductNbSell(){
        Calendar cal = Calendar.getInstance();
        cal.set(2012,0, 15);
        Date date15Janvier2012 = cal.getTime();

        int nbSell = biDAO.getNbSell("EscarppinsJinny", "Noir", date15Janvier2012);
        assertThat(nbSell).isEqualTo(2);
    }
}
