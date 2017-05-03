
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Chris
 */
public class EventViewer {

    ViewController controller;

    ScrollPane scrollablePane;
    VBox eventStack;
    ArrayList<ExternalEvent> events;

    /**
     *
     * @param controller
     * @param events
     */
    public EventViewer(ViewController controller, ArrayList<ExternalEvent> events) {
        this.controller = controller;
        this.events = events;

        eventStack = new VBox(6);
        scrollablePane = new ScrollPane(eventStack);

        scrollablePane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollablePane.setMinWidth(680);
        scrollablePane.setMinHeight(150);
        scrollablePane.setPadding(new Insets(10));
    }

    /**
     *
     * @return
     */
    public Node getFxNode() {
        return scrollablePane;
    }

    /**
     *
     * @param tick
     */
    public void displayEventsForTick(int tick) {
        for (ExternalEvent event : events) {
            if (event.getFromTick() == tick) {
                //logEvent("[" + event.getDate().toLocaleString().substring(0, 11) + "]: ", event.getNature(), " " + event.getAction());
            }
        }
    }

    /**
     *
     */
    public void clearEventsLog() {
        eventStack.getChildren().removeAll(eventStack.getChildren());
    }

    /**
     *
     * @param date
     * @param nature
     * @param action
     */
    public void logEvent(String date, String nature, String action) {
        eventStack.getChildren().add(
                new Label(date + ": " + nature + ", " + action)
        );
    }
}
