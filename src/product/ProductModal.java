package product;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import models.Product;
import repository.ModalBase;

public class ProductModal extends ModalBase<Product> {

    ProductController screenController;

    public ProductModal(Product rowData, TableView<Product> tableView) {
        super();
        this.rowData = rowData;
        this.tableView = tableView;
    }

    @Override
    public void setRowController(FXMLLoader loader) {
        this.screenController = loader.getController();

        this.screenController.setWindowsInstance(this.windowInstance);
        this.screenController.setProductRow(this.rowData);
        this.screenController.setTableView(this.tableView);
    }
}