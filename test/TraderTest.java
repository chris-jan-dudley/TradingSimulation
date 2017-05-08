/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 *
 * @author Conor
 */
public class TraderTest {
    
    Trader trader;
    Client client;
    ArrayList<Client> clients;
    Portfolio portfolio;
    Company company;
    HashMap<Company, Integer> map;
    
    @Before
    public void setUp() {
        StockExchange stockE = new StockExchange("oil");
        company = new FoodCompany("bakedBeans", 50, 10);
        trader = new RandomTrader(null, stockE);
        portfolio = new Portfolio (null, trader);
        client = new Client(trader, portfolio, 50, "steve");
        clients = new ArrayList<>();
        clients.add(client);
        trader.setClients(clients);
        portfolio.setOwnedBy(client);
        client.set_Portfolio(portfolio);
        map = new HashMap<>();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void addToMapTest(){
        trader.addToMap(map, company, 3);
        assertEquals(true, map.containsKey(company));
        assertEquals(3, (int)map.get(company));
    }

}
