
import java.util.ArrayList;

/**
 *
 * @author sjb56
 */
public class TickRow {

    ArrayList<Company> companyClonesAtTick;
    private ArrayList<Trader> traders;
    private ArrayList<TradeHappening> occured;
    private ArrayList<ExternalEvent> eventsOccuring;

    /**
     *
     * @param c
     */
    public void addCompanyPrices(ArrayList<Company> c) {
        this.companyClonesAtTick = c;
    }

    /**
     *
     * @return
     */
    public ArrayList<Company> getCompanyPrices() {
        return this.companyClonesAtTick;
    }

    /**
     *
     * @return
     */
    public int getExchangeIndex() {
        int total = 0;
        for (Company c : companyClonesAtTick) {
            total = (int) (total + c.getSharePrice());
        }
        return total;
    }

    /**
     *
     */

    void addEventsOccuring(ArrayList<ExternalEvent> ev) {
        this.eventsOccuring = ev;
    }


    void commitRow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void addTraders(ArrayList<Trader> clonedTraders) {
        this.traders = clonedTraders;
    }

    void addOccuredTrades(ArrayList<TradeHappening> thisTickTrades) {
        this.occured = thisTickTrades;
    }

}
