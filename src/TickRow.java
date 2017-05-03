
import java.util.ArrayList;

/**
 *
 * @author sjb56
 */
public class TickRow {

    ArrayList<Company> companyClonesAtTick;

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
    public void addOccuredTrades() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void addEventChanges() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void addTraders() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void commitRow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
