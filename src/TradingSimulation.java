
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
    }
    
    public static TradingExchange getTradeExchange() {
        return tradingExchange;
    }

}
