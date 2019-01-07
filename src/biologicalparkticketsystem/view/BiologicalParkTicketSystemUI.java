/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.view;

import biologicalparkticketsystem.controller.ConfigManager;
import biologicalparkticketsystem.controller.MapManager;
import biologicalparkticketsystem.model.Connection;
import biologicalparkticketsystem.model.PointOfInterest;
import digraph.IVertex;
import graphview.CircularSortedPlacementStrategy;
import graphview.GraphPanel;
import graphview.VertexPlacementStrategy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author Luis Varela
 */
public class BiologicalParkTicketSystemUI implements BiologicalParkTicketSystemInterface{
    //config attribute
    private ConfigManager config;
    
    //strategy to show digraph
    private VertexPlacementStrategy strategy;
    
    //Layouts
    private VBox graphViewer;
    private VBox rightMenu;
    private HBox bottomMenu;
    
    
    //Components
    private ToggleGroup group;
    private RadioButton rbFoot, rbBicycle;
    private ComboBox numberComboBox, PathComboBox;
    private Button payBtn, calculateBtn;
    
    
    public BiologicalParkTicketSystemUI(){
        innitComponents();
    }
    
    @Override
    public void innitComponents(){
        //get configs
        config = ConfigManager.getInstance();
        
        //model??
        MapManager mapManager = new MapManager(config.getProperties().getProperty("mapFile"));
        
        //instanciate strategy
        strategy = new CircularSortedPlacementStrategy();
        
        //graph viewer container
        graphViewer = new VBox();
        graphViewer.setPadding(new Insets(20));
        GraphPanel<PointOfInterest, Connection> graphView = new GraphPanel<>(mapManager.getDiGraph(), strategy);
        graphViewer.getChildren().add(graphView);
        
        //right menu container
        rightMenu = new VBox();
        group = new ToggleGroup();
        rbFoot = new RadioButton("Foot");
        rbFoot.setToggleGroup(group);
        rbFoot.setSelected(true);
        rbBicycle = new RadioButton("Bicycle");
        rbBicycle.setToggleGroup(group);
        numberComboBox = new ComboBox();
        numberComboBox.setValue("Number of people");
        numberComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30);
        rightMenu.getChildren().addAll(rbFoot, rbBicycle, numberComboBox);
        rightMenu.setSpacing(10);
        
        //make foreach to get all points of interest and add radio buttons ofr eahc point
        for(IVertex<PointOfInterest> v : mapManager.getDiGraph().vertices()){
            RadioButton radio = new RadioButton(v.toString());
            rightMenu.getChildren().add(radio);
            
        }
        //bottom buttons container
        bottomMenu = new HBox();
        payBtn = new Button("Pay");
        calculateBtn = new Button("Calculate");
        ObservableList<String> options = FXCollections.observableArrayList("Shortest","Cheapest","Most visited");
        PathComboBox = new ComboBox(options);
        PathComboBox.setValue("Path");
        bottomMenu.getChildren().addAll(PathComboBox, calculateBtn, payBtn);
        bottomMenu.setAlignment(Pos.CENTER_LEFT);
    }
    
    @Override
    public VBox getGraphViewer(){
        return this.graphViewer;
    }
    
    @Override
    public VBox getRightMenu(){
        return this.rightMenu;
    }
    
    @Override
    public HBox getbottomMenu(){
        return this.bottomMenu;
    }
}
