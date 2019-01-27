package biologicalparkticketsystem.view;

import biologicalparkticketsystem.controller.MainController;
import biologicalparkticketsystem.model.course.PointOfInterest;
import digraph.IVertex;
import java.util.Observer;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public interface IMainView extends Observer {
    
    Scene getScene();
    
//    String getUserInput();
    
//    void showError(String errMessage);
    
//    void resetInput();
    
    void setTriggers(MainController controller);
    
    void plotGraph();
    
    void setGraphVertexColor(IVertex<PointOfInterest> poi, Color fill, Color stroke);
    
}