package parks.inventorymanager.controller;

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
/** This class controls the mainView.fxml file of the program. */
public class mainViewController implements Initializable {
    @FXML
    private TextField partSearch;
    @FXML
    private TextField productSearch;
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn partIdCol;
    @FXML
    private TableColumn partNameCol;
    @FXML
    private TableColumn partInvCol;
    @FXML
    private TableColumn partCostCol;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn productIdCol;
    @FXML
    private TableColumn productNameCol;
    @FXML
    private TableColumn productInvCol;
    @FXML
    private TableColumn productCostCol;
    @FXML
    private Label systemMessageLabel;

    private final ObservableList<Part> allParts = Inventory.getAllParts();
    private final ObservableList<Product> allProducts = Inventory.getAllProducts();

    /** Overrides the initialize method with statements that populate the table views. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        systemMessageLabel.setText("");

        partsTableView.setItems(allParts);
        partsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        productsTableView.setItems(allProducts);
        productsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    /** This method displays all entries that match the text in the Part panel search text area when the Search button is clicked. */
    @FXML
    public void onPartSearchButtonClick() {
        String searchInput = partSearch.getText();
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

        partsTableView.setItems(searchedParts);
        systemMessageLabel.setText("");
        partSearch.setText("");
    }

    /** This method displays all entries that match the text in the Product panel search text area when the Search button is clicked. */
    @FXML
    public void onProductSearchButtonClick() {
        String searchInput = productSearch.getText();
        ObservableList<Product> searchedProducts = Inventory.lookUpProduct(searchInput);

        try {
            int productId = Integer.parseInt(searchInput);
            Product product = Inventory.lookUpProduct(productId);

            if (product != null) {
                searchedProducts.add(product);
            }
        } catch (NumberFormatException e) {
            //IGNORE
        }

        if (searchedProducts.isEmpty()) {
            systemMessageLabel.setText("** ATTENTION ==> \"" + searchInput + "\" was not found **");
            return;
        }

        productsTableView.setItems(searchedProducts);
        systemMessageLabel.setText("");
        partSearch.setText("");
    }

    /** Changes the view to the 'add part view'.
     * @param addPartButtonClicked An action event initiated by interaction with the Add button.
     * @throws IOException Ensures there is an accurate asset to load.
     * */
    @FXML
    public void onAddPartButtonClick(ActionEvent addPartButtonClicked) throws IOException {
        Parent addPartViewParent = FXMLLoader.load(getClass().getResource("/parks/inventorymanager/view/addPartView.fxml"));
        Scene addPartViewScene = new Scene(addPartViewParent);
        Stage addPartViewWindow = (Stage) ((Node)addPartButtonClicked.getSource()).getScene().getWindow();

        addPartViewWindow.setTitle("Inventory Manager - Add Part");
        addPartViewWindow.setScene(addPartViewScene);
        addPartViewWindow.show();
    }

    /** Changes the view to the 'modify part view'.
     * RUNTIME ERROR the modify part screen would not load. Corrected by creating a method in the modify part view
     * that would transfer information from the main view to the modify parts view.
     * @param modifyButtonClicked An action event initiated by interaction with the Modify button.
     * @throws IOException Ensures there is an accurate asset to load.
     * */
    @FXML
    public void onModifyPartButtonClick(ActionEvent modifyButtonClicked) throws IOException {
        FXMLLoader modifyPartViewLoader = new FXMLLoader();
        modifyPartViewLoader.setLocation(getClass().getResource("/parks/inventorymanager/view/modifyPartView.fxml"));
        modifyPartViewLoader.load();
        modifyPartViewController MPVController = modifyPartViewLoader.getController();

        try {
            MPVController.transferPart(partsTableView.getSelectionModel().getSelectedItem());
        } catch (NullPointerException e) {
            systemMessageLabel.setText("** ATTENTION ==> Select a part from the parts table to modify **");
            return;
        }

        Parent modifyPartViewParent = modifyPartViewLoader.getRoot();
        Scene modifyPartViewScene = new Scene(modifyPartViewParent);
        Stage modifyPartViewWindow = (Stage) ((Node) modifyButtonClicked.getSource()).getScene().getWindow();

        modifyPartViewWindow.setTitle("Inventory Manager - Modify Part");
        modifyPartViewWindow.setScene(modifyPartViewScene);
        modifyPartViewWindow.show();
    }

