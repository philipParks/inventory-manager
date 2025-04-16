package parks.inventorymanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import parks.inventorymanager.dao.EngineDAO;
import parks.inventorymanager.dao.EnginePartDAO;
import parks.inventorymanager.dao.InHouseDAO;
import parks.inventorymanager.dao.OutsourcedDAO;
import parks.inventorymanager.model.*;
import parks.inventorymanager.util.HelpMethods;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static parks.inventorymanager.controller.LoginController.authUser;

/** Defines an engine view controller. */
public class EngineViewController implements Initializable {

    @FXML
    private Label engineIdLabel;
    @FXML
    private TextField nomenclatureTxt;
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
    private TableView<Part> allPartsTable;
    @FXML
    private TableColumn<?, Integer> apNumberCol;
    @FXML
    private TableColumn<?, String> apNameCol;
    @FXML
    private TableColumn<?, String> apLocationCol;

    @FXML
    private TableView<Part> enginePartsTable;
    @FXML
    private TableColumn<?, Integer> epNumberCol;
    @FXML
    private TableColumn<?, String> epNameCol;
    @FXML
    private TableColumn<?, String> epLocationCol;

    @FXML
    private Label systemMessageLabel;

    private ObservableList allOutsourcedParts = OutsourcedDAO.selectAll();
    private ObservableList allInhouseParts = InHouseDAO.selectAll();
    private ObservableList allParts = FXCollections.concat(allOutsourcedParts, allInhouseParts);
    private ObservableList<Part> allAssociatedParts = FXCollections.observableArrayList();

    /** Transfers an engine object from another view.
     * @param selectedEngine The engine to transfer to the engine view. */
    public void transferEngine(Engine selectedEngine) {

        ObservableList<EnginePart> engineParts = EnginePartDAO.selectAssociatedParts(selectedEngine.getId());
        engineIdLabel.setText(String.valueOf(selectedEngine.getId()));
        nomenclatureTxt.setText(selectedEngine.getNomenclature());
        serialNumberTxt.setText(selectedEngine.getSerialNumber());
        fuelTypeTxt.setText(selectedEngine.getFuelType());
        cylinderTxt.setText(selectedEngine.getCylinders());
        statusTxt.setText(selectedEngine.getStatus());
        locationTxt.setText(selectedEngine.getLocation());

        for (Object part : allParts) {
            int partId;
            if (part.getClass() == Inhouse.class) {
                partId = ((Inhouse) part).getId();
            } else {
                partId = ((Outsourced) part).getId();
            }
            for (EnginePart enginePart : engineParts) {
                if (enginePart.getPartId() == partId) {
                    allAssociatedParts.add((Part) part);
                }
            }
        }

    }

    /** Implements initialization. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        systemMessageLabel.setText("");

        allPartsTable.setItems(allParts);
        allPartsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        apNumberCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        apNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        apLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        enginePartsTable.setItems(allAssociatedParts);
        enginePartsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        epNumberCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        epNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        epLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

    }

    /** adds a part to the associated parts. */
    public void onAddPartClick() {

        int id = Integer.parseInt(engineIdLabel.getText());

        Part part = allPartsTable.getSelectionModel().getSelectedItem();

        allAssociatedParts.add(part);
        EnginePartDAO.insert(id, part.getId(), authUser.getUserId());
        enginePartsTable.setItems(allAssociatedParts);

    }

    /** Removes a part from the associated parts. */
    public void onRemovePartClick() {
        int partId = 0;

        try {
            partId = enginePartsTable.getSelectionModel().getSelectedItem().getId();
        } catch (NullPointerException e) {
            systemMessageLabel.setText("Please select an engine part to remove from the engine part table.");
        }

        EnginePartDAO.delete(partId);
        allAssociatedParts.remove(partId);
        enginePartsTable.setItems(allAssociatedParts);
    }

    /** Saves the engine information.
     * @param saveButtonClicked The save button was clicked.
     * @throws IOException If an I/O error occurs with the primary view.*/
    public void onSaveClick(ActionEvent saveButtonClicked) throws IOException {
        int engineId = Integer.parseInt(engineIdLabel.getText());
        String engine = nomenclatureTxt.getText();
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

        EngineDAO.update(engineId, engine, location, status, fuelType, serialNumber, cylinder, authUser.getUserId());

        HelpMethods.primaryViewLoader(saveButtonClicked);
    }

    /** Cancels the work done on an engine.
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
