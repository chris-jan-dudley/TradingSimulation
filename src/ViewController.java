
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author James G
 */
public class ViewController extends Application {

    BorderPane root = new BorderPane();
    SettingsViewer simSettings;
    ChartViewer stocksChart;
    FilterTreeViewer filterTree;
    EventViewer eventsLog;

    StockExchange exchange;
    Timeline ticker;

    @Override
    public void start(Stage primaryStage) {
        root.setRight(filterTree.getFxNode());
        root.setLeft(stocksChart.getFxNode());
        root.setBottom(new HBox(eventsLog.getFxNode(), simSettings.getFxNode()));

        Scene scene = new Scene(root, 880, 550);
        primaryStage.setTitle("Stock Market Simulation");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Stage is closing");
            ticker.stop();
        });
    }

    /**
     * Constructs the Ui classes and passes with required variables
     */
    public ViewController() {
        //All other classes    
        //exchange = new StockExchange(parameters);
        simSettings = new SettingsViewer(this);
        stocksChart = new ChartViewer(this, null);
        //stocksChart = new ChartViewer(this, exchange.getCompanies());
        filterTree = new FilterTreeViewer(this);
        eventsLog = new EventViewer(this, null);
        //eventsLog = new EventViewer(this, exchange.getEvents());

        ticker = new Timeline(new KeyFrame(Duration.minutes(15), e -> {
            update();
        }));
        ticker.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Start up the Ui
     */
    public static void readyGUI() {
        launch();
    }

    /**
     * Run the simulation
     */
    public void playSimulation() {
        ticker.play();
    }

    /**
     * Pause the siumulation
     */
    public void pauseSimulation() {
        ticker.pause();
    }

    /**
     * Set the speed multiplier of the simulation
     *
     * @param newSpeed The new speed
     */
    public void setSpeed(int newSpeed) {
        ticker.setRate(newSpeed);
    }

    /**
     * Update the simulation View and Model
     */
    public void update() {
        stocksChart.updateAllSeries();
        eventsLog.displayEventsForTick(exchange.getTick());
        exchange.tick();

        //All other update methods
    }

    /**
     * Stop the simulation and reset to the start
     */
    public void reset() {
        ticker.stop();
        eventsLog.clearEventsLog();
        stocksChart.clearStocksChart();

        //exchange.reset()
    }

    /**
     * Get the chart Ui element
     *
     * @return ChartViewer Chart class
     */
    public ChartViewer getChart() {
        return stocksChart;
    }

    /**
     * Get the event Ui element
     *
     * @return EventViewer Event class
     */
    public EventViewer getEventLog() {
        return eventsLog;
    }

    /**
     * Get the tree Ui element
     *
     * @return FilterTreeViewer Tree class
     */
    public FilterTreeViewer getFilterTree() {
        return filterTree;
    }

    /**
     * Get the settings Ui element
     *
     * @return SettingsViewer Settings class
     */
    public SettingsViewer getSettings() {
        return simSettings;
    }

    /**
     * Get the stock exchange class
     *
     * @return StockExchange
     */
    public StockExchange getExchange() {
        return exchange;
    }

    
    public void reportBadFile(String stock_Init_Data_was_not_valid_for_file_re) {
        throw new UnsupportedConfigurationValueError();
    }

}
