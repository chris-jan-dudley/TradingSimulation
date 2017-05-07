
import javafx.scene.chart.XYChart;

/**
 *
 * @author James G
 */
public class ChartEntry {

    private Object object;
    private XYChart.Series series;

    /**
     * Initalises variables
     *
     * @param _object The company of the date series (Changes to code needed)
     * @param _series The data series
     */
    public ChartEntry(Object _object, XYChart.Series _series) {
        object = _object;
        series = _series;
    }

    /**
     * Get the Company of this entry
     * 
     * @return Company
     */
    public Object getObject() {
        return object;
    }

    /**
     * Get the Series of this entry
     *
     * @return Series
     */
    public XYChart.Series<Number, Number> getSeries() {
        return series;
    }

}
