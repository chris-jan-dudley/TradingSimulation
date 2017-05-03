
import java.util.ArrayList;

/**
 *
 * @author sjb56
 */
public class StockExchangeData {

    ArrayList<TickRow> rows;

    /**
     *
     * @return
     */
    public TickRow addTickRow() {
        TickRow r = new TickRow();
        rows.add(r);
        return (r);
    }
    
    public void setTickRow(TickRow tR) {
        rows.add(tR);
    }

    /**
     *
     * @param atTick
     * @return
     */
    public TickRow getTickRow(int atTick) {
        return rows.get(atTick);
    }

    /**
     *
     * @return
     */
    public TickRow getLatestRow() {
        return rows.get(rows.size() - 1);
    }

}
