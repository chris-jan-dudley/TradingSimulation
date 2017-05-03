
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import javafx.util.Pair;
import javax.swing.text.View;


/**
 *
 * @author sjb56
 */
public class StockExchange extends Market {

    private int currentTick;
    private int endTick;
    private Date startDate;
    private Date endDate;
    private StockExchangeData mem;
    private ArrayList<ExternalEvent> externalEvents;
    private HashMap<Integer, ArrayList<ExternalEvent>> externalEventsIndexedToTicks;
    private ArrayList<Trader> traders;
    private ArrayList<Company> companies;
    private ArrayList<Client> clients;
    private ArrayList<Portfolio> portfolios;
    private ViewController view; // 
    private ArrayList<TradeHappening> thisTickTrades;
    private ArrayList<ExternalEvent> eventsThisTick;

    /**
     * Just like with Market/TradingExchange, this is provided for testing, not
     * intended for production use (You should always use constructors with init
     * data rather then manually access methods to add CSV data)
     *
     * @param marketName - identify the name to use for this market for
     * references.
     */
    public StockExchange(String marketName) {
        super(marketName);
    }

    /**
     * Creates an instance of a stock exchange, following TradingSimulations
     * config 1, and so expecting csv file names for stock init data and
     * external events
     *
     * @param marketName - used for referencing.
     * @param csvStockDataFileName - valid filename expected of form ala.
     * coursework spec.
     * @param csvExternalEventsFileName - valid filename expected of form ala.
     * coursework spec.
     */
    public StockExchange(String marketName, String csvStockDataFileName, String csvExternalEventsFileName) {
        super(marketName);
        if (!this.constructFromCSV(csvStockDataFileName, csvExternalEventsFileName)) {
            // throw error to GUI
            throw new UnsupportedOperationException("Attach method to tell user CSV was not able to loaded, through GUI.");
        }
        // calculate end tick from start/end date.
        this.calculateEndTick();
        // external events should all be added, so now index them according to tick.
        this.externalEventsIndexedToTicks = calculateEventsIndexs();
        // initialise memory
        this.mem = new StockExchangeData();

        this.view.readyGUI();
    }

    /**
     * A wrapper function to deal with all the CSV parsing functions
     *
     * @param csvStockDataFN
     * @param csvExternalEventsFN
     * @return false if unable to parse data/file/invalid filename, true if
     * successfully parsed.
     */
    @Override
    boolean constructFromCSV(String csvStockDataFN, String csvExternalEventsFN) {
        try {
            this.parseStockDataCSV(csvStockDataFN);
            this.parseExternalEventCSV(csvExternalEventsFN);
        } catch (FileNotFoundException ex) {
            return false;
        }
        return true;
    }

    /**
     *
     * The vital method to the class, this 1. calls the price change formulas,
     * "slightlyVariatePrices();" 2. checks for occuring external events and
     * incorporates that, 3. loop through supply and demand 3.1 calls
     * .getClient() on all portfolios, .getTrader() from that, 3.2 for each
     * portfolios trader, call .tradeBuy() to collect the requested purchases
     * for that portfolio, .tradeSell() for the respective requested sells. 3.3
     * arrange supply and demand such that it is easily separated into Requests
     * for buying and Requests for selling per Company
     *
     * 4. (part of 3 ..) actually execute trades on objects, by looping through
     * each Company: 4.1 grab requests for each Company, sum totalDesiredBuy,
     * totalDesiredSell 4.2 if statement that both those totals are > 0 4.25 if
     * so, take min() of the totals. 4.26 proportionately assign "AmountCan" to
     * each request according to that min proportioned to the totalDesiredBuy
     * and totalDesiredSell (one side, sell or buy, should always have 100%
     * "fufilled". 4.3 sets the shares owned to the right places, 4.4 sets the
     * cash to the right values 5. adapts random traders to
     * balanced/aggressive/seller based on probability(.recalculateStrategy());
     * 6. store records of the current object sets, share prices, clients, etc,
     * in the memory. 7. notify the GUI that the execution has occured so it can
     * update on the screen
     *
     */
    @Override
    void tick() {
        this.thisTickTrades = null;
        this.eventsThisTick = null;
        this.currentTick = this.currentTick + 1;
        // 1 randomly variate prices before traders can request...
        slightlyVariatePrices();

        // 2 apply events
        this.eventsThisTick = this.externalEventsIndexedToTicks.get(this.currentTick);
        applyExternalEvents(eventsThisTick);

        // 3 & 4 Loop through supply and demand, then Execute current offers, update objects.
        // Requires other implementation first.
        executeTrades();

        // 5. Recalcate strategy (ie. random trader changes agressive to balanced, etc, etc.)
        for (Trader t : traders) {
            t.recalculateStrategy(); // obviously random trader does this based on probability, intelligent trader has access to this object to make it's decisions.
        }

        // 6. update mem
        ArrayList<Company> clonedCompanies = new ArrayList<Company>();
        for (Company c : companies) {
            clonedCompanies.add(c.clone());
        }

        ArrayList<Trader> clonedTraders = new ArrayList<Trader>();
        for (Trader t : traders) {
            clonedTraders.add(t.clone());
        }

        TickRow thisTickMemory = mem.addTickRow();

        thisTickMemory.addCompanyPrices(clonedCompanies);
        // put company prices in the memory
        thisTickMemory.addTraders(clonedTraders);
        // put trader states (agressive, balanced, etc) in memory
        thisTickMemory.addOccuredTrades(this.thisTickTrades);
        // put the trades that occured this tick to last..
        thisTickMemory.addEventChanges(this.eventsThisTick);
        // put the events that began and ended as a Strings under Events
        // lock the row for editing

        //7 notify GUI
        view.update();

    }

