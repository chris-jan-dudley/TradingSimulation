/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tradingsimulation;
/**
 *
 * @author Liam 
 */
public class Client {
    
    Portfolio portfolio;
    Trader trader;
    int risk;
    final String clientName;
/**
 * 
 * @param trader
 * @param portfolio
 * @param initialRisk
 * @param clientName 
 */
public Client(Trader trader, Portfolio portfolio, int initialRisk,String clientName){
    this.portfolio = portfolio;
    this.trader = trader;
    portfolio.setRisk(initialRisk);
    this.clientName = clientName;
}

/**
 * @param risk the percentage of risk to take when trading 
 * @return if the operation was successful or not 
 */
public boolean setRisk(int risk){
    if(risk >=0 && risk <=100){
        portfolio.setRisk(risk);
        return true;
    }
    else{return false;}
}

/**
 * @return the current level of risk for this client 0>=n<=100
 */
public int getRisk(){return portfolio.getRisk();}//from portfoli?????

/**
 * @return the net worth of this clients portfolio 
 */
public int getNetWorth(){return portfolio.calculateNetWorth();}

/**
 * set a new portfolio for this client
 * @param p the portfolio to be set
 */
public void set_Portfolio(Portfolio p){portfolio = p;}

/**
 * @return the portfolio for this client
 */
public Portfolio get_Portfolio(){return portfolio;}

/**
 * @return the name of this client
 */
public String getName(){return clientName;}

/**
 * 
 * @return the Trader assigned to this Client  
 */
public Trader getTrader(){return trader;}
}
