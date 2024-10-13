package parks.inventorymanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/** This class launches an application for the management of inventory.
 * @author Philip Parks */
public class inventoryManagerMain extends Application {

    /** This method overrides the start method of Application.
     * Sets the stage for the initial view.
     * @param stage The first scene to display.*/
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(inventoryManagerMain.class.getResource("/parks/inventorymanager/view/mainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Inventory Manager - Main");
        stage.setScene(scene);
        stage.show();
    }

    /** This is the main method.
     * Javadocs Location: inventorymanager/src/main/resources/javadoc
     * FUTURE ENHANCEMENT include a means of associating a quantity of individual parts required for a product.
     * Example: a bicycle would need 2 wheels. Instead of just add a wheel(Part) as a required Part you would also
     * include how many of those wheels(Part) you need to make the bicycle(Product).
     * @param args The statements of the program.
     * */
    public static void main(String[] args) {

        // TODO finish building database
        // create functions that will refresh the data
        // ensure SQL is secure
        // test connection to database
        // TODO finish setting up testing
        // create test files for all classes
        // set up any other tests that may need to be performed
        // TODO finish data access objects
        // everything in the database needs to be accessible
        // TODO set up reports and records
        // record login activity
        // make an option to print or export reports

        launch();
    }

}