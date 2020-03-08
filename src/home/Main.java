package home;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage homeStage;

    @Override
    public void start(final Stage primaryStage) throws Exception{
        final Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));

        homeStage = primaryStage;

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static Stage getPrimeryStage() {
        return homeStage;
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
