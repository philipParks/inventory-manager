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
import parks.inventorymanager.model.InHouse;
import parks.inventorymanager.model.Inventory;
import parks.inventorymanager.model.Outsourced;
import parks.inventorymanager.model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** Controls the modifyPartView.fxml file of the program. */
public class modifyPartViewController implements Initializable {
    @FXML
    private RadioButton inHousePart;
    @FXML
    private RadioButton outsourcedPart;
    @FXML
    private ToggleGroup partSourceTG;
    @FXML
    private TextField partIdTxt;
    @FXML
    private TextField partNameTxt;
    @FXML
    private TextField partCostTxt;
    @FXML
    private TextField partInvTxt;
    @FXML
    private TextField partMinTxt;
    @FXML
    private TextField partMaxTxt;
    @FXML
    private TextField partSourceTxt;
    @FXML
    private Label partSourceLabel;
    @FXML
    private Label systemMessageLabel;

    private int indexOfSelectedPart;

    /** This method populates the text fields with data from a selected Part.
     * @param selectedPart the Part selected to populate the text fields.
     * */
    public void transferPart(Part selectedPart) {
        indexOfSelectedPart = Inventory.getAllParts().indexOf(selectedPart);
        if (selectedPart instanceof InHouse) {
            partSourceTG.selectToggle(inHousePart);
            partSourceLabel.setText("Machine ID");
            partSourceTxt.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        } else if (selectedPart instanceof Outsourced) {
            partSourceTG.selectToggle(outsourcedPart);
            partSourceLabel.setText("Company Name");
            partSourceTxt.setText(((Outsourced) selectedPart).getCompanyName());
        }

        partIdTxt.setText(String.valueOf(selectedPart.getId()));
        partNameTxt.setText(selectedPart.getName());
        partCostTxt.setText(String.valueOf(selectedPart.getPrice()));
        partInvTxt.setText(String.valueOf(selectedPart.getStock()));
        partMinTxt.setText(String.valueOf(selectedPart.getMin()));
        partMaxTxt.setText(String.valueOf(selectedPart.getMax()));
    }

    /** Overrides the initialize method with statements that set the view. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        systemMessageLabel.setText("");
    }

    /** Changes the text for partSource to Machine ID. */
    @FXML
    public void onInHouseSelect() {
        partSourceLabel.setText("Machine ID");
        partSourceTxt.setPromptText("Machine ID");
    }

    /** Changes the text for partSource to Company Name. */
    @FXML
    public void onOutsourcedSelect() {
        partSourceLabel.setText("Company Name");
        partSourceTxt.setPromptText("Company Name");
    }

    /** Creates a new Part in the Inventory variable, allParts that replaces the information from transferPart.
     * @param saveButtonClicked An action event initiated by interaction with the Save button.
     * @throws IOException Ensures there is an accurate asset to load.
     * */
    @FXML
    public void onSaveButtonClick(ActionEvent saveButtonClicked) throws IOException {
        FXMLLoader mainViewLoader = new FXMLLoader();
        mainViewLoader.setLocation(getClass().getResource("/parks/inventorymanager/view/mainView.fxml"));
        mainViewLoader.load();

        int id = Integer.parseInt(partIdTxt.getText());
        String name = partNameTxt.getText();
        double cost;
        int inv;
        int min;
        int max;
        int machineId;
        String company = partSourceTxt.getText();

        if (name.isBlank()) {
            systemMessageLabel.setText("** ATTENTION ==> The part name field must not be blank **");
            return;
        }

        try {
            cost = Double.parseDouble(partCostTxt.getText());
        } catch (NumberFormatException e) {
            systemMessageLabel.setText("** ATTENTION==> Cost must be a number **");
            return;
        }
        try {
            inv = Integer.parseInt(partInvTxt.getText());
        } catch (NumberFormatException e) {
            systemMessageLabel.setText("** ATTENTION ==> Inv must be a number **");
            return;
        }
        try {
            min = Integer.parseInt(partMinTxt.getText());
        } catch (NumberFormatException e) {
            systemMessageLabel.setText("** ATTENTION ==> Min must be a number **");
            return;
        }
        try {
            max = Integer.parseInt(partMaxTxt.getText());
        } catch (NumberFormatException e) {
            systemMessageLabel.setText("** ATTENTION ==> Max must be a number **");
            return;
        }

        if (inv < min || inv > max) {
            systemMessageLabel.setText("** ATTENTION ==> Max must be greater than Min and Inv must be between Min and Max **");
            return;
        }
        if (min < 0) {
            systemMessageLabel.setText("** ATTENTION ==> Min must be greater than 0 **");
            return;
        }

        if (inHousePart.isSelected()) {
            try {
                machineId = Integer.parseInt(partSourceTxt.getText());
            } catch (NumberFormatException e) {
                systemMessageLabel.setText("** ATTENTION ==> Machine ID must be a number **");
                return;
            }
            Part updatedInHousePart = new InHouse(id, name, cost, inv, min, max, machineId);
            Inventory.updatePart(indexOfSelectedPart, updatedInHousePart);
        }
        if (outsourcedPart.isSelected()){
            if (company.isBlank()) {
                systemMessageLabel.setText("** ATTENTION ==> The company field must not be blank **");
                return;
            }
            Part updatedOutsourcedPart = new Outsourced(id, name, cost, inv, min, max, company);
            Inventory.updatePart(indexOfSelectedPart, updatedOutsourcedPart);
        }

        Parent mainViewParent = mainViewLoader.getRoot();
        Scene mainViewScene = new Scene(mainViewParent);
        Stage mainViewWindow = (Stage) ((Node)saveButtonClicked.getSource()).getScene().getWindow();

        mainViewWindow.setTitle("Inventory Manager - Main");
        mainViewWindow.setScene(mainViewScene);
        mainViewWindow.show();
    }

    /** Returns to the main view. Asks the user to confirm that they wanted to cancel the modification of the selected Part.
     * @param cancelButtonClicked An action event initiated by interaction with the Cancel button.
     * @throws IOException Ensures there is an accurate asset to load.
     * */
    @FXML
    public void onCancelButtonClick(ActionEvent cancelButtonClicked) throws IOException {
        Alert modifyPartCancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        modifyPartCancelAlert.setTitle("Cancel Modify Part Dialog");
        modifyPartCancelAlert.setHeaderText("Are you sure you want to cancel?");
        modifyPartCancelAlert.setContentText("Changes you have made may not be saved");
        Optional<ButtonType> result = modifyPartCancelAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent mainViewParent = FXMLLoader.load(getClass().getResource("/parks/inventorymanager/view/mainView.fxml"));
            Scene mainViewScene = new Scene(mainViewParent);
            Stage mainViewWindow = (Stage) ((Node)cancelButtonClicked.getSource()).getScene().getWindow();
            mainViewWindow.setTitle("Inventory Manager - Main");
            mainViewWindow.setScene(mainViewScene);
            mainViewWindow.show();
        }
    }

}
