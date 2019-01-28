package biologicalparkticketsystem.view;

import biologicalparkticketsystem.controller.MainController;
import biologicalparkticketsystem.model.course.ICriteriaStrategy;
import biologicalparkticketsystem.model.course.PointOfInterest;
import digraph.IVertex;
import java.util.Observer;
import javafx.scene.Scene;

/**
 * Interface of main view to set the methods 
 */
public interface IMainView extends Observer {
    
    /**
     * Method to get the scene of the main view
     * @return scene
     */
    Scene getScene();
    
    /**
     * Method to get the combobox component
     * @return criteria strategy
     */
    ICriteriaStrategy getCriteriaComboBox();
    
    /**
     * Method to get whether the user selected to calculate the path on foot or bike
     * @return navigability
     */
    boolean getNavigability();
    
    /**
     * Method to reset the inputs
     */
    void resetInput();
    
    /**
     * Method to show a dialog with a the success message
     * @param message success message
     */
    void showSuccess(String message);
    
    /**
     * Method to show a window with a error message
     * @param message error message
     */
    void showError(String message);
    
    /**
     * Method to set the triggers / handlers
     * @param controller main controller
     */
    void setTriggers(MainController controller);
    
    /**
     * Method to draw the graph
     */
    void plotGraph();
    
    /**
     * Method to mark a point of interest to visit
     * @param poi point of interest vertex
     */
    void markPoiToVisit(IVertex<PointOfInterest> poi);
    
    /**
     * Method to unmark a selected point of interest
     * @param poi point of interest vertex
     */
    void unmarkPoiToVisit(IVertex<PointOfInterest> poi);
    
    /**
     * Method to show a question window
     * @param controller main controller
     */
    void showNifQuestionDialog(MainController controller);
    
}