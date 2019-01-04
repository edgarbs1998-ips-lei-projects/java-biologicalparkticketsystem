package biologicalparkticketsystem;

import biologicalparkticketsystem.controller.ConfigManager;
import biologicalparkticketsystem.model.Connection;
import biologicalparkticketsystem.controller.CourseManager;
import biologicalparkticketsystem.controller.DocumentManager;
import biologicalparkticketsystem.model.CalculatedPath;
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
        
//        CourseManager courseManager = new CourseManager();
//        courseManager.loadCourseMapFile(config.getProperties().getProperty("mapFile"));
//        
//        System.out.println(courseManager.toString());
//        
//        PointOfInterest poiStart = courseManager.getPointOfInterestById(1);
//        
//        List<PointOfInterest> mustVisitPois = new ArrayList<>();
//        PointOfInterest poi1 = courseManager.getPointOfInterestById(5);
//        mustVisitPois.add(poi1);
//        PointOfInterest poi2 = courseManager.getPointOfInterestById(8);
//        mustVisitPois.add(poi2);
//        PointOfInterest poi3 = courseManager.getPointOfInterestById(4);
//        mustVisitPois.add(poi3);
//        
//        testMinimumCostPath(courseManager, CourseManager.Criteria.DISTANCE, poiStart, mustVisitPois, false);
//        testMinimumCostPath(courseManager, CourseManager.Criteria.COST, poiStart, mustVisitPois, false);
//        testMinimumCostPath(courseManager, CourseManager.Criteria.DISTANCE, poiStart, mustVisitPois, true);
//        testMinimumCostPath(courseManager, CourseManager.Criteria.COST, poiStart, mustVisitPois, true);

        try {
            DocumentManager documentManager = new DocumentManager(config);
            documentManager.generateTicket();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BiologicalParkTicketSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BiologicalParkTicketSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void testMinimumCostPath(CourseManager courseManager,
            CourseManager.Criteria criteria, 
            PointOfInterest orig, List<PointOfInterest> mustVisitPois, boolean allowBike) {
        
        System.out.println( String.format("Best (%s) route between %s and %s (onBike: %s)", criteria, orig, mustVisitPois, allowBike));
        CalculatedPath calculatedPath = courseManager.minimumCriteriaPath(criteria, allowBike, orig, mustVisitPois);
        System.out.println( String.format("Total cost (%s) = %d", criteria.getUnit(), calculatedPath.getCost()));
        System.out.println("Points of Interest: " + calculatedPath.getPointsOfInterest());
        System.out.println("Connections: " + calculatedPath.getConnections());
        System.out.println("");
    }
    
}
