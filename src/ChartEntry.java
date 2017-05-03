
import javafx.scene.chart.XYChart;

/**
 *
 * @author James
 */
public class ChartEntry {

    private Object object;
    private XYChart.Series series;

    public ChartEntry(Object _object, XYChart.Series _series) {
        object = _object;
        series = _series;
    }

    public Object getObject() {
        return object;
    }

    public XYChart.Series<Number, Number> getSeries() {
        return series;
    }

}