    /** Removes the selected Part from the allParts list and the partsTableView. */
    @FXML
    public void onPartDeleteButtonClick() {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();

        try {
            Inventory.deletePart(selectedPart);
            partsTableView.setItems(allParts);
            systemMessageLabel.setText("");
        } catch (Exception e) {
            systemMessageLabel.setText("** ATTENTION ==> A part must be selected to delete **");
        }

    }

    /** Changes the view to the 'add product view'.
     * @param addProductButtonClicked An action event initiated by interaction with the Add button.
     * @throws IOException Ensures there is an accurate asset to load.
     * */
    @FXML
    public void onAddProductButtonClick(ActionEvent addProductButtonClicked) throws IOException {
        Parent addProductViewParent = FXMLLoader.load(getClass().getResource("/parks/inventorymanager/view/addProductView.fxml"));
        Scene addProductViewScene = new Scene(addProductViewParent);
        Stage addProductViewWindow = (Stage) ((Node)addProductButtonClicked.getSource()).getScene().getWindow();

        addProductViewWindow.setTitle("Inventory Manager - Add Product");
        addProductViewWindow.setScene(addProductViewScene);
        addProductViewWindow.show();
    }

    /** Changes the view to the 'modify product view'.
     * @param modifyProductButtonClicked An action event initiated by interaction with the Cancel button.
     * @throws IOException Ensures there is an accurate asset to load.
     * */
    @FXML
    public void onModifyProductButtonClick(ActionEvent modifyProductButtonClicked) throws IOException {
        FXMLLoader modifyProductViewLoader = new FXMLLoader();
        modifyProductViewLoader.setLocation(getClass().getResource("/parks/inventorymanager/view/modifyProductView.fxml"));
        modifyProductViewLoader.load();
        modifyProductViewController MPVController = modifyProductViewLoader.getController();

        try {
            MPVController.transferProduct(productsTableView.getSelectionModel().getSelectedItem());
        } catch (NullPointerException e) {
            systemMessageLabel.setText("** ATTENTION ==> A product must be selected to modify **");
            return;
        }

        Parent modifyProductViewParent = modifyProductViewLoader.getRoot();
        Scene modifyProductViewScene = new Scene(modifyProductViewParent);
        Stage modifyProductViewWindow = (Stage) ((Node) modifyProductButtonClicked.getSource()).getScene().getWindow();

        modifyProductViewWindow.setTitle("Inventory Manager - Modify Product");
        modifyProductViewWindow.setScene(modifyProductViewScene);
        modifyProductViewWindow.show();
    }

    /** Removes the selected Product from the allProducts list and the productsTableView. */
    @FXML
    public void onProductDeleteButtonClick() {

        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert associatedPartsAlert = new Alert(Alert.AlertType.WARNING);
            associatedPartsAlert.setTitle("Associated Part Warning");
            associatedPartsAlert.setHeaderText("There are " + selectedProduct.getAllAssociatedParts().size() + " Parts " +
                    "still associated with \"" + selectedProduct.getName() + "\"");
            associatedPartsAlert.setContentText("Please remove all associated Parts from \"" + selectedProduct.getName() +
                    "\" by clicking on the 'Modify' button under the Products table and removing the Parts from the " +
                    "bottom table.");
            Optional<ButtonType> result = associatedPartsAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                return;
            }
        }

        try {
            Inventory.deleteProduct(selectedProduct);
            productsTableView.setItems(allProducts);
            systemMessageLabel.setText("");
        } catch (NullPointerException e) {
            systemMessageLabel.setText("** ATTENTION ==> A product must be selected to delete **");
        }
    }

    /** Exits the program. */
    @FXML
    public void onExitButtonClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Click \"OK\" to exit\nClick \"Cancel\" to return to the program\n");
        alert.setTitle("Exit Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
