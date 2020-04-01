package repository;

import java.net.URL;

import home.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class ModalBase {

    public Stage openScreen(String path) throws Exception {
        Stage primaryStage = Main.getPrimeryStage();

        // Scene secondScene = new Scene(secondaryLayout, 230, 100);
        URL urlResource = this.getClass().getResource(path);
        FXMLLoader loader = new FXMLLoader(urlResource);
        final Parent template = loader.load();

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
        this.setRowController(loader);

        return newWindow;
    }

    public void setRowController(FXMLLoader loader) {
        System.out.println("You need to override this method in your implementation!");
    }
}