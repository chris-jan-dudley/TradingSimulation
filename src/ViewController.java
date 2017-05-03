
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

    public static void readyGUI() {
        launch();
    }

    public void playSimulation() {
        ticker.play();
    }

    public void pauseSimulation() {
        ticker.pause();
    }

    public void setSpeed(int newSpeed) {
        ticker.setRate(newSpeed);
    }

    public void update() {
        stocksChart.updateAllSeries();
        eventsLog.displayEventsForTick(exchange.getTick());
        exchange.tick();

        //All other update methods
    }

    public void reset() {
        ticker.stop();
        eventsLog.clearEventsLog();
        stocksChart.clearStocksChart();

        //exchange.reset()
    }

    public ChartViewer getChart() {
        return stocksChart;
    }

    public EventViewer getEventLog() {
        return eventsLog;
    }

    public FilterTreeViewer getFilterTree() {
        return filterTree;
    }

    public SettingsViewer getSettings() {
        return simSettings;
    }

    public StockExchange getExchange() {
        return exchange;
    }

}
