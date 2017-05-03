/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
        assertEquals(250, foodComp.getNumberOfShares());
        assertEquals(50, foodComp.getSharePrice());
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
        assertEquals(75, foodComp.getSharePrice());
        assertEquals(false, foodComp.setSharePrice(-10));
        
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
