/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.view;

import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Luis Varela
 */
public class PaymentUI implements PaymentInterface{
    //
    private HBox title, POI, type, price, buttonMenu, pathCrit, path;
    private VBox center;
    
    //components
    private Label titleLabel,POILabel, pathLabel, typeLabel, priceLabel, pathCriteriaLabel;
    private Label dynamicPOILabel, dynamicpathLabel, dynamicTypeLabel, dynamicPriceLabel, dynamicPathCriteriaLabel;
    private Button payBtn, backBtn;
    
    public PaymentUI(){
        innitComponents();
    }
    
    @Override
    public void innitComponents(){
        //title
        title = new HBox();
        titleLabel = new Label("Payment");
        titleLabel.setStyle("-fx-font-weight: bold");
        titleLabel.setStyle("-fx-font: 20 arial;");
        title.getChildren().add(titleLabel);
        title.setAlignment(Pos.CENTER);
        
        
        center = new VBox();    
        
        //POI
        POI = new HBox();
        POILabel = new Label("Points of interest: ");
        POILabel.setStyle("-fx-font-weight: bold");
        dynamicPOILabel = new Label("HELLo"); //get value from model
        POI.getChildren().addAll(POILabel, dynamicPOILabel);
        
        //Path
        path = new HBox();
        pathLabel = new Label("Paths: ");
        pathLabel.setStyle("-fx-font-weight: bold");
        dynamicpathLabel = new Label("SOMEWHERE OVER THE RAINBOW"); //get value from model
        path.getChildren().addAll(pathLabel, dynamicpathLabel);
        
        //type
        type = new HBox();
        typeLabel = new Label("Path type: ");
        typeLabel.setStyle("-fx-font-weight: bold");
        dynamicTypeLabel = new Label("Foot"); //get value from model
        type.getChildren().addAll(typeLabel, dynamicTypeLabel);
        
        //path criteria
        pathCrit = new HBox();
        pathCriteriaLabel = new Label("Path criteria: ");
        pathCriteriaLabel.setStyle("-fx-font-weight: bold");
        dynamicPathCriteriaLabel = new Label("Cost");
        pathCrit.getChildren().addAll(pathCriteriaLabel, dynamicPathCriteriaLabel);

        //price
        price = new HBox();
        priceLabel = new Label("Total price: ");
        priceLabel.setStyle("-fx-font-weight: bold");
        dynamicPriceLabel = new Label("30€"); //get value from model
        price.getChildren().addAll(priceLabel, dynamicPriceLabel);
        
        center.getChildren().addAll(POI, type, pathCrit, price);
        center.setSpacing(15);
        
        //button menu
        buttonMenu = new HBox();
        payBtn = new Button("Buy ticket");
        
        payBtn.setOnAction((event) -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Personal information");
            alert.setHeaderText("We may need more information about you, before purchasing the ticket");
            alert.setContentText("Do you want to use your NIF?");
            Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    ClientFormUI clientForm = new ClientFormUI();
                    Scene scene = new Scene(clientForm.getGrid(), 300, 275);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Client form");
                    stage.show();
                    
                } else {
                    
                }
        });
        backBtn = new Button("Back");
        backBtn.setOnAction((event) ->{
            Stage stage = (Stage) backBtn.getScene().getWindow();
            stage.close();
        });
        buttonMenu.getChildren().addAll(backBtn, payBtn);
        
        buttonMenu.setSpacing(30);
    }
    
    @Override
    public HBox getTitle(){
        return this.title;
    }
    
    @Override
    public HBox getPOI(){
        return this.POI;
    }
    
    @Override
    public HBox getType(){
        return this.type;
    }
    
    @Override
    public HBox getPrice(){
        return this.price;
    }
    
    @Override
    public HBox getbuttonMenu(){
        return this.buttonMenu;
    }
    
    @Override
    public VBox getCenter(){
        return this.center;
    }
}
