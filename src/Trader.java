
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Conor
 */
public abstract class Trader {

    ArrayList<Client> clients;

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public ArrayList<Request> getBuyRequests() {
        return buyRequests;
    }

    public void setBuyRequests(ArrayList<Request> buyRequests) {
        this.buyRequests = buyRequests;
    }

    public ArrayList<Request> getSellRequests() {
        return sellRequests;
    }

    public void setSellRequests(ArrayList<Request> sellRequests) {
        this.sellRequests = sellRequests;
    }

    public StockExchange getStockE() {
        return stockE;
    }

    public void setStockE(StockExchange stockE) {
        this.stockE = stockE;
    }
    ArrayList<Request> buyRequests;
    ArrayList<Request> sellRequests;
    StockExchange stockE;

    public Trader(ArrayList<Client> clients, StockExchange stockE) {
        buyRequests = new ArrayList<>();
        sellRequests = new ArrayList<>();
        this.stockE = stockE;
        this.clients = clients;
    }

    public void addToMap(HashMap<Company, Integer> map, Company company, int number) {

        //If the map contains the company with shares already, replace it with
        //new number of shares. otherwise just add to map.
        if (map.containsKey(company)) {
            map.replace(company, (number + (map.get(company))));
        } else {
            map.put(company, number);
        }

    }

    //for clearing request lists between ticks
    void clear() {
        buyRequests.clear();
        sellRequests.clear();
    }

    abstract ArrayList<Request> tradeBuy();

    abstract ArrayList<Request> tradeSell();

    @Override
    protected abstract Trader clone();

    abstract void recalculateStrategy();

}
