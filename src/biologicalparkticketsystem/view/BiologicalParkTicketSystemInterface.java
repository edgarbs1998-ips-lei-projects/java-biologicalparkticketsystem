/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.view;

import biologicalparkticketsystem.model.Connection;
import biologicalparkticketsystem.model.PointOfInterest;
import graphview.GraphPanel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author MaDsHiT
 */
public interface BiologicalParkTicketSystemInterface {
    
    //get graph viewer
    public GraphPanel<PointOfInterest, Connection> getGraphView();
    
    //get right menu
    public VBox getRightMenu();
    
    //get bottom menu
    public HBox getbottomMenu();
    
    public void innitComponents();
}
