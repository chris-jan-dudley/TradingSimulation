
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author James G
 */
public class EventViewer {

    ViewController controller;

    ScrollPane scrollablePane;
    VBox eventStack;
    ArrayList<ExternalEvent> events;

    /**
     * Creates the scrollable textbox used to list the events logged
     *
     * @param controller The ViewController that created the log
     * @param events List of event that occur sometime in the simulation
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
     * Gets the JavaFx node containing the chart and all associated elements
     * 
     * @return Node Javafx node containing the chart and controls
     */
    public Node getFxNode() {
        return scrollablePane;
    }

    /**
     * Checks if any of the events occur on this tick, if so adds them to the textbox
     *
     * @param tick Tick to check
     */
    public void displayEventsForTick(int tick) {
        for (ExternalEvent event : events) {
            if (event.getFromTick() == tick) {
                //logEvent("[" + event.getDate().toLocaleString().substring(0, 11) + "]: ", event.getNature(), " " + event.getAction());
            }
        }
    }

    /**
     * Removes all messages from the textbox
     */
    public void clearEventsLog() {
        eventStack.getChildren().removeAll(eventStack.getChildren());
    }

    /**
     * Creates a net event in the textbox with the listed parameters
     *
     * @param date Date of the event
     * @param nature Cause of the event
     * @param action Results of the event
     */
    public void logEvent(String date, String nature, String action) {
        eventStack.getChildren().add(
                new Label(date + ": " + nature + ", " + action)
        );
    }
}
