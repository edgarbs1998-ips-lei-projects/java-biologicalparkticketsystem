package biologicalparkticketsystem.view;

import biologicalparkticketsystem.controller.MainController;
import biologicalparkticketsystem.model.MainModel;
import biologicalparkticketsystem.model.course.Connection;
import biologicalparkticketsystem.model.course.CourseManager;
import biologicalparkticketsystem.model.course.PointOfInterest;
import digraph.IVertex;
import graphview.CircularSortedPlacementStrategy;
import graphview.GraphPanel;
import graphview.VertexPlacementStrategy;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainView implements IMainView {
    
    // Components
    private GraphPanel<PointOfInterest, Connection> graphPanel;
    private ToggleGroup typeToggleGroup;
    private Map<CheckBox, IVertex<PointOfInterest>> pointsOfInterestCheckBoxes;
    private ComboBox criteriaComboBox;
    private Button statisticsButton, calculateButton, undoButton, issueTicketButton;
    private Label costValueLabel, distanceValueLabel;
    
    // Scene
    private Scene scene;
    
    // Model
    private MainModel mainModel;
    
    public MainView(MainModel model) {
        super();
        
        this.pointsOfInterestCheckBoxes = new HashMap<>();
        this.mainModel = model;
        
        BorderPane borderPane = new BorderPane();
        
        Pane graphPanelContent = initGraphPanel();
        borderPane.setCenter(graphPanelContent);
        Pane rightMenuContent = initRightMenuComponents();
        borderPane.setRight(rightMenuContent);
        Pane bottomMenuContent = initBottomMenuComponents();
        borderPane.setBottom(bottomMenuContent);
        
        borderPane.setPadding(new Insets(10));
        
        scene = new Scene(borderPane, 800, 600);
    }
    
    private Pane initGraphPanel() {
        VertexPlacementStrategy strategy = new CircularSortedPlacementStrategy();
        graphPanel = new GraphPanel<>(this.mainModel.getGraph(), strategy);
        return graphPanel;
    }
    
    private Pane initRightMenuComponents() {
        VBox content = new VBox();
        content.setSpacing(20);
        
        statisticsButton = new Button("Statistics");
        content.getChildren().add(statisticsButton);
        
        Label pathTypeLabel = new Label("Path Type");
        pathTypeLabel.setPadding(new Insets(30, 0, 0, 0));
        pathTypeLabel.setStyle("-fx-font-weight: bold");
        content.getChildren().add(pathTypeLabel);
        typeToggleGroup = new ToggleGroup();
        RadioButton footRadioButton = new RadioButton("Foot");
        footRadioButton.setToggleGroup(typeToggleGroup);
        footRadioButton.setSelected(true);
        content.getChildren().add(footRadioButton);
        RadioButton bicycleRadioButton = new RadioButton("Bicycle");
        bicycleRadioButton.setToggleGroup(typeToggleGroup);
        content.getChildren().add(bicycleRadioButton);
        
        Label pointsOfInterestgLabel = new Label("Points of Interest");
        pointsOfInterestgLabel.setPadding(new Insets(30, 0, 0, 0));
        pointsOfInterestgLabel.setStyle("-fx-font-weight: bold");
        content.getChildren().add(pointsOfInterestgLabel);
        for(IVertex<PointOfInterest> poi : this.mainModel.getVertices()) {
            if (poi.element() == this.mainModel.getStartPointOfInterest()) continue;
            
            CheckBox checkBox = new CheckBox(poi.element().getPoiName());
            pointsOfInterestCheckBoxes.put(checkBox, poi);
            content.getChildren().add(checkBox);
        }
        
        return content;
    }
    
    private Pane initBottomMenuComponents() {
        HBox content = new HBox();
        content.setAlignment(Pos.CENTER_LEFT);
        content.setSpacing(20);
        content.setPadding(new Insets(10));
        content.setStyle("-fx-border-color: black; -fx-border-insets: 2; -fx-border-width: 1;");
        
        Label criteriaLabel = new Label("Criteria");
        criteriaLabel.setStyle("-fx-font-weight: bold");
        criteriaLabel.setPadding(new Insets(0, -15, 0, 0));
        content.getChildren().add(criteriaLabel);
        ObservableList<CourseManager.Criteria> criteriaOptions = FXCollections.observableArrayList(
                CourseManager.Criteria.COST,
                CourseManager.Criteria.DISTANCE
        );
        criteriaComboBox = new ComboBox(criteriaOptions);
        criteriaComboBox.setValue(CourseManager.Criteria.COST);
        content.getChildren().add(criteriaComboBox);
        
        calculateButton = new Button("Calculate");
        content.getChildren().add(calculateButton);
        
        undoButton = new Button("Undo");
        undoButton.setDisable(true);
        content.getChildren().add(undoButton);
        
        issueTicketButton = new Button("Issue Ticket");
        issueTicketButton.setDisable(true);
        content.getChildren().add(issueTicketButton);
        
        Label costLabel = new Label("Total cost (€):");
        costLabel.setStyle("-fx-font-weight: bold");
        costLabel.setPadding(new Insets(0, -15, 0, 0));
        content.getChildren().add(costLabel);
        costValueLabel = new Label("0");
        content.getChildren().add(costValueLabel);
        
        Label distanceLabel = new Label("Total distance (meters):");
        distanceLabel.setStyle("-fx-font-weight: bold");
        distanceLabel.setPadding(new Insets(0, -15, 0, 0));
        content.getChildren().add(distanceLabel);
        distanceValueLabel = new Label("0");
        content.getChildren().add(distanceValueLabel);
        
        return content;
    }
    
    @Override
    public void update(Observable observable, Object object) {
        
    }
    
    @Override
    public Scene getScene() {
        return this.scene;
    }
    
    @Override
    public void setTriggers(MainController controller) {
        // Points of interest checkboxes listener
        for (CheckBox checkBox : pointsOfInterestCheckBoxes.keySet()) {
            checkBox.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
                            controller.changePointOfInterest(pointsOfInterestCheckBoxes.get(checkBox), oldValue, newValue));
        }
        
        // Buttons actions
        issueTicketButton.setOnAction(event -> controller.issueTicket());
        calculateButton.setOnAction(event -> controller.calculatePath());
        statisticsButton.setOnAction(event -> controller.openStatistics());
        undoButton.setOnAction(event -> controller.undoCalculate());
    }
    
    @Override
    public void plotGraph() {
        this.graphPanel.plotGraph();
        //view.setStartPoiColor();
    }
    
    @Override
    public void setGraphVertexColor(IVertex<PointOfInterest> poi, Color fill, Color stroke) {
        graphPanel.setVertexColor(poi, fill, stroke);
    }
    
}
