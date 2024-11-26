package parks.inventorymanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import parks.inventorymanager.dao.*;
import parks.inventorymanager.model.Outsourced;

import java.io.IOException;
/** This class launches an application for the management of inventory.
 * @author Philip Parks */
public class inventoryManagerMain extends Application {

    /** This method overrides the start method of Application.
     * Sets the stage for the initial view.
     * @param stage The first scene to display.*/
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(inventoryManagerMain.class.getResource("/parks/inventorymanager/view/loginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    /** This is the main method.
     * @param args The statements of the program. */
    public static void main(String[] args) {
        JDBConnection.openConnection();
        launch();
        JDBConnection.closeConnection();
    }

}