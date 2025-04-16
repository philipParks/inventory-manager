package parks.inventorymanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import parks.inventorymanager.dao.DistributorDAO;
import parks.inventorymanager.util.HelpMethods;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static parks.inventorymanager.controller.LoginController.authUser;

/** Defines a distributor view controller. */
public class DistributorViewController implements Initializable {

    @FXML
    private Label systemMessageLabel;
    @FXML
    private TextField distributorNameTxt;
    @FXML
    private TextField streetTxt;
    @FXML
    private TextField cityTxt;
    @FXML
    private TextField stateTxt;
    @FXML
    private TextField zipcodeTxt;
    @FXML
    private TextField contactTxt;
    @FXML
    private TextField phoneTxt;
    @FXML
    private TextField emailTxt;

    /** Implements initialization. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        systemMessageLabel.setText("");
    }

    /** Saves the distributor information.
     * @param saveButtonClicked The save button was clicked.
     * @throws IOException If an I/O error occurs with the primary view.*/
    public void onSaveClick(ActionEvent saveButtonClicked) throws IOException {

        String distributor = distributorNameTxt.getText();
        String street = streetTxt.getText();
        String city = cityTxt.getText();
        String state = stateTxt.getText();
        String zipcode = zipcodeTxt.getText();
        String contact = contactTxt.getText();
        String phone = phoneTxt.getText();
        String email = emailTxt.getText();

        if (distributor.isBlank()) {
            System.out.println("Please enter a distributor name.");
            return;
        }

        DistributorDAO.insert(distributor, street, city, state, zipcode, contact, phone, email, authUser.getUserId());

        HelpMethods.primaryViewLoader(saveButtonClicked);

    }

    /** Cancels the creation of a distributor.
     * @param buttonClicked The cancel button was clicked.
     * @throws IOException If an I/O error occurs with the primary view.*/
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
