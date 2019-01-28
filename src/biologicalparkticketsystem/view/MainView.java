package biologicalparkticketsystem.view;

import biologicalparkticketsystem.controller.MainController;
import biologicalparkticketsystem.model.MainModel;
import biologicalparkticketsystem.model.course.CalculatedPath;
import biologicalparkticketsystem.model.course.Connection;
import biologicalparkticketsystem.model.course.CriteriaStrategyCost;
import biologicalparkticketsystem.model.course.CriteriaStrategyDistance;
import biologicalparkticketsystem.model.course.ICriteriaStrategy;
import biologicalparkticketsystem.model.course.PointOfInterest;
import digraph.IEdge;
import digraph.IVertex;
import graphview.CircularSortedPlacementStrategy;
import graphview.GraphPanel;
import graphview.VertexPlacementStrategy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
/**
 * Main view class where the components of the main view get initialized
 */
public class MainView implements IMainView {
    
    // Components
    private GraphPanel<PointOfInterest, Connection> graphPanel;
    private ToggleGroup typeToggleGroup;
    private RadioButton footRadioButton, bicycleRadioButton;
    private Map<CheckBox, IVertex<PointOfInterest>> pointsOfInterestCheckBoxes;
    private ComboBox criteriaComboBox;
    private Button statisticsButton, calculateButton, undoButton, issueTicketButton;
    private Label costValueLabel, distanceValueLabel;
    
    // Scene
    private Scene scene;
    
    // Model
    private MainModel mainModel;
    
    public MainView(MainModel model) {
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
        
        this.scene = new Scene(borderPane, 800, 600);
    }
    /**
     * Method to initialize the graph panel
    */
    private Pane initGraphPanel() {
        VertexPlacementStrategy strategy = new CircularSortedPlacementStrategy();
        this.graphPanel = new GraphPanel<>(this.mainModel.getGraph(), strategy);
        return this.graphPanel;
    }
    /**
     * Method to initialize the right menu itens
     */
    private Pane initRightMenuComponents() {
        VBox content = new VBox();
        content.setSpacing(20);
        
        this.statisticsButton = new Button("Statistics");
        content.getChildren().add(this.statisticsButton);
        
        Label pathTypeLabel = new Label("Path Type");
        pathTypeLabel.setPadding(new Insets(30, 0, 0, 0));
        pathTypeLabel.setStyle("-fx-font-weight: bold");
        content.getChildren().add(pathTypeLabel);
        this.typeToggleGroup = new ToggleGroup();
        this.footRadioButton = new RadioButton("Foot");
        this.footRadioButton.setToggleGroup(this.typeToggleGroup);
        this.footRadioButton.setSelected(true);
        content.getChildren().add(this.footRadioButton);
        this.bicycleRadioButton = new RadioButton("Bicycle");
        this.bicycleRadioButton.setToggleGroup(this.typeToggleGroup);
        content.getChildren().add(this.bicycleRadioButton);
        
        Label pointsOfInterestgLabel = new Label("Points of Interest");
        pointsOfInterestgLabel.setPadding(new Insets(30, 0, 0, 0));
        pointsOfInterestgLabel.setStyle("-fx-font-weight: bold");
        content.getChildren().add(pointsOfInterestgLabel);
        for(IVertex<PointOfInterest> poi : this.mainModel.getGraph().vertices()) {
            if (poi.element() == this.mainModel.getStartVertex().element()) continue;
            
            CheckBox checkBox = new CheckBox(poi.element().getPoiName());
            this.pointsOfInterestCheckBoxes.put(checkBox, poi);
            content.getChildren().add(checkBox);
        }
        
        return content;
    }
    /**
     * Method to initialize the bottom menu itens
     */
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
        ObservableList<ICriteriaStrategy> criteriaOptions = FXCollections.observableArrayList(
                new CriteriaStrategyCost(),
                new CriteriaStrategyDistance()
        );
        this.criteriaComboBox = new ComboBox(criteriaOptions);
        this.criteriaComboBox.setValue(new CriteriaStrategyCost());
        content.getChildren().add(this.criteriaComboBox);
        
        this.calculateButton = new Button("Calculate");
        content.getChildren().add(this.calculateButton);
        
        this.undoButton = new Button("Undo");
        this.undoButton.setDisable(true);
        content.getChildren().add(this.undoButton);
        
        this.issueTicketButton = new Button("Issue Ticket");
        this.issueTicketButton.setDisable(true);
        content.getChildren().add(this.issueTicketButton);
        
        Label costLabel = new Label("Total cost (€):");
        costLabel.setStyle("-fx-font-weight: bold");
        costLabel.setPadding(new Insets(0, -15, 0, 0));
        content.getChildren().add(costLabel);
        this.costValueLabel = new Label("0");
        content.getChildren().add(this.costValueLabel);
        
        Label distanceLabel = new Label("Total distance (meters):");
        distanceLabel.setStyle("-fx-font-weight: bold");
        distanceLabel.setPadding(new Insets(0, -15, 0, 0));
        content.getChildren().add(distanceLabel);
        this.distanceValueLabel = new Label("0");
        content.getChildren().add(this.distanceValueLabel);
        
