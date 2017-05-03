
/**
 *
 * @author sjb56
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Conor
 */
public class Portfolio {

    Client ownedBy;
    Trader managedBy;

    /**
     *
     * @return
     */
    public Client getOwnedBy() {
        return ownedBy;
    }

    /**
     *
     * @param ownedBy
     */
    public void setOwnedBy(Client ownedBy) {
        this.ownedBy = ownedBy;
    }

    /**
     *
     * @return
     */
    public Trader getManagedBy() {
        return managedBy;
    }

    /**
     *
     * @param managedBy
     */
    public void setManagedBy(Trader managedBy) {
        this.managedBy = managedBy;
    }

    /**
     *
     * @return
     */
    public Map<Company, Integer> getOwnedShares() {
        return ownedShares;
    }

    /**
     *
     * @param ownedShares
     */
    public void setOwnedShares(Map<Company, Integer> ownedShares) {
        this.ownedShares = ownedShares;
    }
    int cashValue = 0;

    /**
     *
     * @param cashValue
     */
    public void setCashValue(int cashValue) {
        this.cashValue = cashValue;
    }
    Map<Company, Integer> ownedShares;
    int riskValue;

    /**
     *
     * @param client
     * @param trader
     */
    public Portfolio(Client client, Trader trader) {
        ownedBy = client;
        managedBy = trader;
        ownedShares = new HashMap<>();
    }

    /**
     *
     * @param company
     * @param number
     * @return
     */
    public boolean addShares(Company company, int number) {
        if (ownedShares.containsKey(company)) {
            ownedShares.replace(company, ownedShares.get(company) + (number));
            return true;
        } else if (!ownedShares.containsKey(company)) {
            ownedShares.put(company, number);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param company
     * @param number
     * @return
     */
    public boolean removeShares(Company company, int number) {
        //remove shares from the map
        if (ownedShares.get(company) <= number) {
            ownedShares.replace(company, ownedShares.get(company) - number);
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param cash
     * @return
     */
    public boolean removeCash(int cash) {
        //if cashvalue - cash is >= 0 then
        //cashvalue = cashvalue - cash
        //return true
        //else 
        //return false

        if ((cashValue - cash) >= 0) {
            cashValue -= cash;
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param cash
     * @return
     */
    public boolean addCash(int cash) {
        cashValue += cash;
        return true;
    }

    /**
     *
     * @return
     */
    public int getCurrentCash() {
        return cashValue;
    }

    /**
     *
     * @return
     */
    public int calculateNetWorth() {
        //go through map
        //for each company in map
        //total value += number of shares of each company
        // * share price of each company
        //return value
        int netWorth = 0;
        for (Company key : ownedShares.keySet()) {
            netWorth += ownedShares.get(key);
        }
        return netWorth;
    }

    /**
     *
     * @return
     */
    public int getRisk() {
        return riskValue;
    }

    /**
     *
     * @param risk
     * @return
     */
    public boolean setRisk(int risk) {
        if (risk <= 100) {
            riskValue = risk;
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public Portfolio clone() {
        Portfolio clonePortfolio = new Portfolio(this.getOwnedBy(), this.getManagedBy());
        clonePortfolio.setCashValue(this.getCurrentCash());
        clonePortfolio.setOwnedShares(this.getOwnedShares());
        clonePortfolio.setRisk(this.getRisk());
        return clonePortfolio;
    }

}
