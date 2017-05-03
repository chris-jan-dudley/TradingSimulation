package tradingsimulation;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class FilterTreeViewer {
    ViewController controller;
    VBox container = new VBox(10);
    
    TreeItem<String> rootItem = new TreeItem<> ();
    TreeItem indexRoot = new TreeItem<>("Stock Index");
    TreeItem companiesRoot = new TreeItem<>("Companies");

    
    public FilterTreeViewer (ViewController controller) {
        this.controller = controller;
                
        Label title = new Label ("Chart filters");
        
        loadTree();
        
        TreeView<String> tree = new TreeView<> (rootItem);
        tree.setShowRoot(false); 
        tree.setCellFactory(e -> new CustomCell());
        
        container.setPadding(new Insets(10, 0, 0, 0));
        container.getChildren().addAll(title, tree);
        
        container.setMinHeight(400);
        container.setMinWidth(210);
    }
    
    private void loadTree () {
        TreeItem<String> index = new TreeItem<>("Index");
        indexRoot.getChildren().add(index);        
        indexRoot.setExpanded(true);
        
        rootItem.setExpanded(true);
        rootItem.getChildren().addAll(indexRoot, companiesRoot);
        
        TreeItem[] companies = new TreeItem[controller.getChart().getCompanyEntries().size()];
        for (int i = 0; i < companies.length; i++) {
            Company c = (Company) controller.getChart().getCompanyEntries().get(i).getObject();
            companies[i] = new TreeItem<>(c.getName());
        }        
        
        companiesRoot.getChildren().addAll((Object[]) companies);
        companiesRoot.setExpanded(true);
    }
    
    public Node getFxNode () {
        return container;
    }
    
    class CustomCell extends TreeCell<String> {
        TreeItem currItem;
        
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (isEmpty()) {
                setGraphic(null);
                setText(null);
            } else {       
                currItem = this.getTreeItem();
                if (currItem.isLeaf() && !item.equals("Companies") && !item.equals("Clients")) {       
                    HBox cellBox = new HBox(1);
                    
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(true);                                      
                    checkBox.selectedProperty().addListener((ov, oldValue, newValue) -> {
                        String catagory = (String) currItem.getParent().getValue();
                        ChartViewer chart = controller.getChart();
                        switch (catagory) {
                            case "Companies":                                  
                                for (ChartEntry entry : chart.getCompanyEntries()) {
                                    if (entry.getSeries().getName().equals(item)) {
                                        if (newValue) {                                                
                                            entry.getSeries().getNode().setStyle("-fx-stroke-width: 3;"); 
                                        } else {
                                            entry.getSeries().getNode().setStyle("-fx-stroke-width: 0;");  
                                        }
                                    }                                       
                                }                                    
                                break;
                            case "Stock Index":                                    
                                if (controller.getChart().getIndexEntry().getSeries().getName().equals(item)) {
                                    if (newValue) {                                                
                                        controller.getChart().getIndexEntry().getSeries().getNode().setStyle("-fx-stroke-width: 3;"); 
                                    } else {
                                        controller.getChart().getIndexEntry().getSeries().getNode().setStyle("-fx-stroke-width: 0;");  
                                    }
                                }
                                break;
                        }                           
                    });                   
                    
                    Label label = new Label(item);
                    
                    label.prefHeightProperty().bind(checkBox.heightProperty());
                    cellBox.getChildren().addAll(checkBox, label);
                    setGraphic(cellBox);
                    
                    setText(null);
                } else {
                    setText(item);
                }
            }
        }
    }   
}