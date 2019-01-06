package biologicalparkticketsystem;

import biologicalparkticketsystem.controller.ConfigManager;
import biologicalparkticketsystem.controller.CourseManager;
import biologicalparkticketsystem.controller.DaoManager;
import biologicalparkticketsystem.controller.DocumentManager;
import biologicalparkticketsystem.controller.MapManager;
import biologicalparkticketsystem.model.Client;
import biologicalparkticketsystem.model.PointOfInterest;
import biologicalparkticketsystem.model.Ticket;
import java.util.ArrayList;
import java.util.List;
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
