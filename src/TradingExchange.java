
import java.util.ArrayList;

/**
 *
 * @author sjb56
 */
public class TradingExchange {

    private ArrayList<Market> markets;

    /**
     * Avoid using in production. Used for tests to indicate multiple-market
     * flexibility.
     *
     * Create a bare Trading Exchange with no markets, which have to be added by
     * other methods.
     */
    public TradingExchange() {

    }

    /**
     * Creates a Trading Exchange instance based on the config given. 1
     * Represents the coursework spec.
     *
     * Checks that the configuration values (ie. initialisation data csv file
     * names) are valid csv files(not that they are the right context/size/etc,
     * just csv files), before creating a single StockExchange model and parsing
     * in to it's constructor the now validly checked csv file values.
     *
     * @param config - if 1, using single-market approach with one Market of
     * type StockExchange. Expects two more parameters: The csv files names of
     * the initialisation data for a single-market approach with one Market (ala
     * the coursework spec), and an external events filename.
     * @param csvStockDataFileName
     * @param csvExternalEventsFileName
     */
    public TradingExchange(int config, String csvStockDataFileName, String csvExternalEventsFileName) {
        if (config == 1) {
            this.addMarket(new StockExchange("The Best Stock Exchange", csvStockDataFileName, csvExternalEventsFileName));
        } else {
            throw new UnsupportedConfigurationValueError("Choose a valid configuration when using 3 arguments. 1, stockData.csv, externalevent.csv is the current"
                    + "only accepted configuration.");
        }
    }

    /**
     * Add a market to this trading exchanges collection.
     *
     * @param m - market to be added
     * @return true on success, false on input not valid.
     */
    public boolean addMarket(Market m) {
        if (m != null) {
            markets.add(m);
            return true;
        } else {
            return false;
        }
    }

    /**
     * gets the market at the specified index; typical use case: single-market
     * simulation, use getMarket(0) to get the relevant single market.
     *
     * @param i - access index, 0 up.
     * @return relevent requested Market if exists, otherwise throws an index
     * out of bounds runtime exception.
     */
    public Market getMarket(int i) {
        if (i < markets.size()) {
            return markets.get(i);
        } else {
            throw new IndexOutOfBoundsException("A Market at that index does not exist");
        }
    }
}
