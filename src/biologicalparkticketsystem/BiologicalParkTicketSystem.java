package biologicalparkticketsystem;

import biologicalparkticketsystem.controller.ConfigManager;
import biologicalparkticketsystem.controller.CourseManager;
import biologicalparkticketsystem.controller.DaoManager;
import biologicalparkticketsystem.controller.DocumentManager;
import biologicalparkticketsystem.controller.MapManager;
import biologicalparkticketsystem.model.Client;
import biologicalparkticketsystem.model.Connection;
import biologicalparkticketsystem.model.PointOfInterest;
import biologicalparkticketsystem.model.Ticket;
import biologicalparkticketsystem.view.BiologicalParkTicketSystemUI;
import graphview.*;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author maasbs
 */
public class BiologicalParkTicketSystem extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        /*//test digraph
        ConfigManager config = ConfigManager.getInstance();
        MapManager mapManager = new MapManager(config.getProperties().getProperty("mapFile"));
        
        
        VBox graphViewer = new VBox(); // graphviewer container
        VertexPlacementStrategy strategy = new CircularSortedPlacementStrategy();
        
        GraphPanel<PointOfInterest, Connection> graphView = new GraphPanel<>(mapManager.getDiGraph(), strategy); //SHOULD WORK DONT KNOW WHY IT DOESNT
        graphViewer.getChildren().add(graphView);
        graphViewer.setPadding(new Insets(20));
        
        //right menu
        VBox rightMenu = new VBox();
        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("Foot");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);
        RadioButton rb2 = new RadioButton("Bicycle");
        rb2.setToggleGroup(group);
        final ComboBox numberComboBox = new ComboBox();
        numberComboBox.setValue("Number of people");
        numberComboBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30);
        rightMenu.getChildren().addAll(rb1, rb2, numberComboBox);
        rightMenu.setSpacing(10);

        // bottom respectively "button area"
        HBox bottomMenu = new HBox();
        
        //Instanciate buttons
        Button payBtn = new Button("Pay");
        Button calculateBtn = new Button("Calculate");
        ObservableList<String> options = FXCollections.observableArrayList("Shortest","Cheapest","Most visited");
        final ComboBox PathComboBox = new ComboBox(options);
        PathComboBox.setValue("Path");
        
        //set button handlers
        calculateBtn.setOnAction(new EventHandler<ActionEvent>() {

          public void handle(ActionEvent event) {
            //SHOW WINDOW OF CALCULATED STATISTICS
            
            Stage statisticsWindow = new Stage();
            statisticsWindow.setTitle("Statisitcs");
            //stage.setScene(new Scene(root, 450, 450));
            statisticsWindow.show();
          }
        });
        
        payBtn.setOnAction(new EventHandler<ActionEvent>() {

          public void handle(ActionEvent event) {
            //DO WINDOW
            System.out.println("Receipt window");
          }
        });
        
        //Add buttons to menu
        bottomMenu.getChildren().addAll(PathComboBox, calculateBtn, payBtn);
        
        //align menu
        bottomMenu.setAlignment(Pos.CENTER_LEFT);
        */
        
        BiologicalParkTicketSystemUI view = new BiologicalParkTicketSystemUI();
        
        // root
        BorderPane root = new BorderPane();
        root.setCenter(view.getGraphViewer());
        root.setBottom(view.getbottomMenu());
        root.setRight(view.getRightMenu());
        root.setPadding(new Insets(20));
        
        //scene
        Scene scene = new Scene(root, 800, 600);
        
        //stage build
        primaryStage.setTitle("Biological Park");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
        ConfigManager config = ConfigManager.getInstance();
        
//        LoggerManager logger = LoggerManager.getInstance();
//        logger.getLogger().warning("Teste");
//        logger.getLogger().fine("Teste3");
//        logger.getLoggerCourseCalculations().info("Teste2");
//        logger.getLoggerCourseCalculations().fine("Teste4");
        
        MapManager mapManager = new MapManager(config.getProperties().getProperty("mapFile"));
        System.out.println(mapManager .toString());
        
        CourseManager courseManager = new CourseManager(mapManager);
        System.out.println(courseManager.toString());
        
        List<PointOfInterest> mustVisitPois = new ArrayList<>();
        PointOfInterest poi1 = mapManager.getPointOfInterestById(5);
        mustVisitPois.add(poi1);
        PointOfInterest poi2 = mapManager.getPointOfInterestById(8);
        mustVisitPois.add(poi2);
        PointOfInterest poi3 = mapManager.getPointOfInterestById(4);
        mustVisitPois.add(poi3);
        
//        courseManager.minimumCriteriaPath(CourseManager.Criteria.DISTANCE, false, mustVisitPois);
//        System.out.println(courseManager.toString());
//        courseManager.minimumCriteriaPath(CourseManager.Criteria.COST, false, mustVisitPois);
//        System.out.println(courseManager.toString());
//        courseManager.minimumCriteriaPath(CourseManager.Criteria.DISTANCE, true, mustVisitPois);
//        System.out.println(courseManager.toString());
        courseManager.minimumCriteriaPath(CourseManager.Criteria.COST, true, mustVisitPois);
        System.out.println(courseManager.toString());
        
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
        
        Client client = new Client("Edgar Santos", "267400292");
        Client.Address clientAddress = client.new Address("Est. da Charneca", "2665-506", "Venda do Pinheiro", "Portugal");
        client.setAddress(clientAddress);
        
        DaoManager daoManager = DaoManager.getInstance();
        daoManager.init(config);

        DocumentManager documentManager = new DocumentManager(config.getProperties().getProperty("documents.folder"), companyData, Double.parseDouble(config.getProperties().get("vat").toString()));
        documentManager.generateDocuments(courseManager.getCalculatedPath(), client);
        //documentManager.generateDocuments(courseManager.getCalculatedPath(), null);
        
        for (Ticket ticket : daoManager.getTicketDao().selectTickets()) {
            System.out.println(ticket);
        }
    }
    
}