        return content;
    }
    /**
     * Method to update the values of some text fields
     */
    @Override
    public void update(Observable observable, Object object) {
        CalculatedPath calculatedPath = (CalculatedPath) object;
        
        this.resetGraphColors();
        
        // Set graph vertexes colors with the new calculated path
        Iterator<PointOfInterest> pointsOfInterestIterator = calculatedPath.getPointsOfInterest().iterator();
        PointOfInterest firstPoint = pointsOfInterestIterator.next();
        PointOfInterest fromPoint = firstPoint;
        PointOfInterest toPoint = pointsOfInterestIterator.next();
        for (Connection connection : calculatedPath.getConnections()) {
            IVertex<PointOfInterest> vertex = this.mainModel.getGraph().getVertexByElement(fromPoint);
            this.graphPanel.setVertexColor(vertex, Color.GREENYELLOW, Color.GREEN);

            IEdge<Connection, PointOfInterest> edge = this.mainModel.getGraph().getEdgeByElement(connection, fromPoint);
            this.graphPanel.setEdgeColor(edge, Color.DARKRED, 0.6);

            fromPoint = toPoint;
            toPoint = (pointsOfInterestIterator.hasNext() ? pointsOfInterestIterator.next() : firstPoint);
        }
        setStartPoiColor();
        
        // Update costs labels
        double cost = 0;
        double distance = 0;
        for (Connection connection : calculatedPath.getConnections()) {
            cost += connection.getCostEuros();
            distance += connection.getDistance();
        }
        this.costValueLabel.textProperty().setValue(Double.toString(cost));
        this.distanceValueLabel.textProperty().setValue(Double.toString(distance));
        
        // Update buttons
        this.issueTicketButton.setDisable(false);
        this.undoButton.setDisable(!this.mainModel.hasUndoCalculatedCourse());
    }
    /**
     * Method to get the scene of the main view
     */
    @Override
    public Scene getScene() {
        return this.scene;
    }
    /**
     * Method to get the combobox component
     */
    @Override
    public ICriteriaStrategy getCriteriaComboBox() {
        return (ICriteriaStrategy) this.criteriaComboBox.getSelectionModel().getSelectedItem();
    }
    /**
     * Method to get whether the user selected to calculate the path on foot or bike
     */
    @Override
    public boolean getNavigability() {
        return (RadioButton) this.typeToggleGroup.getSelectedToggle() == bicycleRadioButton;
    }
    /**
     * Method to reset the inputs
     */
    @Override
    public void resetInput() {
        this.resetGraphColors();
        this.criteriaComboBox.setValue(new CriteriaStrategyCost());
        this.issueTicketButton.setDisable(true);
        this.undoButton.setDisable(true);
        this.costValueLabel.textProperty().setValue("0");
        this.distanceValueLabel.textProperty().setValue("0");
        this.footRadioButton.setSelected(true);
        this.bicycleRadioButton.setSelected(false);
        for (CheckBox checkBox : this.pointsOfInterestCheckBoxes.keySet()) {
            checkBox.setSelected(false);
        }
    }
    
    /**
     * Method to show a dialog with a the success message
     */
    @Override
    public void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
    
    /**
     * Method to show a window with a error message
     */
    @Override
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle ("An error has occured");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
    
    /**
     * Method to set the triggers / handlers
     */
    @Override
    public void setTriggers(MainController controller) {
        // Points of interest checkboxes listener
        for (CheckBox checkBox : this.pointsOfInterestCheckBoxes.keySet()) {
            checkBox.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
                            controller.changePointOfInterest(this.pointsOfInterestCheckBoxes.get(checkBox), oldValue, newValue));
        }
        
        // Buttons actions
        this.issueTicketButton.setOnAction(event -> controller.issueTicket());
        this.calculateButton.setOnAction(event -> controller.calculatePath());
        this.statisticsButton.setOnAction(event -> controller.openStatistics());
        this.undoButton.setOnAction(event -> controller.undoCalculate());
    }
    /**
     * Method to draw the graph
     */
    @Override
    public void plotGraph() {
        this.graphPanel.plotGraph();
        this.setStartPoiColor();
    }
    /**
     * Method to mark a point of interest to visit
     */
    @Override
    public void markPoiToVisit(IVertex<PointOfInterest> vertex) {
        this.graphPanel.setVertexColor(vertex, Color.RED, Color.BLACK);
    }
    /**
     * Method to unmark a selected point of interest
     */
    @Override
    public void unmarkPoiToVisit(IVertex<PointOfInterest> vertex) {
        this.graphPanel.setVertexColor(vertex, Color.ORANGE, Color.CORAL);
    }
    /**
     * Method to show a question window
     */
    @Override
    public void showNifQuestionDialog(MainController controller) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Personal Information");
        alert.setHeaderText("We may need more information about you, before purchasing the ticket");
        alert.setContentText("Do you want your invoice with NIF?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(type -> controller.nifDialogResponse(type));
    }
    
    private void resetGraphColors() {
        for (IVertex<PointOfInterest> poi : this.mainModel.getGraph().vertices()) {
            this.graphPanel.setVertexColor(poi, Color.ORANGE, Color.CORAL);
        }
        for (IEdge<Connection, PointOfInterest> connection : this.mainModel.getGraph().edges()) {
            this.graphPanel.setEdgeColor(connection, Color.FORESTGREEN, 0.4);
        }
        this.setStartPoiColor();
    }
    
    private void setStartPoiColor() {
        this.graphPanel.setVertexColor(this.mainModel.getStartVertex(), Color.AQUA, Color.BLUE);
    }
    
}
