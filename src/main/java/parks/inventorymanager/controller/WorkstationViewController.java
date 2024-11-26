package parks.inventorymanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import parks.inventorymanager.dao.WorkstationDAO;

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

        Parent primaryViewParent = FXMLLoader.load(getClass().getResource("/parks/inventorymanager/view/primaryView.fxml"));
        Scene primaryViewScene = new Scene(primaryViewParent);
        Stage primaryViewWindow = (Stage) ((Node)saveButtonClicked.getSource()).getScene().getWindow();

        primaryViewWindow.setTitle("Engine-uity Rebuilds Inventory Manager");
        primaryViewWindow.setScene(primaryViewScene);
        primaryViewWindow.show();

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
            FXMLLoader primaryViewLoader = new FXMLLoader();
            primaryViewLoader.setLocation(getClass().getResource("/parks/inventorymanager/view/primaryView.fxml"));
            primaryViewLoader.load();

            Parent primaryViewParent = primaryViewLoader.getRoot();
            Scene primaryViewScene = new Scene(primaryViewParent);
            Stage primaryViewWindow = (Stage) ((Node)cancelButtonClicked.getSource()).getScene().getWindow();

            primaryViewWindow.setTitle("Engine-uity Rebuilds Inventory Manager");
            primaryViewWindow.setScene(primaryViewScene);
            primaryViewWindow.show();
        }

    }
}
