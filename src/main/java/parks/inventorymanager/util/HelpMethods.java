package parks.inventorymanager.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HelpMethods {

    public static void loadScene(ActionEvent buttonClicked, String view, String style, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelpMethods.class.getResource(view));
        loader.load();

        Parent parent = loader.getRoot();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(HelpMethods.class.getResource(style).toExternalForm());
        Stage stage = (Stage) ((Node) buttonClicked.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void loadScene(ActionEvent buttonClicked, FXMLLoader loader, String style, String title) {
        Parent parent = loader.getRoot();
        Scene scene = new Scene(parent);
        scene.getStylesheets().add(HelpMethods.class.getResource(style).toExternalForm());
        Stage stage = (Stage) ((Node) buttonClicked.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static void primaryViewLoader(ActionEvent buttonClicked) throws IOException {
        Parent primaryViewParent = FXMLLoader.load(HelpMethods.class.getResource("/parks/inventorymanager/view/primaryView.fxml"));
        Scene primaryViewScene = new Scene(primaryViewParent);
        primaryViewScene.getStylesheets().add(HelpMethods.class.getResource("/style.css").toExternalForm());
        Stage primaryViewWindow = (Stage) ((Node)buttonClicked.getSource()).getScene().getWindow();

        primaryViewWindow.setTitle("Engine-uity Rebuilds Inventory Manager");
        primaryViewWindow.setScene(primaryViewScene);
        primaryViewWindow.show();
    }
}
