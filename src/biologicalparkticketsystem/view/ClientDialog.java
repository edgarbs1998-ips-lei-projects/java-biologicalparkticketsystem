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
import javafx.scene.layout.Pane;

public class ClientDialog extends Dialog<Client> {
    
    private TextField nifTextField, nameTextField, addressTextField, postalCodeTextField, locationTextField, countryTextField;
    
    public ClientDialog () {
        // Create the client dialog
        setTitle("Client Form");
        setHeaderText("Input your information for the invoice");
        
        // Set the button types
        getDialogPane().getButtonTypes().addAll(ButtonType.NEXT, ButtonType.CANCEL);
        
        Pane grid = initComponents();
        
        // Enable/Disable confirm button depending on whether a nif was entered
        Node nextButton = getDialogPane().lookupButton(getDialogPane().getButtonTypes().get(0));
        nextButton.setDisable(true);
        nifTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            nextButton.setDisable(newValue.trim().isEmpty());
        });
        
        // Set dialog content
        getDialogPane().setContent(grid);
        
        // Request focus on the nif field by default
        Platform.runLater(() -> nifTextField.requestFocus());
        
        // Convert the result to a client object when the next button is clicked
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.NEXT) {
                Client client = new Client(nameTextField.getText(), nifTextField.getText());
                Client.Address clientAddress = client.new Address(
                        addressTextField.getText(),
                        postalCodeTextField.getText(),
                        locationTextField.getText(),
                        countryTextField.getText()
                );
                client.setAddress(clientAddress);
                
                return client;
            }
            return null;
        });
    }
    
    private Pane initComponents() {
        // Create the client labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        nifTextField = new TextField();
        nifTextField.setPromptText("Nif");
        nameTextField = new TextField();
        nameTextField.setPromptText("Name");
        addressTextField = new TextField();
        addressTextField.setPromptText("Address");
        postalCodeTextField = new TextField();
        postalCodeTextField.setPromptText("Postal code");
        locationTextField = new TextField();
        locationTextField.setPromptText("Location");
        countryTextField = new TextField();
        countryTextField.setPromptText("Country");

        grid.add(new Label("Nif:"), 0, 0);
        grid.add(nifTextField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameTextField, 1, 1);
        grid.add(new Label("Address:"), 0, 2);
        grid.add(addressTextField, 1, 2);
        grid.add(new Label("Postal code:"), 0, 3);
        grid.add(postalCodeTextField, 1, 3);
        grid.add(new Label("Location:"), 0, 4);
        grid.add(locationTextField, 1, 4);
        grid.add(new Label("Country:"), 0, 5);
        grid.add(countryTextField, 1, 5);
        
        return grid;
    }
    
}
