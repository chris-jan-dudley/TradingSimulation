
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
import tradingsimulation.Request;
/**
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Conor
 */
public class RandomTraderTest {
    RandomTrader trader;
    Client client;
    ArrayList<Client> clients;
    Portfolio portfolio;
    Company company;
    StockExchange stockE;
    
    @Before
    public void setUp() {
        stockE = new StockExchange("oil");
        company = new FoodCompany(50, 10);
        trader = new RandomTrader(null, stockE);
        portfolio = new Portfolio (null, trader);
        client = new Client(trader, portfolio, 50, "steve");
        clients = new ArrayList<>();
        clients.add(client);
        trader.setClients(clients);
        
        client.set_Portfolio(portfolio);
        portfolio.setOwnedBy(client);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void setTradeModeTest(){
        trader.setTradeMode(trader.tradeMode.SELLER);
        assertEquals(trader.tradeMode.SELLER, trader.getTradeMode());
    }
    
    @Test
    public void randIntTest(){
        assertEquals(true, trader.randomInt() < 11);
        assertEquals(true, trader.randomInt() > 0);
    }
    
    @Test
    public void tradeSellTest(){
        portfolio.addShares(company, 1000);
        trader.setSellRequests(trader.tradeSell());
        System.out.println("" + trader.calculateValue(trader.getSellRequests().get(0).getMap()));
        System.out.println("" + portfolio.calculateNetWorth()* 0.01);
        System.out.println("" + trader.tradeMode);
        assertEquals(true, trader.calculateValue(trader.getSellRequests().get(0).getMap()) <= (portfolio.calculateNetWorth() * 0.01));
    }
    
    @Test
    public void tradeSellTest2(){
        portfolio.addShares(company, 1000);
        trader.setTradeMode(trader.tradeMode.SELLER);
        trader.setSellRequests(trader.tradeSell());
        System.out.println("" + trader.calculateValue(trader.getSellRequests().get(0).getMap()));
        System.out.println("" + portfolio.calculateNetWorth()* 0.02);
        System.out.println("" + trader.tradeMode);
        assertEquals(true, trader.calculateValue(trader.getSellRequests().get(0).getMap()) <= (portfolio.calculateNetWorth() * 0.02));
    }
    
    @Test
    public void tradeSellTest3(){
        portfolio.addShares(company, 1000);
        trader.setTradeMode(trader.tradeMode.PURCHASER);
        trader.setSellRequests(trader.tradeSell());
        System.out.println("" + trader.calculateValue(trader.getSellRequests().get(0).getMap()));
        System.out.println("" + portfolio.calculateNetWorth() * 0.005);
        System.out.println("" + trader.tradeMode);
        assertEquals(true, trader.calculateValue(trader.getSellRequests().get(0).getMap()) <= (portfolio.calculateNetWorth() * 0.005));
    }
}
