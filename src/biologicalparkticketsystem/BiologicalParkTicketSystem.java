package biologicalparkticketsystem;

import biologicalparkticketsystem.controller.ConfigManager;
import biologicalparkticketsystem.controller.CourseManager;
import biologicalparkticketsystem.controller.DocumentManager;
import biologicalparkticketsystem.controller.MapManager;
import biologicalparkticketsystem.model.Address;
import biologicalparkticketsystem.model.Client;
import biologicalparkticketsystem.model.PointOfInterest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author maasbs
 */
public class BiologicalParkTicketSystem extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //launch(args);
        
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
        
        Address companyAddress = new Address(
                config.getProperties().get("company.address.adress").toString(),
                config.getProperties().get("company.address.postal_code").toString(),
                config.getProperties().get("company.address.location").toString(),
                config.getProperties().get("company.address.country").toString()
        );
        Client companyData = new Client(
                config.getProperties().get("company.name").toString(),
                config.getProperties().get("company.nif").toString(),
                companyAddress
        );
        
        Address clientAddress = new Address("Est. da Charneca", "2665-506", "Venda do Pinheiro", "Portugal");
        Client client = new Client("Edgar Santos", "267400292", clientAddress);

        DocumentManager documentManager = new DocumentManager("", companyData, Double.parseDouble(config.getProperties().get("vat").toString()));
        documentManager.generateDocuments(courseManager.getCalculatedPath(), client);
        //documentManager.generateDocuments(courseManager.getCalculatedPath(), null);
    }
    
}
