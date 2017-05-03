
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tradingsimulation.Company;
import tradingsimulation.FoodCompany;

/**
 *
 * @author James
 */
public class CompanyTest {

    FoodCompany foodComp;

    public CompanyTest() {
    }

    @Before
    public void setUp() {
        foodComp = new FoodCompany("Food", 250, 50);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    @Test
    public void ContructorTest() {
        assertTrue(foodComp.getNumberOfShares() == 250);
        assertTrue(foodComp.getSharePrice() == 50);
    }

    @Test
    public void SetSharesTest() {
        foodComp.setNumberOfShares(200);
        assertEquals(200, foodComp.getNumberOfShares());
        assertEquals(false, foodComp.setNumberOfShares(-10));
    }

    @Test
    public void SetPriceTest() {
        foodComp.setSharePrice(75);
        assertTrue(foodComp.getSharePrice() == 75);
        assertFalse(foodComp.setSharePrice(-10));

    }

    @Test
    public void RiskTest() {
        assertEquals(true, foodComp.getRiskFactor().equals(Company.RiskLevels.Low));
        foodComp.setRisk(Company.RiskLevels.High);
        assertEquals(true, foodComp.getRiskFactor().equals(Company.RiskLevels.High));
    }

    @Test
    public void CloneCompanyTest() {
        FoodCompany foodCompClone = (FoodCompany) foodComp.clone();
        assertEquals(true, foodComp.getSharePrice() == foodCompClone.getSharePrice());
        assertEquals(true, foodComp.getRiskFactor() == foodCompClone.getRiskFactor());
        assertEquals(false, foodComp == foodCompClone);
    }

}
