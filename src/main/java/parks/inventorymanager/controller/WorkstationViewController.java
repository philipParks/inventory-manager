package parks.inventorymanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import parks.inventorymanager.dao.WorkstationDAO;
import parks.inventorymanager.util.HelpMethods;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static parks.inventorymanager.controller.LoginController.authUser;

/** Defines a workstation view controller. */
public class WorkstationViewController implements Initializable {
    public TextField workstationNameTxt;
    public Label systemMessageLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        systemMessageLabel.setText("");
    }

    /** Saves the workstation. */
    public void onSaveClick(ActionEvent saveButtonClicked) throws IOException {

        String workstation = workstationNameTxt.getText();

        if (workstation.isBlank()) {
            systemMessageLabel.setText("A workstation must have a name.");
            return;
        }

        WorkstationDAO.insert(workstation, authUser.getUserId());

        HelpMethods.primaryViewLoader(saveButtonClicked);

    }

    /** Cancels the creation of a new workstation. */
    public void onCancelClick(ActionEvent cancelButtonClicked) throws IOException {

        Alert cancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cancelAlert.setTitle("Cancel Confirmation Dialog");
        cancelAlert.setHeaderText("Are you sure you want to cancel?");
        cancelAlert.setContentText("Changes you have made will not be saved!\nclick \"OK\" " +
                "to return to the login screen\nclick \"Cancel\" to stay logged in.");
        Optional<ButtonType> result = cancelAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            HelpMethods.primaryViewLoader(cancelButtonClicked);
        }

    }
}
