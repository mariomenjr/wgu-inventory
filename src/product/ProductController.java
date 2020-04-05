package product;

import javafx.scene.control.ToggleGroup;
import models.Product;

public class ProductController {

    private ToggleGroup _tG;
    private Product _productRow;

    public void outConsole() {
        System.out.println("ProductController");
    }

    private void _init() {
    }

    private void _populateForm() {

    }

    public void setProductRow(Product productRow) {
        this._productRow = productRow;
        // this._init();

        // this._reactToggleGroup(_productRow.getClass().getName());
        this._populateForm();
    }
}
