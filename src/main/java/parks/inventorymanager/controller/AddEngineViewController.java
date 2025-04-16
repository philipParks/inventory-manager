package parks.inventorymanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import parks.inventorymanager.dao.EngineDAO;
import parks.inventorymanager.util.HelpMethods;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static parks.inventorymanager.controller.LoginController.authUser;

/** Defines an add engine view controller. */
public class AddEngineViewController implements Initializable {

    @FXML
    private TextField engineNameTxt;
    @FXML
    private TextField serialNumberTxt;
    @FXML
    private TextField fuelTypeTxt;
    @FXML
    private TextField cylinderTxt;
    @FXML
    private TextField statusTxt;
    @FXML
    private TextField locationTxt;
    @FXML
    private Label systemMessageLabel;

    /** Implements initialization. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        systemMessageLabel.setText("");
    }

    /** Saves the engine to the database.
     * @param saveButtonClicked The save button is clicked.
     * @throws IOException If an I/O error occurs with the primary view. */
    public void onSaveClick(ActionEvent saveButtonClicked) throws IOException {

        String engine = engineNameTxt.getText();
        String serialNumber = serialNumberTxt.getText();
        String fuelType = fuelTypeTxt.getText();
        String cylinder = cylinderTxt.getText();
        String status = statusTxt.getText();
        String location = locationTxt.getText();

        if (engine.isBlank()) {
            systemMessageLabel.setText("Engine must have a name.");
            return;
        }

        if (serialNumber.isBlank()) {
            systemMessageLabel.setText("Engine must have a serial number.");
            return;
        }

        if (status.isBlank()) {
            systemMessageLabel.setText("Engine must have a status.");
            return;
        }

        EngineDAO.insert(engine, location, status, fuelType, serialNumber, cylinder, authUser.getUserId());

        HelpMethods.primaryViewLoader(saveButtonClicked);

    }

    /** Cancel the creation of a new engine.
     * @param buttonClicked The cancel Button is clicked.
     * @throws IOException If an I/O error occurs with the primary view. */
    public void onCancelClick(ActionEvent buttonClicked) throws IOException {

        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cancelAlert.setTitle("Cancel Confirmation Dialog");
        cancelAlert.setHeaderText("Are you sure you want to cancel?");
        cancelAlert.setContentText("Changes you have made will not be saved!\nclick \"OK\" " +
                "to return to the login screen\nclick \"Cancel\" to stay logged in.");
        Optional<ButtonType> result = cancelAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            HelpMethods.primaryViewLoader(buttonClicked);
        }

    }

}
