/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Luis Varela
 */
public class ClientFormUI {
    GridPane grid;
    Text scenetitle;
    Label clientName, idNumber, FiscalNumber;
    TextField clientNameTextField, idNumberTextField, FiscalNumberTextField;
    HBox hbBtn;
    
    public ClientFormUI(){
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        scenetitle = new Text("Client Form");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        clientName = new Label("Name: ");
        grid.add(clientName, 0, 1);
        
        clientNameTextField = new TextField();
        grid.add(clientNameTextField, 1, 1);
        
        idNumber = new Label("ID number:");
        grid.add(idNumber, 0, 2);

        idNumberTextField = new TextField();
        grid.add(idNumberTextField, 1, 3);

        FiscalNumber = new Label("Fiscal number:");
        grid.add(FiscalNumber, 0, 3);

        FiscalNumberTextField = new TextField();
        grid.add(FiscalNumberTextField, 1, 2);

        Button btn = new Button("Proceed");
        hbBtn = new HBox();
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
    }
    
    public GridPane getGrid(){
        return this.grid;
    }
}
