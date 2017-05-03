package tradingsimulation;

import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class SettingsViewer {
    ViewController controller;
    
    VBox container = new VBox(10);
    
    private DatePicker startDate = new DatePicker(LocalDate.of(2017, 1, 1));
    private DatePicker endDate = new DatePicker(LocalDate.of(2018, 1, 1));
    
    public SettingsViewer (ViewController controller) {
        this.controller = controller;
        
        startDate.setMaxWidth(117);
        startDate.valueProperty().addListener((ov, oldValue, newValue) -> {            
            if (newValue.compareTo(endDate.getValue()) > 0) {
                startDate.setValue(endDate.getValue());
            } 
            controller.getChart().updateDateBounds();
        });  
        
        endDate.setMaxWidth(122);
        endDate.valueProperty().addListener((ov, oldValue, newValue) -> {            
            if (newValue.compareTo(startDate.getValue()) < 0) {
                endDate.setValue(startDate.getValue());
            }  
            controller.getChart().updateDateBounds();
        });
                
        HBox startWrapper = new HBox(7);
        startWrapper.getChildren().addAll(
                new Label("Start Date: "),
                startDate
        );
        
        HBox endWrapper = new HBox(7);
        endWrapper.getChildren().addAll(
                new Label("End Date: "),
                endDate
        );
               
        TextField speedInput = new TextField("");
        speedInput.setMaxWidth(80);
        speedInput.setPromptText("Speed");
        speedInput.textProperty().addListener((ov, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                speedInput.setText(newValue.replaceAll("[^\\d]", ""));                
            }
        });
        
        Button speedSet = new Button("Set");
        speedSet.setMaxWidth(40);
        speedSet.setOnAction(e -> {
            int speedNum = 0;
            try {
                speedNum = Integer.parseInt(speedInput.getText());
                controller.setSpeed(speedNum);
            } catch (NumberFormatException n) {}
        });
        
        
        Button playPauseBut = new Button("▶");
        playPauseBut.setOnAction(e -> {
            startDate.setDisable(true);
            endDate.setDisable(true);
            
            if (playPauseBut.getText().equals("▶")) {
                playPauseBut.setText("||");
                controller.playSimulation();
            } else {
                playPauseBut.setText("▶");
                controller.pauseSimulation();
            }  
        });
        
        Button stopBut = new Button("■");
        stopBut.setOnAction(e -> {
            startDate.setDisable(false);
            endDate.setDisable(false);
            playPauseBut.setText("▶");
            controller.reset();
        });
        
        HBox controls = new HBox(7);
        controls.getChildren().addAll(
                playPauseBut,
                stopBut,
                speedInput,
                speedSet
        );
        
        container.getChildren().addAll(
                startWrapper,
                endWrapper,
                controls
        );
                
        container.setMinWidth(200);      
        container.setMinHeight(150);       
        container.setPadding(new Insets(10, 7, 10, 7));
               
    }
    
    public Node getFxNode () {
        return container;
    }
    
    public LocalDate getStartDate () {
        return startDate.getValue();
    }
    
    public LocalDate getEndDate () {
        return endDate.getValue();
    }    
}