
/**
 *
 * @author sjb56
 */
public class TradingSimulation {
    
    private static TradingExchange tradingExchange;

    /**
     * @param args the command line arguments,
     */
    public static void main(String[] args) {
        //TODO code application logic here
        //Create a TradingExchnage instance, set it to the static variable
        ViewController.readyGUI();
        // this first so that view is about, view is required to return error on csv load etc.
        TradingSimulation tS = new TradingSimulation(1, args[0], args[1]);
        TradingExchange tE = tS.getTradeExchange();
        StockExchange sE = (StockExchange)tE.getMarket(0);
        
    }
    
    public TradingExchange getTradeExchange() {
        return tradingExchange;
    }
    
    public TradingSimulation(int config, String csvStockDataFileName, String csvExternalEventsFileName) {
        // code to set TradeExchange
        this.tradingExchange = new TradingExchange(1, csvStockDataFileName, csvExternalEventsFileName);
    }
}
