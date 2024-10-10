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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** Controls the addPartView.fxml file of the program. */
public class addPartViewController implements Initializable {
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

    /** Overrides the initialize method with statements that set the view. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        systemMessageLabel.setText("");
    }

    /** Changes the text and prompt text for partSource to Machine ID. */
    @FXML
    public void onInHouseSelect() {
        partSourceLabel.setText("Machine ID");
        partSourceTxt.setPromptText("Machine ID");
    }

    /** Changes the text and prompt text for partSource to Company Name. */
    @FXML
    public void onOutsourcedSelect() {
        partSourceLabel.setText("Company Name");
        partSourceTxt.setPromptText("Company Name");
    }

    /** Creates a new Part in the Inventory variable, allParts. After the Part has been added, the view is changed to the main view.
     * @param saveButtonClicked An action event initiated by interaction with the Save button.
     * @throws IOException Ensures there is an accurate asset to load.
     * */
    @FXML
    public void onSaveButtonClick(ActionEvent saveButtonClicked) throws IOException {
        int id = 0;
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
            systemMessageLabel.setText("** ATTENTION ==> Cost must be a number **");
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
            Inventory.addPart(new InHouse(id, name, cost, inv, min, max, machineId));
        }
        if (outsourcedPart.isSelected()){
            if (company.isBlank()) {
                systemMessageLabel.setText("** ATTENTION ==> The company field must not be blank **");
                return;
            }
            Inventory.addPart(new Outsourced(id, name, cost, inv, min, max, company));
        }

        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/parks/inventorymanager/view/mainView.fxml"));
        Scene mainViewScene = new Scene(mainViewParent);
        Stage mainViewWindow = (Stage) ((Node)saveButtonClicked.getSource()).getScene().getWindow();

        mainViewWindow.setTitle("Inventory Manager - Main");
        mainViewWindow.setScene(mainViewScene);
        mainViewWindow.show();
    }

    /** Returns to the main view. Asks the user to confirm that they wanted to cancel the creation of a new part.
     * @param cancelButtonClicked An action event initiated by interaction with the Cancel button.
     * @throws IOException Ensures there is an accurate asset to load.
     * */
    @FXML
    public void onCancelButtonClick(ActionEvent cancelButtonClicked) throws IOException {
        Alert partAddCancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        partAddCancelAlert.setTitle("Cancel Add Part Dialog");
        partAddCancelAlert.setHeaderText("Are you sure you want to cancel?");
        partAddCancelAlert.setContentText("Any changes you made will not be saved");
        Optional<ButtonType> result = partAddCancelAlert.showAndWait();


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
