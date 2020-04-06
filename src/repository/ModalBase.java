package repository;

import java.net.URL;

import home.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class ModalBase<T> {

    public Stage windowInstance;

    protected T rowData;
    protected TableView<T> tableView;

    public Stage openScreen(String path) throws Exception {
        Stage primaryStage = Main.getPrimeryStage();

        // Scene secondScene = new Scene(secondaryLayout, 230, 100);
        URL urlResource = this.getClass().getResource(path);
        FXMLLoader loader = new FXMLLoader(urlResource);
        final Parent template = loader.load();

        // New window (Stage)
        this.windowInstance = new Stage();
        this.windowInstance.setTitle(path);
        this.windowInstance.setScene(new Scene(template, 600, 400));

        // Specifies the modality for new window.
        this.windowInstance.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        this.windowInstance.initOwner(primaryStage);

        // Set position of second window, related to primary window.
        this.windowInstance.setX(primaryStage.getX() + 200);
        this.windowInstance.setY(primaryStage.getY() + 100);
        this.windowInstance.setResizable(false);

        this.windowInstance.show();
        this.setRowController(loader);

        return this.windowInstance;
    }

    public void setRowController(FXMLLoader loader) {
        System.out.println("You need to override this method in your implementation!");
    }
}