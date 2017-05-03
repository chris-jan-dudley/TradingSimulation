/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tradingsimulation;

/**
 *
 * @author sjb56
 */
public abstract class Company {
    private String name;
    private int numberOfShares;  
    private double sharePrice;
    
    protected RiskLevels riskFactor;
    
    public enum RiskLevels {
        High, Medium, Low
    }
    
    public Company (String name, int startingShares, double startingPrice) {
        this.name = name;
        numberOfShares = startingShares;
        sharePrice = startingPrice;
    }   
    
    public String getName () {
        return name;
    }    
    
    public boolean setSharePrice (double newPrice) {
        if (newPrice >= 0) {
            sharePrice = newPrice;
            return true;
        } else {
            return false; 
        }
    }
    
    public double getSharePrice () {
        return sharePrice;
    }
    
    public boolean setNumberOfShares (int newAmount) {
        if (newAmount >= 0) {
            numberOfShares = newAmount;
            return true;
        } else {
           return false; 
        }
    }   
     
    public int getNumberOfShares () {
        return numberOfShares;
    }
    
    public void setRisk (RiskLevels risk) {
        riskFactor = risk;
    }  
    
    public RiskLevels getRiskFactor () {
        return riskFactor;
    }
    
    @Override
    protected abstract Company clone ();
    
}
