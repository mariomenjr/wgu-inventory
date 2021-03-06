package home;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import models.InHouse;
import models.Part;
import models.Product;
import part.PartModal;
import product.ProductModal;

public class MainController {

    public static final String PRODUCT_TYPE = "PRODUCT_TYPE";
    public static final String PART_TYPE = "PART_TYPE";

    // Helpers to achieve filtering
    private FilteredList<Part> filteredPartData;
    private FilteredList<Product> filteredProductData;

    // Instances of controls in view
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TextField SearchProductBox;
    @FXML
    private TextField SearchPartBox;

    /**
     * Method executed when FXML is initialize
     */
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

    /**
     * Handler for add or modify button
     * 
     * @param event
     */
    public void onRowActionClicked(ActionEvent event) {
        try {
            Button eT = (Button) event.getSource(); // Button dispatching event
            String botonId = eT.idProperty().getValue();
            Boolean isAdd = eT.getText().equalsIgnoreCase("Add");

            switch (botonId) {
                case "PartModifyButton":
                case "PartAddButton":
                    if (isAdd) {
                        // Since this is a Add action, we send a brand new instance
                        new PartModal((Part) new InHouse(0, "", 0.0, 0, 0, 0, 0), this.partTableView)
                                .openScreen("../part/PartForm.fxml").setTitle("Add Part");
                    } else {
                        int i = this.partTableView.getSelectionModel().getSelectedIndex();
                        if (i < 0) {
                            // If not row have been selected, we cannot modify
                            Main.showMessageBox("There's a problem", "No row has been selected to get modified.")
                                    .setAlertType(AlertType.ERROR);
                            return;
                        }

                        Part currentPart = this.partTableView.getItems().get(i);
                        switch (currentPart.getClass().getName()) {
                            case "models.OutSourced":
                                // Part modal is a middleman that allow us to inject dependencies into the
                                // controller instance
                                new PartModal(currentPart, this.partTableView).openScreen("../part/PartForm.fxml")
                                        .setTitle("Modify Part");
                                break;

                            case "models.InHouse":
                                new PartModal(currentPart, this.partTableView).openScreen("../part/PartForm.fxml")
                                        .setTitle("Modify Part");
                                break;

                            default:
                                break;
                        }
                    }
                    break;

                case "ProductModifyButton":
                case "ProductAddButton":
                    int j = this.productTableView.getSelectionModel().getSelectedIndex();
                    ProductModal productModal = new ProductModal(
                            isAdd ? new Product(0, "", 0.0, 0, 0, 0) : this.productTableView.getItems().get(j),
                            this.productTableView);
                    productModal.openScreen("../product/ProductForm.fxml")
                            .setTitle((isAdd ? "Add" : "Modify") + " " + "Product");

                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Handler for search buttons
     * 
     * @param event
     */
    public void onSearchClick(ActionEvent event) {
        try {
            Button bt = (Button) event.getSource();

            switch (bt.idProperty().getValue()) {
                case "PartButtonSearch":
                    String str1 = this.SearchPartBox.getText();
                    this.filteredPartData.setPredicate(row -> {
                        if (str1 == null || str1.isEmpty())
                            return true;

                        return row.getName().toLowerCase().contains(str1.toLowerCase());
                    });

                    this.partTableView.refresh(); // Needed to reflect the predicate result
                    break;

                case "ProductButtonSearch":
                    String str2 = this.SearchProductBox.getText();
                    this.filteredProductData.setPredicate(row -> {
                        if (str2 == null || str2.isEmpty())
                            return true;
                        return row.getName().toLowerCase().contains(str2.toLowerCase());
                    });

                    this.productTableView.refresh();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Handler for delete buttons
     * 
     * @param event
     */
    public void onRowDeleteClicked(ActionEvent event) {
        try {
            Alert messageBox = Main.showMessageBox("Do you really want to remove this record?", "");
            messageBox.alertTypeProperty().set(AlertType.CONFIRMATION);

            switch (((Button) event.getSource()).idProperty().getValue()) {
                case "PartDeleteButton":

                    int i = this.partTableView.getSelectionModel().getSelectedIndex();
                    if (i < 0) {
                        messageBox.alertTypeProperty().set(AlertType.ERROR);
                        messageBox.setHeaderText("No Part selected!");
                    } else
                        messageBox.resultProperty().addListener((observable, oldValue, newValue) -> {
                            if (newValue.getButtonData().isCancelButton())
                                return;
                            Main.inventory.getAllParts().remove(this.partTableView.getItems().get(i));
                        });
                    break;
                case "ProductDeleteButton":
                    int j = this.productTableView.getSelectionModel().getSelectedIndex();

                    if (j < 0) {
                        messageBox.alertTypeProperty().set(AlertType.ERROR);
                        messageBox.setHeaderText("No Product selected!");
                    } else
                        messageBox.resultProperty().addListener((observable, oldValue, newValue) -> {
                            if (newValue.getButtonData().isCancelButton())
                                return;
                            Main.inventory.getAllProducts().remove(this.productTableView.getItems().get(j));
                        });
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

    /**
     * Binds ObservableList from inventory to each TableView
     * 
     */
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

    /**
     * Draws columns in TableViews
     * 
     * @param table
     */
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
