
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author James G
 */
public class ChartViewer {

    private ViewController controller;

    private VBox container = new VBox();
    LineChart<Number, Number> lineChart;

    private NumberAxis dateAxis = new NumberAxis();
    private NumberAxis sharePriceAxis = new NumberAxis();

    private DatePicker viewFromDate = new DatePicker(LocalDate.of(2017, 1, 1));
    private DatePicker viewToDate = new DatePicker(LocalDate.of(2018, 1, 1));

    private ArrayList<ChartEntry> companyEntries = new ArrayList<>();
    private ChartEntry indexEntry;

    /**
     * Creates the date and share price axis and sets the bounds to the correct number of the days,
     * as well as the view range date pickers
     *
     * @param controller The ViewController that created the chart
     * @param companies The Companies from the model
     */
    public ChartViewer(ViewController controller, ArrayList<Company> companies) {
        this.controller = controller;

        dateAxis.setLabel("Days Since Simulation Start");
        dateAxis.setAutoRanging(false);
        dateAxis.setTickUnit(50);
        sharePriceAxis.setLabel("Share Price in Pence");
        sharePriceAxis.setAnimated(false);

        updateDateBounds();
        viewFromDate.setOnAction(e -> {
            updateDateBounds();
            if (viewFromDate.getValue().compareTo(viewToDate.getValue()) > 0) {
                viewFromDate.setValue(viewToDate.getValue());
            }
        });

        viewToDate.setOnAction(e -> {
            updateDateBounds();
            if (viewToDate.getValue().compareTo(viewFromDate.getValue()) < 0) {
                viewToDate.setValue(viewFromDate.getValue());
            }
        });

        lineChart = new LineChart<>(dateAxis, sharePriceAxis);
        lineChart.setTitle("Stock Monitoring");

        lineChart.setMaxHeight(360);
        lineChart.setPrefWidth(680);
        lineChart.setLegendSide(Side.RIGHT);
        lineChart.setLegendVisible(false);
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        lineChart.getData().add(testData);

        if (companies != null) {
            addCompaniesToChart(companies);
        }

        HBox chartParameters = new HBox(7);
        chartParameters.setPadding(new Insets(0, 0, 0, 70));
        chartParameters.getChildren().addAll(
                new Label("View From: "),
                viewFromDate,
                new Label("View To: "),
                viewToDate
        );

        container.getChildren().addAll(lineChart, chartParameters);
    }
  
    /**
     * Adds a random double at the specified tick value
     * 
     * @param tick Tick value to add the data at
     */
    XYChart.Series testData = new XYChart.Series();
    Random ran = new Random();
    public void addDataTest(double tick) {
        testData.getData().add(new XYChart.Data(tick, (ran.nextDouble() * 1) + 5));
    }

    /**
     * Checks the validity of the date pickers and resizes the date axis accordingly
     */
    public void updateDateBounds() {
        LocalDate startDate = controller.getSettings().getStartDate();
        LocalDate endDate = controller.getSettings().getEndDate();
        if (viewFromDate.getValue().compareTo(startDate) < 0) {
            viewFromDate.setValue(startDate);
        }

        if (viewToDate.getValue().compareTo(endDate) > 0) {
            viewToDate.setValue(endDate);
        }

        dateAxis.setLowerBound((int) ChronoUnit.DAYS.between(startDate, viewFromDate.getValue()));
        dateAxis.setUpperBound((int) ChronoUnit.DAYS.between(startDate, endDate) - ChronoUnit.DAYS.between(viewToDate.getValue(), endDate));
    }

    /**
     * Gets the JavaFx node containing the chart and all associated elements
     * 
     * @return Node JavaFx node containing the chart and controls
     */
    public Node getFxNode() {
        return container;
    }

    /**
     * Adds the provided list of companies to the chart with their starting share prices
     *
     * @param companies Companies to add to the chart
     */
    public void addCompaniesToChart(ArrayList<Company> companies) {
        double sumSharePrice = 0;
        for (Company companyObj : companies) {
            XYChart.Series companyData = new XYChart.Series();
            companyData.setName(companyObj.getName());
            companyData.getData().add(new XYChart.Data(0, companyObj.getSharePrice()));
            lineChart.getData().add(companyData);
            companyEntries.add(new ChartEntry(companyObj, companyData));
            sumSharePrice += companyObj.getSharePrice();
        }

        XYChart.Series indexData = new XYChart.Series();
        indexData.setName("Index");
        indexData.getData().add(new XYChart.Data(0, sumSharePrice / companies.size()));
        System.out.println(indexData.getData().get(0));
        lineChart.getData().add(indexData);
        indexEntry = new ChartEntry(null, indexData);

    }

    /**
     * Clears the current series contained in the chart
     */
    public void clearStocksChart() {
        companyEntries.clear();
        lineChart.getData().clear();
    }

    /**
     * Gets the latest share prices from the stock market data and adds them to the chart, 
     * then computes the average for use in the index
     */
    public void updateAllSeries() {
        double day = controller.getExchange().getCurrTick() / 28;
        double sumSharePrice = 0;
        for (ChartEntry entry : companyEntries) {
            Company entryComp = (Company) entry.getObject();
            for (Company modelComp : controller.getExchange().getStockExchangeData().getLatestRow().getCompanyPrices()) {
                if (modelComp.getName().equals(entryComp.getName())) {
                    entry.getSeries().getData().add(new XYChart.Data(day, modelComp.getSharePrice()));
                    sumSharePrice += entryComp.getSharePrice(); 
                }                           
            }

        }
        indexEntry.getSeries().getData().add(new XYChart.Data(day, sumSharePrice / companyEntries.size()));

    }

    /**
     * Gets the company entries used in the chart
     *
     * @return ArrayList<ChartEntry> List of chart entries 
     */
    public ArrayList<ChartEntry> getCompanyEntries() {
        return companyEntries;
    }

    /**
     * Get the specific chart entry for the index
     * 
     * @return ChartEntry The index chart entry
     */
    public ChartEntry getIndexEntry() {
        return indexEntry;
    }
}