    // note: kept public for individually testing CSV loading the events file only

    /**
     *
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public boolean parseExternalEventCSV(String fileName) throws FileNotFoundException {
        // 1st, creates a CSV parser with the configs
        //CsvParser parser = new CsvParser(new CsvParserSettings());

        // 2nd, parses all rows from the CSV file into a 2-dimensional array
        //List<String[]> resolvedData = parser.parseAll(new FileReader("/examples/example.csv"));
        String csvFile = "fileName";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);

                System.out.println(Arrays.toString(country));

            }
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    // note: kept public for individually testing CSV loading for init data only.

    /**
     *
     * @param csvStockDataFN
     */
    public void parseStockDataCSV(String csvStockDataFN) {

        try {
            String line32 = Files.readAllLines(Paths.get(csvStockDataFN)).get(32);
        } catch (IOException e) {
            view.reportBadFile("Stock Init Data was not valid for file reading");

        }
    }

    Iterable<Company> getCompanies() {
        return this.companies;
    }

    /**
     * Access this Stock Exchanges mem objects for the rows specified.
     *
     * @param fromTick
     * @param toTick
     * @return
     */
    @Override
    public ArrayList<TickRow> getGraphDataOverTime(int fromTick, int toTick) {
        ArrayList<TickRow> memoryRows = new ArrayList<>();
        for (int i = fromTick; i < toTick; i++) {
            memoryRows.add(mem.getTickRow(i));
        }
        return memoryRows;
    }
    // to return: Hashmap<int tickIndex, Hashmap<String goodName, int itemValue>>

    private void addExternalEvent(ExternalEvent e) {
        this.externalEvents.add(e);
    }

    private HashMap<Integer, ArrayList<ExternalEvent>> calculateEventsIndexs() {
        // for each tick the simulation runs
        HashMap<Integer, ArrayList<ExternalEvent>> evIndex = new HashMap<Integer, ArrayList<ExternalEvent>>();
        for (int i = 0; i < this.endTick; i++) {
            ArrayList<ExternalEvent> val = evIndex.get(i);
            // check active external events at that tick and put it in that ticks hashmap
            for (ExternalEvent event : externalEvents) {
                int fromTick = event.getFromTick();
                int toTick = event.getToTick();
                if (i >= fromTick && i <= toTick) {
                    val.add(event);
                    // it is within the period, so append it to the hashmap for this, ith, tick.
                }
            }
            evIndex.put(i, val);
        }
        return evIndex;
    }

    private void calculateEndTick() {
        this.endTick = 100;
    }

    private void slightlyVariatePrices() {
        // awaiting others to commit so can access price and modify it, psuedo code below:
        Random rng = new Random();
        for (Company c : companies) {
            c.setSharePrice((int) Math.min(1, (c.getSharePrice() * ((0.5 - rng.nextFloat()) / 50))));
        }

    }

    private void applyExternalEvents(ArrayList<ExternalEvent> EVs) {
        for (ExternalEvent event : EVs) {
            if (event instanceof AnyExtEvt) {
                // iterate through companies .. randomly adding buy/sell orders of various companies..
            } else if (event instanceof CategoryExtEvt) {
                /* iterate only through companies of that category
                eventsCategory = event.getCategory
                        .. if company.getCategory = eventsCategory 
                                .. add to some orders..
                 */
            } else if (event instanceof CompanyExtEv) {
                // randomly add buy/sell orders to this company..
            }
        }
    }

    /**
     * 1. Generate list of supply and demand per Portfolio 2. Arrange (into
     * companies -- demand/supply) into form where we can work out possible
     * trades 3. execute proportionately 4. thats it
     */
    /*
    
    3. loop through supply and demand
     *          3.1 calls .getTrader() from that,
     *          3.2 for each portfolios trader, call .tradeBuy() to collect the requested purchases for that portfolio, .tradeSell() for the respective requested sells.
     *          3.3 arrange supply and demand such that it is easily separated into Requests for buying and Requests for selling per Company
    
    * 4. (part of 3 ..) actually execute trades on objects, by looping through each Company:
     *          4.1 grab requests for each Company, sum totalDesiredBuy, totalDesiredSell
     *          4.2 if statement that both those totals are > 0
     *              4.25 if so, take min() of the totals.
     *              4.26 proportionately assign "AmountCan" to each request according to that min proportioned to the totalDesiredBuy and totalDesiredSell (one side, sell or buy, should always have 100%
     *              "fufilled".
     *          4.3 sets the shares owned to the right places,
     *          4.4 sets the cash to the right values
     */
    private void executeTrades() {
        ArrayList<TradeHappening> trades = this.thisTickTrades;

        // TO CHANGE: below
        // .trade(Buy|Sell)  are implemented as per-Trader --> per Traders Clients --> per Portfolio in randomTrader
        // so instead of iterating through portfolio, iterate through traders out (it does make more sense...)
        for (Portfolio p : portfolios) {
            Trader psTrader = p.getManagedBy();
            ArrayList<Request> wantToBuy = psTrader.tradeBuy();
            ArrayList<Request> wantToSell = psTrader.tradeSell();
            // each Request represents one clients singular portfolio.
            // each request has a map of what that portfolio should aim to buy or sell.
            for (Request portfolioInstance : wantToBuy) {
                HashMap< Company, Integer> buyMap = portfolioInstance.getMap();

            }

        }

        // Collect and store supply and demand for each Company
        

        for (Trader t : traders) {
            ArrayList<Pair<Company, int>> wantToBuy = t.getWantToBuy();
            for (Pair<Company, int> requestedPurchase : wantToBuy) {
                // add to relevant CompanyTradeInfo

            }
        }
    }

}
