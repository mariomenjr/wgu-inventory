package home;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.Part;
import models.Product;
import part.PartController;
import part.PartModal;

public class MainController {

    public static final String PRODUCT_TYPE = "PRODUCT_TYPE";
    public static final String PART_TYPE = "PART_TYPE";

    private FilteredList<Part> filteredPartData;
    private FilteredList<Product> filteredProductData;

    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TextField searchProductBox;
    @FXML
    private TextField searchPartBox;

    @FXML
    public void initialize() {
        try {
            System.out.println("\nApplication Mounted");

            this._renderTable(PRODUCT_TYPE);
            this._renderTable(PART_TYPE);

            this._renderData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void openAddPartForm() {
        try {
            // Stage st = Modal.openScreen("../part/Form.fxml");
            // System.out.println(st.getTitle());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void openAddProductForm() {
        try {
            // Stage st = Modal.openScreen("../product/Form.fxml");
            // System.out.println(st.getTitle());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void onRowModifyClicked(ActionEvent event) {
        try {

            Button eT = (Button) event.getSource();
            switch (eT.idProperty().getValue()) {
                case "PartModifyButton":
                    int i = this.partTableView.getSelectionModel().getSelectedIndex();
                    PartModal partModal = new PartModal(this.partTableView.getItems().get(i));
                    partModal.openScreen("../part/PartForm.fxml");

                    break;

                case "ProductModifyButton":
                    // Stage productForm = Modal.openScreen("../product/ProductForm.fxml");
                    break;

                default:
                    break;
            }

            // System.out.println(st.getTitle());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    // public void openUpdateProductForm() {
    // try {
    // Stage st = Modal.openScreen("../product/Form.fxml");
    // System.out.println(st.getTitle());
    // } catch (Exception e) {
    // System.out.println(e.getMessage());
    // System.exit(1);
    // }
    // }

    public void onSearchTextChanged(KeyEvent event) {
        try {
            TextField tF = (TextField) event.getSource();
            String strText = tF.textProperty().getValue();
            switch (tF.idProperty().getValue()) {
                case "SearchPartBox":
                    this.filteredPartData.setPredicate(row -> {
                        if (strText == null || strText.isEmpty())
                            return true;
                        return row.getName().toLowerCase().contains(strText.toLowerCase());
                    });
                    this.filteredPartData.clear();
                    break;

                case "SearchProductBox":
                    this.filteredProductData.setPredicate(row -> {
                        if (strText == null || strText.isEmpty())
                            return true;
                        return row.getName().toLowerCase().contains(strText.toLowerCase());
                    });
                    this.filteredProductData.clear();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void onRowDeleteClicked(ActionEvent event) {
        try {
            Button eT = (Button) event.getSource();

            switch (eT.idProperty().getValue()) {
                case "PartDeleteButton":
                    int i = this.partTableView.getSelectionModel().getSelectedIndex();
                    if (i >= 0)
                        Main.inventory.getAllParts().remove(this.partTableView.getItems().get(i));
                    break;

                case "ProductDeleteButton":
                    int j = this.productTableView.getSelectionModel().getSelectedIndex();
                    if (j >= 0)
                        Main.inventory.getAllProducts().remove(this.productTableView.getItems().get(j));
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void exitApplication() {
        System.exit(0);
    }

    private void _renderData() {
        try {
            this.filteredPartData = new FilteredList<Part>(Main.inventory.getAllParts(), p -> true);
            this.partTableView.setItems(this.filteredPartData);

            this.filteredProductData = new FilteredList<Product>(Main.inventory.getAllProducts(), p -> true);
            this.productTableView.setItems(this.filteredProductData);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private void _renderTable(String table) {
        try {
            switch (table) {
                case PRODUCT_TYPE:
                    TableColumn<Product, Integer> idProdColumn = new TableColumn<Product, Integer>("Id");
                    idProdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                    this.productTableView.getColumns().add(idProdColumn);

                    TableColumn<Product, String> nameProdColumn = new TableColumn<Product, String>("Name");
                    nameProdColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                    this.productTableView.getColumns().add(nameProdColumn);

                    TableColumn<Product, Double> priceProdColumn = new TableColumn<Product, Double>("Price");
                    priceProdColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                    this.productTableView.getColumns().add(priceProdColumn);

                    TableColumn<Product, Integer> stockProdColumn = new TableColumn<Product, Integer>("Stock");
                    stockProdColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                    this.productTableView.getColumns().add(stockProdColumn);

                    TableColumn<Product, Integer> minProdColumn = new TableColumn<Product, Integer>("Min");
                    minProdColumn.setCellValueFactory(new PropertyValueFactory<>("min"));
                    this.productTableView.getColumns().add(minProdColumn);

                    TableColumn<Product, Integer> maxProdColumn = new TableColumn<Product, Integer>("Max");
                    maxProdColumn.setCellValueFactory(new PropertyValueFactory<>("max"));
                    this.productTableView.getColumns().add(maxProdColumn);

                    break;

                case PART_TYPE:
                    TableColumn<Part, Integer> idPartColumn = new TableColumn<Part, Integer>("Id");
                    idPartColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                    this.partTableView.getColumns().add(idPartColumn);

                    TableColumn<Part, String> namePartColumn = new TableColumn<Part, String>("Name");
                    namePartColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                    this.partTableView.getColumns().add(namePartColumn);

                    TableColumn<Part, Double> pricePartColumn = new TableColumn<Part, Double>("Price");
                    pricePartColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                    this.partTableView.getColumns().add(pricePartColumn);

                    TableColumn<Part, Integer> stockPartColumn = new TableColumn<Part, Integer>("Stock");
                    stockPartColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                    this.partTableView.getColumns().add(stockPartColumn);

                    TableColumn<Part, Integer> minPartColumn = new TableColumn<Part, Integer>("Min");
                    minPartColumn.setCellValueFactory(new PropertyValueFactory<>("min"));
                    this.partTableView.getColumns().add(minPartColumn);

                    TableColumn<Part, Integer> maxPartColumn = new TableColumn<Part, Integer>("Max");
                    maxPartColumn.setCellValueFactory(new PropertyValueFactory<>("max"));
                    this.partTableView.getColumns().add(maxPartColumn);

                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
