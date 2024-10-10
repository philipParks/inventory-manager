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
import parks.inventorymanager.model.Inventory;
import parks.inventorymanager.model.Part;
import parks.inventorymanager.model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
/** Controls the addProductView.fxml file of the program. */
public class addProductViewController implements Initializable {
    @FXML
    private TextField productIdTxt;
    @FXML
    private TextField productNameTxt;
    @FXML
    private TextField productPriceTxt;
    @FXML
    private TextField productInvTxt;
    @FXML
    private TextField productMinTxt;
    @FXML
    private TextField productMaxTxt;
    @FXML
    private TextField partSearchTxt;
    @FXML
    private TableView<Part> partsInvTable;
    @FXML
    private TableColumn partsTableIdCol;
    @FXML
    private TableColumn partsTableNameCol;
    @FXML
    private TableColumn partsTableInvCol;
    @FXML
    private TableColumn partsTableCPUCol;
    @FXML
    private TableView<Part> productPartTable;
    @FXML
    private TableColumn productPartIdCol;
    @FXML
    private TableColumn productPartNameCol;
    @FXML
    private TableColumn productPartInvCol;
    @FXML
    private TableColumn productPartCPUCol;
    @FXML
    private Label systemMessageLabel;

    private ObservableList<Part> allParts = Inventory.getAllParts();
    private ObservableList<Part> allProductAssociatedParts = FXCollections.observableArrayList();

    /** Overrides the initialize method with statements that set the view and populate the tables. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        systemMessageLabel.setText("");

        partsInvTable.setItems(allParts);
        partsInvTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        productPartTable.setItems(allProductAssociatedParts);
        productPartTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        partsTableIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partsTableNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partsTableInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partsTableCPUCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        productPartIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productPartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productPartInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productPartCPUCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    /** Adds a selected Part from the partsInvTable to the productPartTable. */
    @FXML
    public void onAddPartButtonClick() {
        Part selectedPart = partsInvTable.getSelectionModel().getSelectedItem();

        try {
            allProductAssociatedParts.add(selectedPart);
            systemMessageLabel.setText("");
        } catch (NullPointerException e) {
            systemMessageLabel.setText("** ATTENTION ==> A part must be selected to add **");
        }
    }

    /** Removes a selected Part from the productPartTable. */
    @FXML
    public void onRemoveButtonClick() {
        Part selectedPart = productPartTable.getSelectionModel().getSelectedItem();

        try {
            Alert partRemovalAlert = new Alert(Alert.AlertType.CONFIRMATION);
            partRemovalAlert.setTitle("Remove Part Dialog");
            partRemovalAlert.setHeaderText("Are you sure you want to remove \"" + selectedPart.getName() + "\"?");
            partRemovalAlert.setContentText("This action cannot be undone.");
            Optional<ButtonType> result = partRemovalAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                allProductAssociatedParts.remove(selectedPart);
                productPartTable.setItems(allProductAssociatedParts);
                systemMessageLabel.setText("");
            }
        } catch (NullPointerException e) {
            systemMessageLabel.setText("** ATTENTION ==> A part must be selected to remove **");
        }
    }

    /** This method displays all entries that match the text in the part search text field when the Search button is clicked. */
    @FXML
    public void onSearchButtonClick() {
        String searchInput = partSearchTxt.getText();
        ObservableList<Part> searchedParts = Inventory.lookUpPart(searchInput);

        try {
            int partId = Integer.parseInt(searchInput);
            Part part = Inventory.lookUpPart(partId);

            if (part != null) {
                searchedParts.add(part);
            }
        } catch (NumberFormatException e) {
            //IGNORE
        }

        if (searchedParts.isEmpty()) {
            systemMessageLabel.setText("** ATTENTION ==> \"" + searchInput + "\" was not found **");
            return;
        }

        partsInvTable.setItems(searchedParts);
        systemMessageLabel.setText("");
        partSearchTxt.setText("");
    }

    /** Creates a new Product in the Inventory variable, allProducts. After the Product has been added, the view is changed to the naim view.
     * @param saveProductButtonClicked An action event initiated by interaction with the Save button.
     * @throws IOException Ensures there is an accurate asset to load.
     * */
    @FXML
    public void onSaveProductButtonClick(ActionEvent saveProductButtonClicked) throws IOException {
        FXMLLoader mainViewLoader = new FXMLLoader();
        mainViewLoader.setLocation(getClass().getResource("/parks/inventorymanager/view/mainView.fxml"));
        mainViewLoader.load();

        int id = 0;
        String name = productNameTxt.getText();
        double cost;
        int inv;
        int min;
        int max;

        if (name.isBlank()) {
            systemMessageLabel.setText("** ATTENTION ==> The product name field must not be blank **");
            return;
        }
        try {
            cost = Double.parseDouble(productPriceTxt.getText());
        } catch (NumberFormatException e) {
            systemMessageLabel.setText("** ATTENTION ==> Cost must be a number **");
            return;
        }
        try {
            inv = Integer.parseInt(productInvTxt.getText());
        } catch (NumberFormatException e) {
            systemMessageLabel.setText("** ATTENTION ==> Inv must be a number **");
            return;
        }
        try {
            min = Integer.parseInt(productMinTxt.getText());
        } catch (NumberFormatException e) {
            systemMessageLabel.setText("** ATTENTION ==> Min must be a number **");
            return;
        }
        try {
            max = Integer.parseInt(productMaxTxt.getText());
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
        // Create a new product
        Product newProduct = new Product(id, name, cost, inv, min, max);
        // Add parts to the product
        for (Part part : allProductAssociatedParts) {
            newProduct.addAssociatedPart(part);
        }
        // Add product to Inventory
        Inventory.addProduct(newProduct);

        allProductAssociatedParts.clear();

        Parent mainViewParent = mainViewLoader.getRoot();
        Scene mainViewScene = new Scene(mainViewParent);
        Stage mainViewWindow = (Stage) ((Node)saveProductButtonClicked.getSource()).getScene().getWindow();

        mainViewWindow.setTitle("Inventory Manager - Main");
        mainViewWindow.setScene(mainViewScene);
        mainViewWindow.show();
    }

    /** Returns to the main view. Asks the user to confirm that they wanted to cancel the creation of a new product.
     * @param cancelButtonClicked An action event initiated by interaction with the Cancel button.
     * @throws IOException Ensures there is an accurate asset to load.
     * */
    @FXML
    public void onCancelButtonClick(ActionEvent cancelButtonClicked) throws IOException {
        Alert productAddCancelAlert = new Alert(Alert.AlertType.CONFIRMATION);
        productAddCancelAlert.setTitle("Cancel Add Product Dialog");
        productAddCancelAlert.setHeaderText("Are you sure you want to cancel?");
        productAddCancelAlert.setContentText("Any changes you have made will not be saved.");
        Optional<ButtonType> result = productAddCancelAlert.showAndWait();

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
