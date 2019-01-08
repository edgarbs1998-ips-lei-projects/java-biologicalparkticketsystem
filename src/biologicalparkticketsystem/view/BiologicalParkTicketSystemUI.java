/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.view;

import biologicalparkticketsystem.controller.ConfigManager;
import biologicalparkticketsystem.controller.LoggerManager;
import biologicalparkticketsystem.controller.MapManager;
import biologicalparkticketsystem.controller.MapManagerException;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Luis Varela
 */
public class BiologicalParkTicketSystemUI implements BiologicalParkTicketSystemInterface{
    //Layouts
    private GraphPanel<PointOfInterest, Connection> graphView;
    private VBox rightMenu;
    private HBox bottomMenu;
    
    //Components
    private ToggleGroup group;
    private RadioButton rbFoot, rbBicycle;
    private ComboBox PathComboBox;
    private Button payBtn, calculateBtn;
    private Label POITitle, pathTypeTitle;
    
    
    public BiologicalParkTicketSystemUI(){
        innitComponents();
    }
    
    @Override
    public void innitComponents(){
        ConfigManager config = ConfigManager.getInstance();
        config.init();
        
        LoggerManager logger = LoggerManager.getInstance();
        logger.init();
        
        MapManager mapManager;
        try {
            mapManager = new MapManager(config.getProperties().getProperty("map.file"));
        } catch (MapManagerException ex) {
            LoggerManager.getInstance().log(ex);
            return;
        }
        
        //instanciate strategy
        //VertexPlacementStrategy strategy = new CircularPlacementStrategy();
        VertexPlacementStrategy strategy = new CircularSortedPlacementStrategy();
        //VertexPlacementStrategy strategy = new RandomPlacementStrategy();
        
        //graph viewer container
        graphView = new GraphPanel<>(mapManager.getDiGraph(), strategy);
        
        //right menu container
        rightMenu = new VBox();
        pathTypeTitle = new Label("Path type");
        pathTypeTitle.setStyle("-fx-font-weight: bold");
        rightMenu.getChildren().add(pathTypeTitle);
        group = new ToggleGroup();
        rbFoot = new RadioButton("Foot");
        rbFoot.setToggleGroup(group);
        rbFoot.setSelected(true);
        rbBicycle = new RadioButton("Bicycle");
        rbBicycle.setToggleGroup(group);
        rightMenu.getChildren().addAll(rbFoot, rbBicycle);
        rightMenu.setSpacing(10);
        rightMenu.setSpacing(20);
        POITitle = new Label("Points of Interest");
        POITitle.setStyle("-fx-font-weight: bold");
        rightMenu.getChildren().add(POITitle);
        //make foreach to get all points of interest and add radio buttons ofr eahc point
        for(IVertex<PointOfInterest> v : mapManager.getDiGraph().vertices()){
            CheckBox  checkBox = new CheckBox (v.element().toString());
            rightMenu.getChildren().add(checkBox);
        }
        //bottom buttons container
        bottomMenu = new HBox();
        payBtn = new Button("Issue ticket");
        payBtn.setOnAction((event) -> {
            PaymentUI paymentWindow = new PaymentUI();
            BorderPane root = new BorderPane();
            root.setTop(paymentWindow.getTitle());
            root.setCenter(paymentWindow.getCenter());
            root.setBottom(paymentWindow.getbuttonMenu());
            root.setPadding(new Insets(20));
            Stage paymentStage = new Stage();
            paymentStage.setTitle("Issue ticket");
            paymentStage.setScene(new Scene(root, 400, 300));
            paymentStage.show();
            //System.out.println("Button Action");
        });
        calculateBtn = new Button("Calculate"); // TODO Displaye error message to user on error
        ObservableList<String> options = FXCollections.observableArrayList("Shortest","Cheapest","Most visited");
        PathComboBox = new ComboBox(options);
        PathComboBox.setValue("Path");
        bottomMenu.getChildren().addAll(PathComboBox, calculateBtn, payBtn);
        bottomMenu.setAlignment(Pos.CENTER_LEFT);
        bottomMenu.setSpacing(10);
    }
    
    @Override
    public GraphPanel<PointOfInterest, Connection> getGraphView(){
        return this.graphView;
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
