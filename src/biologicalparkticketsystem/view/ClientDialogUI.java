/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.view;

import biologicalparkticketsystem.model.Client;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author goldspy98
 */
public class ClientDialogUI extends Dialog<Client> {
    
    public ClientDialogUI () {
        // Create the custom dialog
        setTitle("Client Form");
        setHeaderText("Input your information for the invoice");
        
        // Set the button types
        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);
        
        // Create the username and password labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nif = new TextField();
        nif.setPromptText("Nif");
        TextField name = new TextField();
        name.setPromptText("Name");
        TextField address = new TextField();
        address.setPromptText("Address");
        TextField postalCode = new TextField();
        postalCode.setPromptText("Postal code");
        TextField location = new TextField();
        location.setPromptText("Location");
        TextField country = new TextField();
        country.setPromptText("Country");

        grid.add(new Label("Nif:"), 0, 0);
        grid.add(nif, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(name, 1, 1);
        grid.add(new Label("Address:"), 0, 2);
        grid.add(address, 1, 2);
        grid.add(new Label("Postal code:"), 0, 3);
        grid.add(postalCode, 1, 3);
        grid.add(new Label("Location:"), 0, 4);
        grid.add(location, 1, 4);
        grid.add(new Label("Country:"), 0, 5);
        grid.add(country, 1, 5);
        
        // Enable/Disable confirm button depending on whether a username was entered
        Node confirmButton = getDialogPane().lookupButton(confirmButtonType);
        confirmButton.setDisable(true);
        
        // Do some validation (using the Java 8 lambda syntax).
        nif.textProperty().addListener((observable, oldValue, newValue) -> {
            confirmButton.setDisable(newValue.trim().isEmpty());
        });
        
        getDialogPane().setContent(grid);
        
        // Request focus on the username field by default.
        Platform.runLater(() -> nif.requestFocus());
        
        // Convert the result to a username-password-pair when the login button is clicked.
        setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                Client client = new Client(name.getText(), nif.getText());
                Client.Address clientAddress = client.new Address(address.getText(), postalCode.getText(), location.getText(), country.getText());
                client.setAddress(clientAddress);
                
                return client;
            }
            return null;
        });
    }
}
