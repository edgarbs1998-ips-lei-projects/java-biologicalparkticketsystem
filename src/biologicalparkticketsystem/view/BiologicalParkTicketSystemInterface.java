package biologicalparkticketsystem.view;

import biologicalparkticketsystem.model.course.Connection;
import biologicalparkticketsystem.model.course.PointOfInterest;
import graphview.GraphPanel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public interface BiologicalParkTicketSystemInterface {
    
    //get graph viewer
    public GraphPanel<PointOfInterest, Connection> getGraphView();
    
    //get right menu
    public VBox getRightMenu();
    
    //get bottom menu
    public HBox getbottomMenu();
    
    public void innitComponents();
    
}
