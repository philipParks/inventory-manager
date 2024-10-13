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
import javafx.stage.Stage;
import parks.inventorymanager.dao.UserQuery;
import parks.inventorymanager.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label passwordLbl;
    @FXML
    private Label usernameLbl;
    @FXML
    private Label credentialsLbl;
    @FXML
    private Label zoneLabel;
    @FXML
    private Label warnLabel;
    @FXML
    private TextField passwordTxtFld;
    @FXML
    private TextField userNameTxtFld;

    public static User authUser;

    private ResourceBundle rb;

    /** Overrides the initialize method. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ZoneId systemZone = ZoneId.systemDefault();
        String zoneId = systemZone.getId();
        zoneLabel.setText(zoneId);
        warnLabel.setText("");

    }

    /** Verifies credentials and loads the main view. */
    public void submitButtonClicked(ActionEvent buttonClicked) throws IOException {
        String userName = userNameTxtFld.getText();
        String password = passwordTxtFld.getText();
        authUser = UserQuery.select(userName, password);
        // Set up the login_activity.txt file
        String fileName = "login_activity.txt";
        ZonedDateTime currentLocalZDT = ZonedDateTime.now();
        FileWriter writeToFile = new FileWriter(fileName, true);
        PrintWriter targetFile = new PrintWriter(writeToFile);
        // Set up the 15-minute alert
        Alert appointmentAlert = new Alert(Alert.AlertType.INFORMATION);
        appointmentAlert.setTitle(rb.getString("apptAlertTitle"));

        if (userName.isEmpty()) {
            warnLabel.setText("*** " + rb.getString("usernameError") + " ***");
            return;
        }

        if (password.isEmpty()) {
            warnLabel.setText("*** " + rb.getString("passwordError") + " ***");
            return;
        }

        if (authUser == null) {
            targetFile.println("Failed log in: " + currentLocalZDT + " Username: " + userName + " Password: " + password);
            targetFile.close();
            warnLabel.setText("*** " + rb.getString("credentialsError") + " ***");
            return;
        } else {
            targetFile.println("Successful log in: " + currentLocalZDT + " Username: " + userName);
            targetFile.close();
        }

        appointmentAlert.setHeaderText(rb.getString("thereIsNotAnAppointment"));
        appointmentAlert.setContentText(rb.getString("noAppointment"));
        appointmentAlert.showAndWait();

        FXMLLoader mainViewLoader = new FXMLLoader();
        mainViewLoader.setLocation(getClass().getResource("/com/TIME/view/mainView.fxml"));
        mainViewLoader.load();

        Parent mainViewParent = mainViewLoader.getRoot();
        Scene mainViewScene = new Scene(mainViewParent);
        Stage mainViewWindow = (Stage) ((Node) buttonClicked.getSource()).getScene().getWindow();

        mainViewWindow.setTitle("T.I.M.E.");
        mainViewWindow.setScene(mainViewScene);
        mainViewWindow.show();
    }

    /** Closes the application. */
    public void cancelButtonClicked() {
        Alert exitAlert = new Alert(Alert.AlertType.WARNING, rb.getString("cancelAlertText"));
        exitAlert.setTitle(rb.getString("cancelAlertTitle"));
        exitAlert.setHeaderText(rb.getString("cancelAlertHeader"));
        Optional<ButtonType> result = exitAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

}
