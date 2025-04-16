package parks.inventorymanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import parks.inventorymanager.dao.UserDAO;
import parks.inventorymanager.model.User;
import parks.inventorymanager.util.HelpMethods;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/** The log-in view controller.
 * @author Philip Parks */
public class LoginController implements Initializable {

    @FXML
    private Label warnLabel;
    @FXML
    private TextField passwordTxtFld;
    @FXML
    private TextField userNameTxtFld;

    /** The logged-in user. */
    public static User authUser;

    /** Implements initialization. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        warnLabel.setText("");

    }

    /** Verifies credentials and loads the main view.
     * @param buttonClicked The submit button is clicked.
     * @throws IOException If an I/O error occurs with the primary view. */
    public void submitClicked(ActionEvent buttonClicked) throws IOException {
        String userName = userNameTxtFld.getText();
        String password = passwordTxtFld.getText();
        authUser = UserDAO.select(userName, password);
        // Set up the login_activity.txt file
        String fileName = "login_activity.txt";
        ZonedDateTime currentLocalZDT = ZonedDateTime.now();
        FileWriter writeToFile = new FileWriter(fileName, true);
        PrintWriter targetFile = new PrintWriter(writeToFile);

        if (userName.isEmpty()) {
            warnLabel.setText("Please enter a valid user name.");
            return;
        }

        if (password.isEmpty()) {
            warnLabel.setText("Please enter a valid password");
            return;
        }

        if (authUser == null) {
            targetFile.println("Failed log in: " + currentLocalZDT + " Username: " + userName + " Password: " + password);
            targetFile.close();
            warnLabel.setText("Incorrect user name or password");
            return;
        } else {
            targetFile.println("Successful log in: " + currentLocalZDT + " Username: " + userName);
            targetFile.close();
        }

        HelpMethods.primaryViewLoader(buttonClicked);
    }

    /** Closes the application. */
    public void cancelClicked() {
        Alert exitAlert = new Alert(Alert.AlertType.WARNING, "Click \"OK\" to close the application\nClick \"Cancel\" to return to the login screen\n");
        exitAlert.setTitle("Cancel confirmation dialog");
        exitAlert.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> result = exitAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("Application closed!");
            System.exit(0);
        }
    }

}
