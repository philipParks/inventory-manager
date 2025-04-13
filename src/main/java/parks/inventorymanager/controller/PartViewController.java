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
import parks.inventorymanager.dao.*;
import parks.inventorymanager.model.Distributor;
import parks.inventorymanager.model.Inhouse;
import parks.inventorymanager.model.Outsourced;
import parks.inventorymanager.model.Workstation;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static parks.inventorymanager.controller.LoginController.authUser;

/** Defines the part view controller. */
public class PartViewController implements Initializable {

    @FXML
    private Label partIdLabel;
    @FXML
    private ToggleGroup partSource;
    @FXML
    private RadioButton inhouseRB;
    @FXML
    private RadioButton outsourceRB;
    @FXML
    private Label sourceLabel;
    @FXML
    private TextField locationTxt;
    @FXML
    private ChoiceBox sourceCB;
    @FXML
    private TextField partNameTxt;
    @FXML
    private Label systemMessageLabel;

    /** Transfer an inhouse part to the part view.
     * @param selectedPart The part that has been selected. */
    public void transferPart(Inhouse selectedPart) {
        partIdLabel.setText(String.valueOf(selectedPart.getId()));
        partSource.selectToggle(inhouseRB);
        sourceCB.setItems(WorkstationDAO.selectAll());
        sourceCB.setValue(selectedPart.getWorkstation());
        partNameTxt.setText(selectedPart.getName());
        locationTxt.setText(selectedPart.getLocation());
    }

    /** Transfer an outsourced part to the part view.
     * @param selectedPart The part that has been selected. */
    public void transferPart(Outsourced selectedPart) {
        partIdLabel.setText(String.valueOf(selectedPart.getId()));
        partSource.selectToggle(outsourceRB);
        sourceCB.setItems(DistributorDAO.selectAll());
        sourceCB.setValue(selectedPart.getDistributor());
        partNameTxt.setText(selectedPart.getName());
        locationTxt.setText(selectedPart.getLocation());
    }

    /** Defines the initialization process of the part view. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdLabel.setText("New");
        systemMessageLabel.setText("");
        partSource.selectToggle(inhouseRB);
        sourceCB.setValue("Select a workstation");
        sourceCB.setItems(WorkstationDAO.selectAll());
    }

    /** Modifies the input controls of the view when the in house radio button is selected. */
    public void onInhouseSelected() {
        sourceLabel.setText("Workstation");
        sourceCB.setValue("Select a workstation");
        sourceCB.setItems(WorkstationDAO.selectAll());
    }

    /** Modifies the input controls of the view when the outsource radio button is selected. */
    public void onOutsourceSelected() {
        sourceLabel.setText("Distributor");
        sourceCB.setValue("Select a distributor");
        sourceCB.setItems(DistributorDAO.selectAll());
    }

    /** Saves a part of the selected source to the respective tables.
     * @param saveButtonClicked The save button is clicked.
     * @throws IOException If an I/O error occurs with the primary view.*/
    public void onSaveClick(ActionEvent saveButtonClicked) throws IOException {

        String location = locationTxt.getText();
        String partName = partNameTxt.getText();
        int user = authUser.getUserId();
        int partId;

        PartDAO.insert(partName, location, user);
        partId = PartDAO.select(partName, location, user);

        if (inhouseRB.isSelected()) {
            Workstation workstation = (Workstation) sourceCB.getSelectionModel().getSelectedItem();
            int workstationId = workstation.getId();
            InHouseDAO.insert(workstationId,partId, user);
        }

        if (outsourceRB.isSelected()) {
            Distributor distributor = (Distributor) sourceCB.getSelectionModel().getSelectedItem();
            int distributorId = distributor.getId();
            OutsourcedDAO.insert(distributorId, partId, user);
        }

        Parent primaryViewParent = FXMLLoader.load(getClass().getResource("/parks/inventorymanager/view/primaryView.fxml"));
        Scene primaryViewScene = new Scene(primaryViewParent);
        primaryViewScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        Stage primaryViewWindow = (Stage) ((Node)saveButtonClicked.getSource()).getScene().getWindow();

        primaryViewWindow.setTitle("Engine-uity Rebuilds Inventory Manager");
        primaryViewWindow.setScene(primaryViewScene);
        primaryViewWindow.show();

    }

    /** Cancels the creation of a new part and returns to the primary view.
     * @param cancelButtonClicked The cancel button is clicked. */
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
            primaryViewScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            Stage primaryViewWindow = (Stage) ((Node)cancelButtonClicked.getSource()).getScene().getWindow();

            primaryViewWindow.setTitle("Engine-uity Rebuilds Inventory Manager");
            primaryViewWindow.setScene(primaryViewScene);
            primaryViewWindow.show();
        }

    }

}
