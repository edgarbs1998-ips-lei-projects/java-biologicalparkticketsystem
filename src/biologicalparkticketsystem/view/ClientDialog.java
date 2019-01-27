package biologicalparkticketsystem.view;

import biologicalparkticketsystem.model.document.Client;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ClientDialog extends Dialog<Client> {
    
    public ClientDialog () {
        // Create the client dialog
        setTitle("Client Form");
        setHeaderText("Input your information for the invoice");
        
        // Set the button types
        getDialogPane().getButtonTypes().addAll(ButtonType.NEXT, ButtonType.CANCEL);
        
        // Create the client labels and fields
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
        
        // Enable/Disable confirm button depending on whether a nif was entered
        Node nextButton = getDialogPane().lookupButton(getDialogPane().getButtonTypes().get(0));
        nextButton.setDisable(true);
        nif.textProperty().addListener((observable, oldValue, newValue) -> {
            nextButton.setDisable(newValue.trim().isEmpty());
        });
        
        // Set dialog content
        getDialogPane().setContent(grid);
        
        // Request focus on the nif field by default
        Platform.runLater(() -> nif.requestFocus());
        
        // Convert the result to a client object when the next button is clicked
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.NEXT) {
                Client client = new Client(name.getText(), nif.getText());
                Client.Address clientAddress = client.new Address(address.getText(), postalCode.getText(), location.getText(), country.getText());
                client.setAddress(clientAddress);
                
                return client;
            }
            return null;
        });
    }
    
}
