package biologicalparkticketsystem.view;

import biologicalparkticketsystem.ConfigManager;
import biologicalparkticketsystem.model.course.CourseManager;
import biologicalparkticketsystem.model.course.CourseManagerException;
import biologicalparkticketsystem.DaoManager;
import biologicalparkticketsystem.model.document.DocumentManager;
import biologicalparkticketsystem.LoggerManager;
import biologicalparkticketsystem.model.course.MapManager;
import biologicalparkticketsystem.model.course.MapManagerException;
import biologicalparkticketsystem.model.document.Client;
import biologicalparkticketsystem.model.course.Connection;
import biologicalparkticketsystem.model.course.PointOfInterest;
import digraph.IEdge;
import digraph.IVertex;
import graphview.CircularSortedPlacementStrategy;
import graphview.GraphPanel;
import graphview.VertexPlacementStrategy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BiologicalParkTicketSystemUI implements BiologicalParkTicketSystemInterface {
    
    //Layouts
    private GraphPanel<PointOfInterest, Connection> graphView;
    private VBox rightMenu, poiVBox;
    private HBox bottomMenu;
    
    //Components
    private ToggleGroup group;
    private RadioButton rbFoot, rbBicycle;
    private ComboBox pathComboBox;
    private Button payBtn, calculateBtn, statisticsBtn, undoBtn;
    private Label POITitle, pathTypeTitle, costLabel, costLabelValue, distanceLabel, distanceLabelValue;
    //private List<Integer> selectedPOI = new ArrayList<>();
    //public int index;
    
    //Variables
    private List<PointOfInterest> selectedPois;
    MapManager mapManager;
    CourseManager courseManager;
    DocumentManager documentManager;
    
    public BiologicalParkTicketSystemUI(){
        selectedPois = new ArrayList<>();
        innitComponents();
    }
    
    @Override
    public void innitComponents(){
        ConfigManager config = ConfigManager.getInstance();
        config.init();
        
        LoggerManager logger = LoggerManager.getInstance();
        logger.init();
        
        try {
            mapManager = new MapManager(config.getProperties().getProperty("map"));
        } catch (MapManagerException ex) {
            LoggerManager.getInstance().log(ex);
            return;
        }
        
        Client companyData = new Client(
                config.getProperties().get("company.name").toString(),
                config.getProperties().get("company.nif").toString()
        );
        Client.Address companyAddress = companyData.new Address(
                config.getProperties().get("company.address.adress").toString(),
                config.getProperties().get("company.address.postal_code").toString(),
                config.getProperties().get("company.address.location").toString(),
                config.getProperties().get("company.address.country").toString()
        );
        companyData.setAddress(companyAddress);
        
        DaoManager daoManager = DaoManager.getInstance();
        daoManager.init(config, mapManager);
        
        documentManager = new DocumentManager(config.getProperties().getProperty("documents.folder"), companyData, Double.parseDouble(config.getProperties().get("documents.vat").toString()));
        
        courseManager = new CourseManager(mapManager);
        
        //instanciate strategy
        //VertexPlacementStrategy strategy = new CircularPlacementStrategy();
        VertexPlacementStrategy strategy = new CircularSortedPlacementStrategy();
        //VertexPlacementStrategy strategy = new RandomPlacementStrategy();
        
        //graph viewer container
        graphView = new GraphPanel<>(mapManager.getDiGraph(), strategy);
        
        //right menu container
        rightMenu = new VBox();
        
        statisticsBtn = new Button("Statistics");
        statisticsBtn.setOnAction((event) -> {
            LoggerManager.getInstance().log(LoggerManager.Component.STATISTICS_CHECKS);
            
            StatisticsUI statisticsWindow = new StatisticsUI();
            Stage statisticsStage = new Stage();
            statisticsStage.setTitle("Statistics");
            statisticsStage.setScene(new Scene(statisticsWindow, 600, 400));
            statisticsStage.show();
        });
        rightMenu.getChildren().add(statisticsBtn);
        
        VBox typeVBox = new VBox();
        pathTypeTitle = new Label("Path type");
        pathTypeTitle.setStyle("-fx-font-weight: bold");
        typeVBox.getChildren().add(pathTypeTitle);
        group = new ToggleGroup();
        rbFoot = new RadioButton("Foot");
        rbFoot.setToggleGroup(group);
        rbFoot.setSelected(true);
        rbBicycle = new RadioButton("Bicycle");
        rbBicycle.setToggleGroup(group);
        typeVBox.getChildren().addAll(rbFoot, rbBicycle);
        typeVBox.setSpacing(20);
        rightMenu.getChildren().add(typeVBox);
        
        poiVBox = new VBox();
        POITitle = new Label("Points of Interest");
        POITitle.setStyle("-fx-font-weight: bold");
        poiVBox.getChildren().add(POITitle);
        //make foreach to get all points of interest and add radio buttons ofr eahc point
        for(IVertex<PointOfInterest> poi : mapManager.getDiGraph().vertices()){
            if (poi.element() == mapManager.getStartPoint()) continue;
            
            CheckBox checkBox = new CheckBox(poi.element().toString());
            checkBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                if (newValue) {
                    selectedPois.add(poi.element());
                    graphView.setVertexColor(poi, Color.RED, Color.BLACK);
                } else {
                    selectedPois.remove(poi.element());
                    graphView.setVertexColor(poi, Color.ORANGE, Color.CORAL);
                }
            });
            poiVBox.getChildren().add(checkBox);
        }
        poiVBox.setSpacing(20);
        rightMenu.getChildren().add(poiVBox);
        
        rightMenu.setSpacing(50);
        
        //bottom buttons container
        bottomMenu = new HBox();
        payBtn = new Button("Issue ticket");
        payBtn.setDisable(true);
        payBtn.setOnAction((event) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Personal information");
            alert.setHeaderText("We may need more information about you, before purchasing the ticket");
            alert.setContentText("Do you want your invoice with NIF?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    Optional<Client> result = new ClientDialogUI().showAndWait();

                    result.ifPresent(client -> {
                        generateDocuments(client);
                    });
                } else {
                    generateDocuments(null);
                }
            });
        });
        
        undoBtn = new Button("Undo");
        undoBtn.setDisable(true);
        undoBtn.setOnAction((event) -> {
            courseManager.undoCalculatedCourse();
            resetColors();
            updateColors();
            updateCosts();
            
            if (courseManager.countCalculatedCourses() == 0) {
                undoBtn.setDisable(true);
            }
        });
        
        calculateBtn = new Button("Calculate");
        calculateBtn.setOnAction((event) -> {
            CourseManager.Criteria criteria = (CourseManager.Criteria) pathComboBox.getSelectionModel().getSelectedItem();
            
            boolean navigability = (!((RadioButton )group.getSelectedToggle()).getText().equals("Foot"));
            
            try {
                courseManager.minimumCriteriaPath(criteria, navigability, this.selectedPois);
            } catch (CourseManagerException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle ("An error has occured while calculating the course");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());
                alert.show();
                
                return;
            }
            
            resetColors();
            updateColors();
            
            payBtn.setDisable(false);
            if (courseManager.countCalculatedCourses() > 0) {
                undoBtn.setDisable(false);
            }
            
            updateCosts();
        });
        
        //cost
        HBox costHBox = new HBox();
        costLabel = new Label("Total cost (€):");
        costLabel.setStyle("-fx-font-weight: bold");
        costLabelValue = new Label("0");
        costHBox.getChildren().addAll(costLabel, costLabelValue);
        costHBox.setAlignment(Pos.CENTER_LEFT);
        costHBox.setSpacing(5);
        
        //distance
        HBox distanceHBox = new HBox();
        distanceLabel = new Label("Total distance (Meters):");
        distanceLabel.setStyle("-fx-font-weight: bold");
        distanceLabelValue = new Label("0");
        distanceHBox.getChildren().addAll(distanceLabel, distanceLabelValue);
        distanceHBox.setAlignment(Pos.CENTER_LEFT);
        distanceHBox.setSpacing(5);
        
        
        HBox criteriaHBox = new HBox();
        Label criteriaLabel = new Label("Criteria:");
        criteriaLabel.setStyle("-fx-font-weight: bold");
        ObservableList<CourseManager.Criteria> options = FXCollections.observableArrayList(CourseManager.Criteria.COST, CourseManager.Criteria.DISTANCE);
        pathComboBox = new ComboBox(options);
        pathComboBox.setValue(CourseManager.Criteria.COST);
        criteriaHBox.getChildren().addAll(criteriaLabel, pathComboBox);
        criteriaHBox.setAlignment(Pos.CENTER_LEFT);
        criteriaHBox.setSpacing(5);
        
        bottomMenu.getChildren().addAll(criteriaHBox, calculateBtn, undoBtn, payBtn, costHBox, distanceHBox);
        bottomMenu.setAlignment(Pos.CENTER_LEFT);
        bottomMenu.setSpacing(20);
        bottomMenu.setPadding(new Insets(10));
        bottomMenu.setStyle("-fx-border-color: black; -fx-border-insets: 2; -fx-border-width: 1;");
    }
    
    public void generateDocuments(Client client) {
        documentManager.generateDocuments(courseManager.getCalculatedPath(), client);

        Alert alertFinish = new Alert(Alert.AlertType.INFORMATION);
        alertFinish.setTitle("Ticket Issued");
        alertFinish.setHeaderText("");
        alertFinish.setContentText("Your ticket has been issued!");
        alertFinish.show();

        resetOptions();
    }
    
    public void resetOptions() {
        courseManager.clearCalculatedCourses();
        resetColors();
        pathComboBox.setValue(CourseManager.Criteria.COST);
        payBtn.setDisable(true);
        undoBtn.setDisable(true);
        costLabelValue.textProperty().setValue("0");
        distanceLabelValue.textProperty().setValue("0");
        for (Toggle toggle : group.getToggles()) {
            toggle.setSelected(false);
        }
        rbFoot.setSelected(true);
        for (Node node : poiVBox.getChildren()) {
            if (node instanceof CheckBox) {
                ((CheckBox) node).setSelected(false);
            }
        }
    }
    
    public void updateCosts() {
        double cost = 0;
        double distance = 0;
        for (Connection connection : courseManager.getCalculatedPath().getConnections()) {
            cost += connection.getCostEuros();
            distance += connection.getDistance();
        }
        costLabelValue.textProperty().setValue(Double.toString(cost));
        distanceLabelValue.textProperty().setValue(Double.toString(distance));
    }
    
    public void resetColors() {
        for (IVertex<PointOfInterest> poi : mapManager.getDiGraph().vertices()) {
            graphView.setVertexColor(poi, Color.ORANGE, Color.CORAL);
        }
        for (IEdge<Connection, PointOfInterest> connection : mapManager.getDiGraph().edges()) {
            graphView.setEdgeColor(connection, Color.FORESTGREEN, 0.4);
        }
        setStartPoiColor();
    }
    
    public void updateColors() {
        Iterator<PointOfInterest> pointsOfInterestIterator = courseManager.getCalculatedPath().getPointsOfInterest().iterator();
        PointOfInterest firstPoint = pointsOfInterestIterator.next();
        PointOfInterest fromPoint = firstPoint;
        PointOfInterest toPoint = pointsOfInterestIterator.next();
        for (Connection connection : courseManager.getCalculatedPath().getConnections()) {
            IVertex<PointOfInterest> vertex = mapManager.getDiGraph().getVertexByElement(fromPoint);
            graphView.setVertexColor(vertex, Color.GREENYELLOW, Color.GREEN);

            IEdge<Connection, PointOfInterest> edge = mapManager.getDiGraph().getEdgeByElement(connection, fromPoint);
            graphView.setEdgeColor(edge, Color.DARKRED, 0.6);

            fromPoint = toPoint;
            toPoint = (pointsOfInterestIterator.hasNext() ? pointsOfInterestIterator.next() : firstPoint);
        }
        setStartPoiColor();
    };
    
    public void setStartPoiColor() {
        graphView.setVertexColor(mapManager.getDiGraph().getVertexByElement(mapManager.getStartPoint()), Color.AQUA, Color.BLUE);
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
