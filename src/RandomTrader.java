/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tradingsimulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Conor
 */
public class RandomTrader extends Trader {

    public mode tradeMode;

    public mode getTradeMode() {
        return tradeMode;
    }

    public void setTradeMode(mode tradeMode) {
        this.tradeMode = tradeMode;
    }

    public RandomTrader(ArrayList < Client > clients, StockExchange stockE) {
        super(clients, stockE);
        tradeMode = mode.BALANCED;
    }

    @Override
    ArrayList<Request> tradeBuy() {
        //For each client, generate a request object which contains a reference
        // to the portfolio, and a list of shares it would like to buy

        for (Client client: clients) {

            //Create request object with the portfolio to be bought into
            Request buyReq = new Request(client.get_Portfolio());

            //Create new hashmap, containing company and number of shares from
            //company they wish to buy
            HashMap < Company, Integer > wantBuy = new HashMap < > ();

            //Switch case for each trader mode, only difference being the number
            //the total assets are multiplied by
            switch (tradeMode) {
                //When in BALANCED mode
                case BALANCED:
                    {
                        //while the total value of the share requests are less than total assets in portfolio * 0.01
                        while (calculateValue(wantBuy) < ((double) client.getNetWorth() * 0.01)) {
                            //for each company in the company list
                            for (Company company: stockE.getCompanies()) {
                                //generate a random int to "randomly" add shares
                                int randomInteger = randomInt();
                                //if the int is 5 or less AND the above condition
                                if (randomInteger <= 5 && calculateValue(wantBuy) < ((double) client.getNetWorth() * 0.01)) {
                                    //add a share to the wantBuy map
                                    addToMap(wantBuy, company, 1);
                                }
                            }
                        }
                    }
                case PURCHASER:
                    {
                        while (calculateValue(wantBuy) < ((double) client.getNetWorth() * 0.02)) {
                            for (Company company: stockE.getCompanies()) {
                                int randomInteger = randomInt();
                                if (randomInteger <= 5 && calculateValue(wantBuy) < ((double) client.getNetWorth() * 0.02)) {
                                    addToMap(wantBuy, company, 1);
                                }
                            }
                        }
                    }
                case SELLER:
                    {
                        while (calculateValue(wantBuy) < ((double) client.getNetWorth() * 0.005)) {
                            for (Company company: stockE.getCompanies()) {
                                int randomInteger = randomInt();
                                if (randomInteger <= 5 && calculateValue(wantBuy) < ((double) client.getNetWorth() * 0.005)) {
                                    addToMap(wantBuy, company, 1);
                                }
                            }
                        }
                    }
            }

            //adds the wantBuy map to the Request object
            buyReq.setMap(wantBuy);
            //adds the Request object to the buyRequests ArrayList
            buyRequests.add(buyReq);
        }
        
        return buyRequests;
    }

    @Override
    ArrayList<Request> tradeSell() {
        for (Client client: clients) {
            Request sellReq = new Request(client.get_Portfolio());
            HashMap < Company, Integer > wantSell = new HashMap < > ();
            switch (tradeMode) {
                case BALANCED:
                    {
                        while ((calculateValue(wantSell) < ((double) client.getNetWorth() * 0.01)  || (client.get_Portfolio().calculateNetWorth() == calculateValue(wantSell)))) {
                            for (Company company: client.get_Portfolio().ownedShares.keySet()) {
                                int randomInteger = randomInt();
                                if (randomInteger <= 5 && calculateValue(wantSell) < ((double) client.getNetWorth() * 0.01)) {
                                    addToMap(wantSell, company, 1);
                                }
                            }
                        }
                    }
                case PURCHASER:
                    {
                        while ((calculateValue(wantSell) < ((double) client.getNetWorth() * 0.005)  || (client.get_Portfolio().calculateNetWorth() == calculateValue(wantSell)))) {
                            for (Company company: client.get_Portfolio().ownedShares.keySet()) {
                                int randomInteger = randomInt();
                                if (randomInteger <= 5 && calculateValue(wantSell) < ((double) client.getNetWorth() * 0.005 )) {
                                    addToMap(wantSell, company, 1);
                                }
                            }
                        }
                    }
                case SELLER:
                    {
                        while ((calculateValue(wantSell) < ((double) client.getNetWorth() * 0.02) || (client.get_Portfolio().calculateNetWorth() == calculateValue(wantSell)))) {
                            for (Company company: client.get_Portfolio().ownedShares.keySet()) {
                                int randomInteger = randomInt();
                                if (randomInteger <= 5 && calculateValue(wantSell) < ((double) client.getNetWorth() * 0.02)) {
                                    addToMap(wantSell, company, 1);
                                }
                            }
                        }
                    }
            }

            sellReq.setMap(wantSell);
            sellRequests.add(sellReq);
        }
        
        return sellRequests;
    }

    //This is used to calculate the value of a map in terms of the sum of all (share number * share price)
    int calculateValue(HashMap < Company, Integer > map) {
        int totalMapValue = 0;
        if (!map.isEmpty()) {
            for (Company company: map.keySet()) {
                totalMapValue += (map.get(company) * company.getSharePrice());
            }
            return totalMapValue;
        } else {
            return 0;
        }
    }

    //purchase mode enum
    public enum mode {
        PURCHASER,
        BALANCED,
        SELLER
    }

    //returns a random int between 1 and 10
    public int randomInt() {
        Random randomGenerator = new Random();
        int randInt = randomGenerator.nextInt(10);

        return randInt + 1;
    }

    //changes the mode of the trader in accordance to the probabilities.
    @Override
    void recalculateStrategy() {
        switch (tradeMode) {
            case BALANCED:
                int probabilityB = randomInt();
                if (probabilityB > 2) {
                    tradeMode = mode.BALANCED;
                } else if (probabilityB == 1) {
                    tradeMode = mode.SELLER;
                } else if (probabilityB == 2) {
                    tradeMode = mode.PURCHASER;
                }

            case PURCHASER:
                int probabilityP = randomInt();
                if (probabilityP > 3) {
                    tradeMode = mode.BALANCED;
                } else {
                    tradeMode = mode.SELLER;
                }

            case SELLER:
                int probabilityS = randomInt();
                if (probabilityS > 4) {
                    tradeMode = mode.BALANCED;
                } else {
                    tradeMode = mode.SELLER;
                }
        }
    }
    
    
    @Override
    protected Trader clone(){
        RandomTrader cloneRandom = new RandomTrader(super.getClients(), super.getStockE());
        cloneRandom.setBuyRequests(super.getBuyRequests());
        cloneRandom.setSellRequests(super.getSellRequests());
        cloneRandom.setTradeMode(tradeMode);
        return cloneRandom;
    }
}