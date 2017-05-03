
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author sjb56
 */
abstract public class Market {

    private final String name;
    private int currentTick;
    private Date startDate;
    private Date endDate;
    private int tickRate;

    /**
     *
     * @param name
     */
    public Market(String name) {
        this.name = name;
    }

    abstract boolean constructFromCSV(String csvStockDataFN, String csvExternalEventsFN);

    abstract void tick();

    /**
     * sets the tick to 0, aka, akin to the start date
     */
    public void resetTick() {
        this.currentTick = 0;
    }

    // old return was: HashMap<Integer, HashMap<String, Integer>>
    abstract ArrayList<TickRow> getGraphDataOverTime(int fromTick, int toTick);

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * param tick, an int referencing the tick
     *
     *
     * @param tick
     * @return 
     */
    public Date getDateFromTick(int tick) {
        return new Date(0, 0, 0);
    }

}
