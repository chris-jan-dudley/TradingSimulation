
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Conor
 */
public abstract class Trader {

    ArrayList<Client> clients;

    /**
     *
     * @return
     */
    public ArrayList<Client> getClients() {
        return clients;
    }

    /**
     *
     * @param clients
     */
    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    /**
     *
     * @return
     */
    public ArrayList<Request> getBuyRequests() {
        return buyRequests;
    }

    /**
     *
     * @param buyRequests
     */
    public void setBuyRequests(ArrayList<Request> buyRequests) {
        this.buyRequests = buyRequests;
    }

    /**
     *
     * @return
     */
    public ArrayList<Request> getSellRequests() {
        return sellRequests;
    }

    /**
     *
     * @param sellRequests
     */
    public void setSellRequests(ArrayList<Request> sellRequests) {
        this.sellRequests = sellRequests;
    }

    /**
     *
     * @return
     */
    public StockExchange getStockE() {
        return stockE;
    }

    /**
     *
     * @param stockE
     */
    public void setStockE(StockExchange stockE) {
        this.stockE = stockE;
    }
    ArrayList<Request> buyRequests;
    ArrayList<Request> sellRequests;
    StockExchange stockE;

    /**
     *
     * @param clients
     * @param stockE
     */
    public Trader(ArrayList<Client> clients, StockExchange stockE) {
        buyRequests = new ArrayList<>();
        sellRequests = new ArrayList<>();
        this.stockE = stockE;
        this.clients = clients;
    }

    /**
     *
     * @param map
     * @param company
     * @param number
     */
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

    /**
     *
     * @return
     */
    @Override
    protected abstract Trader clone();

    abstract void recalculateStrategy();

}
