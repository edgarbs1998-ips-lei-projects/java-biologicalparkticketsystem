/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author MaDsHiT
 */
public interface PaymentInterface {
    //instanciate layout components
    public void innitComponents();
    
    public HBox getTitle();
    
    public HBox getPOI();
    
    public HBox getType();
    
    public HBox getPrice();
    
    public HBox getbuttonMenu();
    
    public VBox getCenter();
}
