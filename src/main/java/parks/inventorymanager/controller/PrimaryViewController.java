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
import parks.inventorymanager.dao.*;
import parks.inventorymanager.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static parks.inventorymanager.controller.LoginController.authUser;

/** The primary view controller. */
public class PrimaryViewController implements Initializable {

    @FXML
    private Label systemMessageLabel;

    @FXML
    private TableView<Engine> engineTable;
    @FXML
    private TableColumn<?, Integer> engineIdCol;
    @FXML
    private TableColumn<?, String> nomenclatureCol;
    @FXML
    private TableColumn<?, String> serialNumberCol;
    @FXML
    private TableColumn<?, String> fuelCol;
    @FXML
    private TableColumn<?, String> cylinderCol;
    @FXML
    private TableColumn<?, String> statusCol;
    @FXML
    private TableColumn<?, Double> locationCol;
    @FXML
    private TextField engineSearchTxt;

    @FXML
    private TableView<Inhouse> inhouseTable;
    @FXML
    private TableColumn<?, Integer> inhouseNumberCol;
    @FXML
    private TableColumn<?, String> inhouseNameCol;
    @FXML
    private TableColumn<?, String> inhouseLocationCol;
    @FXML
    private TableColumn<?, String> workstationCol;
    @FXML
    private TextField inhouseSearchTxt;
    @FXML
    private TableView<Outsourced> outsourceTable;
    @FXML
    private TableColumn<?, Integer> outsourceNumberCol;
    @FXML
    private TableColumn<?, String> outsourceNameCol;
    @FXML
    private TableColumn<?, String> outsourceLocationCol;
    @FXML
    private TableColumn<?, String> distributorCol;
    @FXML
    private TextField outsourceSearchTxt;

    @FXML
    private Tab adminTab;
    @FXML
    private TableView<Inhouse> iprTable;
    @FXML
    private TableColumn<?, Integer> ihrNumberCol;
    @FXML
    private TableColumn<?, String> ihrNameCol;
    @FXML
    private TableColumn<?, String> ihrLocationCol;
    @FXML
    private ChoiceBox workstationCB;
    @FXML
    private TableView<Engine> erTable;
    @FXML
    private TableColumn<?, Integer> erNumberCol;
    @FXML
    private TableColumn<?, String> erNameCol;
    @FXML
    private TableColumn<?, String> erLocationCol;
    @FXML
    private ChoiceBox engineStatusCB;
    @FXML
    private TableView<Outsourced> oprTable;
    @FXML
    private TableColumn<?, Integer> orNumberCol;
    @FXML
    private TableColumn<?, String> orNameCol;
    @FXML
    private TableColumn<?, String> orLocationCol;
    @FXML
    private ChoiceBox distributorCB;
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private PasswordField reenterPasswordTxt;
    @FXML
    private CheckBox adminCheckBox;

    private final ObservableList<Engine>  allEngines = EngineDAO.selectAll();
    private final ObservableList<Outsourced> allOutsourcedParts = OutsourcedDAO.selectAll();
    private final ObservableList<Inhouse> allInhouseParts = InHouseDAO.selectAll();

