/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import tradingsimulation.Client;
import tradingsimulation.Company;
import tradingsimulation.FoodCompany;
import tradingsimulation.Portfolio;
import tradingsimulation.RandomTrader;
import tradingsimulation.StockExchange;
import tradingsimulation.Trader;
/**
 *
 * @author Conor
 */
public class PortfolioTest {
    Trader trader;
        Client client;
        ArrayList<Client> clients;
        Portfolio portfolio;
        Company company;
    
    
    @Before
    public void setUp() {
            
        StockExchange stockE = new StockExchange("oil");
        company = new FoodCompany(50, 10);
        trader = new RandomTrader(null, stockE);
        portfolio = new Portfolio (null, trader);
        client = new Client(trader, portfolio, 50, "steve");
        clients = new ArrayList<>();
        clients.add(client);
        trader.setClients(clients);
        
        client.set_Portfolio(portfolio);
        
        
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void addCashTest(){
        portfolio.addCash(50);
        assertEquals(50, portfolio.getCurrentCash());
    }
    
    @Test
    public void removeCashTest(){
        portfolio.addCash(50);
        portfolio.removeCash(25);
        assertEquals(25, portfolio.getCurrentCash());
    }
    
    @Test
    public void setRiskValueTest(){
        portfolio.setRisk(50);
        assertEquals(50, portfolio.getRisk());
    }

    
    @Test
    public void addSharesTest(){
        portfolio.addShares(company, 2);
        assertEquals(true, portfolio.getOwnedShares().containsKey(company));
        assertEquals(2, (int)portfolio.getOwnedShares().get(company));
    }
        
    @Test
    public void calculateNetWorthTest(){
        portfolio.addShares(company, 2);
        assertEquals((company.getSharePrice()*2), portfolio.calculateNetWorth());
    }
    
    
}
