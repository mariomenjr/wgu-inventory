package product;

import javafx.fxml.FXMLLoader;
import models.Product;
import repository.ModalBase;

public class ProductModal extends ModalBase {

    Product rowData;
    ProductController screenController;

    public ProductModal(Product rowData) {
        super();
        this.rowData = rowData;
    }

    @Override
    public void setRowController(FXMLLoader loader) {
        this.screenController = loader.getController();
        this.screenController.setProductRow(this.rowData);
    }
}