    /** What to do when the view is initialized.
     * @param url The uniform resource locator.
     * @param resourceBundle The resource bundle. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (!authUser.getAdmin()) {
            adminTab.setDisable(true);
        }

        systemMessageLabel.setText("");
    }

    /** Set default tab view. */
    public void onEngineTabSelected() {

        engineTable.setItems(allEngines);
        engineTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        engineIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nomenclatureCol.setCellValueFactory(new PropertyValueFactory<>("Nomenclature"));
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<>("SerialNumber"));
        fuelCol.setCellValueFactory(new PropertyValueFactory<>("FuelType"));
        cylinderCol.setCellValueFactory(new PropertyValueFactory<>("Cylinders"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        try {
            systemMessageLabel.setText("");
        } catch (Exception e) {
            // DO NOTHING
        }

    }

    /** What to do when the new engine button is clicked.
     * @param buttonClicked New engine button clicked. */
    public void onNewEngineClick(ActionEvent buttonClicked) throws IOException {

        FXMLLoader engineViewLoader = new FXMLLoader();
        engineViewLoader.setLocation(getClass().getResource("/parks/inventorymanager/view/addEngineView.fxml"));
        engineViewLoader.load();

        Parent engineViewParent = engineViewLoader.getRoot();
        Scene engineViewScene = new Scene(engineViewParent);
        engineViewScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        Stage engineViewWindow = (Stage) ((Node) buttonClicked.getSource()).getScene().getWindow();

        engineViewWindow.setTitle("Engine-uity Rebuilds Inventory Manager");
        engineViewWindow.setScene(engineViewScene);
        engineViewWindow.show();

    }

    /** What to do when the new engine button is clicked.
     * @param buttonClicked Modify engine button clicked. */
    public void onModifyEngineClick(ActionEvent buttonClicked) throws IOException {

        FXMLLoader engineViewLoader = new FXMLLoader();
        engineViewLoader.setLocation(getClass().getResource("/parks/inventorymanager/view/engineView.fxml"));
        engineViewLoader.load();
        EngineViewController evpController = engineViewLoader.getController();

        try {
            evpController.transferEngine(engineTable.getSelectionModel().getSelectedItem());
        } catch (NullPointerException e) {
            systemMessageLabel.setText("An engine must be selected to be modify.");
            return;
        }

        Parent engineViewParent = engineViewLoader.getRoot();
        Scene engineViewScene = new Scene(engineViewParent);
        engineViewScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        Stage engineViewWindow = (Stage) ((Node) buttonClicked.getSource()).getScene().getWindow();

        engineViewWindow.setTitle("Engine-uity Rebuilds Inventory Manager");
        engineViewWindow.setScene(engineViewScene);
        engineViewWindow.show();

    }

    /** Deletes a selected engine. */
    public void onDeleteEngineClick() {

        Engine selectedEngine = engineTable.getSelectionModel().getSelectedItem();

        try {
            EngineDAO.delete(selectedEngine.getId());
            engineTable.setItems(allEngines);
            systemMessageLabel.setText("");
        } catch (NullPointerException e) {
            systemMessageLabel.setText("An engine must be selected to delete.");
        }

    }

    /** Search for engines that match the search input */
    public void onEngineSearchClick() {

        String searchInput = engineSearchTxt.getText();
        ObservableList<Engine> selectedEngines = FXCollections.observableArrayList();

        for (Engine engine : allEngines) {
            if (engine.getNomenclature().toLowerCase().contains(searchInput)) {
                selectedEngines.add(engine);
            }
        }

        try {
            int engineId = Integer.parseInt(searchInput);
            Engine engine = EngineDAO.select(engineId);

            if (engine != null) {
                selectedEngines.add(engine);
            }
        } catch (NumberFormatException e) {
            //IGNORE
        }

        if (selectedEngines.isEmpty()) {
            systemMessageLabel.setText("Engine: \"" + searchInput + "\" was not found.");
            return;
        }

        engineTable.setItems(selectedEngines);
        systemMessageLabel.setText("");
        engineSearchTxt.setText("");
    }

    /** Set default tab view. */
    public void onPartTabSelected() {

        inhouseTable.setItems(allInhouseParts);
        inhouseTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        inhouseNumberCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        inhouseNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        inhouseLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        workstationCol.setCellValueFactory(new PropertyValueFactory<>("Workstation"));

        outsourceTable.setItems(allOutsourcedParts);
        outsourceTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        outsourceNumberCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        outsourceNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        outsourceLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        distributorCol.setCellValueFactory(new PropertyValueFactory<>("Distributor"));

        systemMessageLabel.setText("");

    }

    /** Search for in house parts that match the search input. */
    public void onInhouseSearchClick() {

        String searchInput = inhouseSearchTxt.getText();
        ObservableList<Inhouse> selectedParts = FXCollections.observableArrayList();

        for (Inhouse part : allInhouseParts) {
            if (part.getName().toLowerCase().contains(searchInput)) {
                selectedParts.add(part);
            }
        }

        try {
            int partId = Integer.parseInt(searchInput);
            Inhouse part = InHouseDAO.select(partId);

            if (part != null) {
                selectedParts.add(part);
            }
        } catch (NumberFormatException e) {
            //IGNORE
        }

        if (selectedParts.isEmpty()) {
            systemMessageLabel.setText("Inhouse Part: \"" + searchInput + "\" was not found.");
            return;
        }

        inhouseTable.setItems(selectedParts);
        systemMessageLabel.setText("");
        engineSearchTxt.setText("");


    }

    /** What to do when the modify inhouse part button is clicked.
     * @param buttonClicked Modify in house part button clicked. */
    public void onModifyInhouseClick(ActionEvent buttonClicked) throws IOException {

        FXMLLoader partViewLoader = new FXMLLoader();
        partViewLoader.setLocation(getClass().getResource("/parks/inventorymanager/view/partView.fxml"));
        partViewLoader.load();
        PartViewController pvController = partViewLoader.getController();

        try {
            pvController.transferPart(inhouseTable.getSelectionModel().getSelectedItem());
        } catch (NullPointerException e) {
            systemMessageLabel.setText("Please select an in house part to modify.");
            return;
        }

        Parent partViewParent = partViewLoader.getRoot();
        Scene partViewScene = new Scene(partViewParent);
        partViewScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        Stage partViewWindow = (Stage) ((Node) buttonClicked.getSource()).getScene().getWindow();

        partViewWindow.setTitle("Engine-uity Rebuilds Inventory Manager");
        partViewWindow.setScene(partViewScene);
        partViewWindow.show();

    }

    /** Delete the selected in house part. */
    public void onDeleteInhouseClick() {

        Inhouse selectedInhousePart = inhouseTable.getSelectionModel().getSelectedItem();

        try {
            InHouseDAO.delete(selectedInhousePart.getId());
            inhouseTable.setItems(allInhouseParts);
            systemMessageLabel.setText("");
        } catch (NullPointerException e) {
            systemMessageLabel.setText("An in house part must be selected to delete.");
            return;
        }

        systemMessageLabel.setText("");

    }

    /** Search for outsource parts that match the search input. */
    public void onOutsourceSearchClick() {

        String searchInput = outsourceSearchTxt.getText();
        ObservableList<Outsourced> selectedParts = FXCollections.observableArrayList();

        for (Outsourced part : allOutsourcedParts) {
            if (part.getName().toLowerCase().contains(searchInput)) {
                selectedParts.add(part);
            }
        }

        try {
            int partId = Integer.parseInt(searchInput);
            Outsourced part = OutsourcedDAO.select(partId);

            if (part != null) {
                selectedParts.add(part);
            }
        } catch (NumberFormatException e) {
            //IGNORE
        }

        if (selectedParts.isEmpty()) {
            systemMessageLabel.setText("Outsourced Part: \"" + searchInput + "\" was not found.");
            return;
        }

        outsourceTable.setItems(selectedParts);
        systemMessageLabel.setText("");
        engineSearchTxt.setText("");

    }

    /** What to do when the modify outsourced button is clicked.
     * @param buttonClicked modify outsource part button clicked. */
    public void onModifyOutsourceClick(ActionEvent buttonClicked) throws IOException {

        FXMLLoader partViewLoader = new FXMLLoader();
        partViewLoader.setLocation(getClass().getResource("/parks/inventorymanager/view/partView.fxml"));
        partViewLoader.load();
        PartViewController pvController = partViewLoader.getController();

        try {
            pvController.transferPart(outsourceTable.getSelectionModel().getSelectedItem());
        } catch (NullPointerException e) {
            systemMessageLabel.setText("Please select an outsourced part to modify.");
            return;
        }

        Parent partViewParent = partViewLoader.getRoot();
        Scene partViewScene = new Scene(partViewParent);
        partViewScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        Stage partViewWindow = (Stage) ((Node) buttonClicked.getSource()).getScene().getWindow();

        partViewWindow.setTitle("Engine-uity Rebuilds Inventory Manager");
        partViewWindow.setScene(partViewScene);
        partViewWindow.show();

    }

    /** Delete the selected outsource part. */
    public void onDeleteOutsourceClick() {

        Outsourced selectedOutsourcePart = outsourceTable.getSelectionModel().getSelectedItem();

        try {
            OutsourcedDAO.delete(selectedOutsourcePart.getId());
            outsourceTable.setItems(allOutsourcedParts);
            systemMessageLabel.setText("");
        } catch (NullPointerException e) {
            systemMessageLabel.setText("An outsource part must be selected to delete.");
            return;
        }

        systemMessageLabel.setText("");

    }

    /** What to do when the add part button is clicked.
     * @param buttonClicked Add part button clicked. */
    public void onAddPartClick(ActionEvent buttonClicked) throws IOException {

        FXMLLoader partViewLoader = new FXMLLoader();
        partViewLoader.setLocation(getClass().getResource("/parks/inventorymanager/view/partView.fxml"));
        partViewLoader.load();

        Parent partViewParent = partViewLoader.getRoot();
        Scene partViewScene = new Scene(partViewParent);
        partViewScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        Stage partViewWindow = (Stage) ((Node) buttonClicked.getSource()).getScene().getWindow();

        partViewWindow.setTitle("Engine-uity Rebuilds Inventory Manager");
        partViewWindow.setScene(partViewScene);
        partViewWindow.show();

    }

    /** Set default tab view. */
    public void onAdminTabSelected() {

        workstationCB.setValue("Select a Workstation");
        workstationCB.setItems(WorkstationDAO.selectAll());
        iprTable.getItems().clear();
        iprTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ihrNumberCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        ihrNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ihrLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        distributorCB.setValue("Select a Distributor");
        distributorCB.setItems(DistributorDAO.selectAll());
        oprTable.getItems().clear();
        oprTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        orNumberCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        orNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        orLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        engineStatusCB.setValue("Select a Status");
        engineStatusCB.setItems(EngineDAO.getStatus());
        erTable.getItems().clear();
        erTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        erNumberCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        erNameCol.setCellValueFactory(new PropertyValueFactory<>("Nomenclature"));
        erLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        systemMessageLabel.setText("");

    }

    /** What to do when the add distributor button is clicked.
     * @param buttonClicked Button clicked. */
    public void onAddDistributorClick(ActionEvent buttonClicked) throws IOException {

        FXMLLoader distributorViewLoader = new FXMLLoader();
        distributorViewLoader.setLocation(getClass().getResource("/parks/inventorymanager/view/distributorView.fxml"));
        distributorViewLoader.load();

        Parent distributorViewParent = distributorViewLoader.getRoot();
        Scene distributorViewScene = new Scene(distributorViewParent);
        distributorViewScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        Stage distributorViewWindow = (Stage) ((Node) buttonClicked.getSource()).getScene().getWindow();

        distributorViewWindow.setTitle("Engine-uity Rebuilds Inventory Manager");
        distributorViewWindow.setScene(distributorViewScene);
        distributorViewWindow.show();

    }

    /** Adds a workstation.
     * @param buttonClicked Button clicked. */
    public void onAddWorkstationClick(ActionEvent buttonClicked) throws IOException {

        FXMLLoader workstationViewLoader = new FXMLLoader();
        workstationViewLoader.setLocation(getClass().getResource("/parks/inventorymanager/view/workstationView.fxml"));
        workstationViewLoader.load();

        Parent workstationViewParent = workstationViewLoader.getRoot();
        Scene workstationViewScene = new Scene(workstationViewParent);
        workstationViewScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        Stage workstationViewWindow = (Stage) ((Node) buttonClicked.getSource()).getScene().getWindow();

        workstationViewWindow.setTitle("Engine-uity Rebuilds Inventory Manager");
        workstationViewWindow.setScene(workstationViewScene);
        workstationViewWindow.show();

    }

    /** Saves user information. */
    public void onSaveUserClick() {

        String userName = usernameTxt.getText();
        String password = passwordTxt.getText();
        String reenteredPassword = reenterPasswordTxt.getText();
        boolean isAdmin = adminCheckBox.isSelected();

        if (userName.isBlank()) {
            systemMessageLabel.setText("A new user must have a username.");
            return;
        }

        if (password.isBlank()) {
            systemMessageLabel.setText("A new user must have a password.");
            return;
        }

        if (Objects.equals(reenteredPassword, password)) {
            UserDAO.insert(userName, password, isAdmin);
            usernameTxt.setText("");
            passwordTxt.setText("");
            reenterPasswordTxt.setText("");
            systemMessageLabel.setText("User has been successfully saved!");
        } else {
            systemMessageLabel.setText("Re-entered password does not match the entered password.");
            return;
        }

        systemMessageLabel.setText("");

    }

    /** Populates the inhouse part report table with parts that were made at the selected workstation. */
    public void runIprClick() {

        Workstation workstation = (Workstation) workstationCB.getSelectionModel().getSelectedItem();
        int id = 0;

        try {
            id = workstation.getId();
        } catch (NullPointerException e) {
            systemMessageLabel.setText("Please select a workstation.");
            return;
        }

        ObservableList<Inhouse> inhouseParts = InHouseDAO.selectAll(id);

        iprTable.setItems(inhouseParts);

    }

    /** Populates the engine reports table with engines with the selected status. */
    public void runEngineReportClick() {
        String selectedStatus = (String) engineStatusCB.getSelectionModel().getSelectedItem();

        if (selectedStatus.isBlank()) {
            systemMessageLabel.setText("Please select an engine status.");
            return;
        }

        ObservableList<Engine> enginesByStatus = EngineDAO.selectAll(selectedStatus);

        erTable.setItems(enginesByStatus);

        systemMessageLabel.setText("");

    }

    /** Populates the outsource part report table with parts that were received from the selected distributor. */
    public void runOprClick() {

        Distributor distributor = (Distributor) distributorCB.getSelectionModel().getSelectedItem();
        int id = 0;

        try {
            id = distributor.getId();
        } catch (NullPointerException e) {
            systemMessageLabel.setText("Please select a distributor.");
            return;
        }

        ObservableList<Outsourced> outsourcedParts = OutsourcedDAO.selectAll(id);

        oprTable.setItems(outsourcedParts);

    }

    /** Returns the user to the login screen.
     * @param buttonClicked Logout button clicked. */
    public void onLogOutClick(ActionEvent buttonClicked) throws IOException {

        Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION);
        logoutAlert.setTitle("Logout Confirmation Dialog");
        logoutAlert.setHeaderText("Are you sure you want to logout?");
        logoutAlert.setContentText("click \"OK\" to return to the login screen\nclick \"Cancel\" to stay logged in.");
        Optional<ButtonType> result = logoutAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            FXMLLoader loginViewLoader = new FXMLLoader();
            loginViewLoader.setLocation(getClass().getResource("/parks/inventorymanager/view/loginView.fxml"));
            loginViewLoader.load();

            Parent loginViewParent = loginViewLoader.getRoot();
            Scene loginViewScene = new Scene(loginViewParent);
            loginViewScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            Stage loginViewWindow = (Stage) ((Node)buttonClicked.getSource()).getScene().getWindow();

            loginViewWindow.setTitle("Login");
            loginViewWindow.setScene(loginViewScene);
            loginViewWindow.show();
        }

    }

}
