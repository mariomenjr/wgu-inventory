package home;

import java.net.URI;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import models.Part;
import models.Product;
import utils.Modal;

public class Controller {

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableView<Part> partTableView;

    // @Override
    // public void initialize(URI location, ResourceBundle resources) {

    // }

    public void exitApplication() {
        System.exit(0);
    }

    public void openAddPartForm() {
        try {
            Stage st = Modal.openScreen("../part/Form.fxml");
            System.out.println(st.getTitle());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void openUpdatePartForm() {
        try {
            Stage st = Modal.openScreen("../part/Form.fxml");
            System.out.println(st.getTitle());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void openAddProductForm() {
        try {
            Stage st = Modal.openScreen("../product/Form.fxml");
            System.out.println(st.getTitle());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void openUpdateProductForm() {
        try {
            Stage st = Modal.openScreen("../product/Form.fxml");
            System.out.println(st.getTitle());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
