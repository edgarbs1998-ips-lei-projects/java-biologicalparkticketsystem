package biologicalparkticketsystem.view;

import biologicalparkticketsystem.controller.MainController;
import biologicalparkticketsystem.model.course.CourseManager;
import biologicalparkticketsystem.model.course.PointOfInterest;
import digraph.IVertex;
import java.util.Observer;
import javafx.scene.Scene;

public interface IMainView extends Observer {
    
    Scene getScene();
    
    CourseManager.Criteria getCriteriaComboBox();
    
    boolean getNavigability();
    
    void resetInput();
    
    void showSuccess(String message);
    
    void showError(String message);
    
    void setTriggers(MainController controller);
    
    void plotGraph();
    
    void markPoiToVisit(IVertex<PointOfInterest> poi);
    
    void unmarkPoiToVisit(IVertex<PointOfInterest> poi);
    
    void showNifQuestionDialog(MainController controller);
    
}