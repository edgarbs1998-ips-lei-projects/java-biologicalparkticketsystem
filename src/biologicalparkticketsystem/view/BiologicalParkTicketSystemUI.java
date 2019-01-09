/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.view;

import biologicalparkticketsystem.controller.ConfigManager;
import biologicalparkticketsystem.controller.CourseManager;
import biologicalparkticketsystem.controller.LoggerManager;
import biologicalparkticketsystem.controller.MapManager;
import biologicalparkticketsystem.controller.MapManagerException;
import biologicalparkticketsystem.model.Connection;
import biologicalparkticketsystem.model.PointOfInterest;
import digraph.IVertex;
import graphview.CircularSortedPlacementStrategy;
import graphview.GraphPanel;
import graphview.RandomPlacementStrategy;
import graphview.VertexPlacementStrategy;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    private Button payBtn, calculateBtn, statisticsBtn;
    private Label POITitle, pathTypeTitle;
    private List<Integer> selectedPOI = new ArrayList<>();
    public int index;
    
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
            CourseManager courseManager = new CourseManager(mapManager);
            
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
        ListView<String> list = new ListView<>();
        index = 0;
        //make foreach to get all points of interest and add radio buttons ofr eahc point
        for(IVertex<PointOfInterest> v : mapManager.getDiGraph().vertices()){
            //cant create dynamic variables
            CheckBox  checkBox = new CheckBox (v.element().toString());
            //CheckBox checkbox = (CheckBox)rightMenu.getChildren().get(index + 4); TEST
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    // TODO Auto-generated method stub
                    if(newValue){
                        selectedPOI.add(v.element().getPoiId());
                        System.out.println("tick" + selectedPOI.size());

                    }else{
                        selectedPOI.remove(index);
                        System.out.println("untick" + selectedPOI.size());
                    }
                }
            });
            rightMenu.getChildren().add(checkBox);
            index++;
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
        });
        
        statisticsBtn = new Button("Statistics");
        statisticsBtn.setOnAction((event) -> {
            StatisticsUI statisticsWindow = new StatisticsUI();
            BorderPane root = new BorderPane();
            root.setCenter(statisticsWindow.getChart());
            root.setPadding(new Insets(20));
            Stage statisticsStage = new Stage();
            statisticsStage.setTitle("Statistics");
            statisticsStage.setScene(new Scene(root, 400, 300));
            statisticsStage.show();
        });
        calculateBtn = new Button("Calculate"); // TODO Displaye error message to user on error
        
        ObservableList<String> options = FXCollections.observableArrayList("Shortest","Cheapest","Most visited");
        PathComboBox = new ComboBox(options);
        PathComboBox.setValue("Path");
        bottomMenu.getChildren().addAll(PathComboBox, calculateBtn, statisticsBtn, payBtn);
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
