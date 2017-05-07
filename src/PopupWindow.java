
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupWindow {
    public static void display(String message, int width) {
        Stage popupStage = new Stage();
        popupStage.setResizable(false);
        
        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        
        Label infoLabel = new Label(message);

        Button okButton = new Button("OK");
        okButton.setMinWidth(40);
        okButton.setOnAction(e -> popupStage.close());
        
        layout.getChildren().addAll(infoLabel, okButton);
        
        Scene scene = new Scene(layout, width, 85);           
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Application Error");
        popupStage.setScene(scene);
        popupStage.showAndWait();
        
    }
    
}
