package utils;

import java.net.URL;

import home.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public final class Modal {

    public static Stage openScreen(String path) throws Exception {
        Stage primaryStage = Main.getPrimeryStage();
        
        // Scene secondScene = new Scene(secondaryLayout, 230, 100);
        URL urlResource = Modal.class.getResource(path);
        final Parent template = FXMLLoader.load(urlResource);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle(path);
        newWindow.setScene(new Scene(template, 600, 400));

        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(primaryStage);

        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);
        newWindow.setResizable(false);

        newWindow.show();

        return newWindow;
    }
}
