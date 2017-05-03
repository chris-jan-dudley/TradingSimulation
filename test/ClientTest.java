
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class ClientTest {

    Trader t;
    Portfolio p;
    StockExchange se;
    ArrayList<Client> cl;
    Client c;
    int InitialRisk;

    public ClientTest() {
    }

    @Before
    public void setUp() {
        cl = new ArrayList();
        se = new StockExchange("test1");
        t = new RandomTrader(cl, se);
        InitialRisk = 10;
        p = new Portfolio(null, t);
        c = new Client(t, p, InitialRisk, "test");
        c.set_Portfolio(p);

    }

    @Test
    public void testInitialRisk() {
        assertEquals(InitialRisk, c.getRisk());
    }

    @Test
    public void testSetRiskTrue() {
        assertTrue(c.setRisk(50));
        assertEquals(50, c.getRisk());
    }

    @Test
    public void testSetRiskFalseUpperBound() {
        c.setRisk(50);
        assertFalse(c.setRisk(101));
        assertEquals(50, c.getRisk());
    }

    @Test
    public void testSetRiskFalseLowerBound() {
        c.setRisk(50);
        assertFalse(c.setRisk(-1));
        assertEquals(50, c.getRisk());
    }

    @Test
    public void testSetGetPortfolio() {
        Portfolio p2 = new Portfolio(null, null);
        c.set_Portfolio(p);
        assertEquals(p, c.get_Portfolio());
        c.set_Portfolio(p2);
        assertEquals(p2, c.get_Portfolio());
        assertFalse(c.get_Portfolio().equals(p));
    }

    @Test
    public void testGetName() {
        assertEquals("test", c.getName());
    }

    @Test
    public void testGetTrader() {
        assertEquals(t, c.getTrader());
    }
}
