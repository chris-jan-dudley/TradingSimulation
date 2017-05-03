
import javafx.scene.chart.XYChart;

/**
 *
 * @author James
 */
public class ChartEntry {

    private Object object;
    private XYChart.Series series;

    /**
     *
     * @param _object
     * @param _series
     */
    public ChartEntry(Object _object, XYChart.Series _series) {
        object = _object;
        series = _series;
    }

    /**
     *
     * @return
     */
    public Object getObject() {
        return object;
    }

    /**
     *
     * @return
     */
    public XYChart.Series<Number, Number> getSeries() {
        return series;
    }

}
