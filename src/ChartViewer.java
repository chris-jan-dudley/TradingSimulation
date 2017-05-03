
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
 * @author Chris
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
     *
     * @param controller
     * @param companies
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

    XYChart.Series testData = new XYChart.Series();
    Random ran = new Random();

    /**
     *
     * @param tick
     */
    public void addDataTest(double tick) {
        testData.getData().add(new XYChart.Data(tick, (ran.nextDouble() * 1) + 5));
    }

    /**
     *
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
     *
     * @return
     */
    public Node getFxNode() {
        return container;
    }

    /**
     *
     * @param companies
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
     *
     */
    public void clearStocksChart() {
        companyEntries.clear();
        lineChart.getData().clear();
    }

    /**
     *
     */
    public void updateAllSeries() {
        double day = controller.getExchange().getTick() / 28;
        double sumSharePrice = 0;
        for (ChartEntry entry : companyEntries) {
            Company entryComp = (Company) entry.getObject();
            //for (Company modelComp : controller.getExchange().getStockExchangeData().getLatestRow().getCompanyPrices) {
            //    if (modelComp.getName().equals(entryComp.getName())) {
            //        entry.getSeries().getData().add(new XYChart.Data(day, modelComp.getSharePrice()));
            //        sumSharePrice += entryComp.getSharePrice(); 
            //    }                           
            //}

        }
        indexEntry.getSeries().getData().add(new XYChart.Data(day, sumSharePrice / companyEntries.size()));

    }

    /**
     *
     * @return
     */
    public ArrayList<ChartEntry> getCompanyEntries() {
        return companyEntries;
    }

    /**
     *
     * @return
     */
    public ChartEntry getIndexEntry() {
        return indexEntry;
    }
}
