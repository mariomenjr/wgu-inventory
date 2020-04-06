package product;

import home.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Part;
import models.Product;
import repository.ControllerBase;

public class ProductController extends ControllerBase<Product> {

    @FXML
    private TableView<Part> ProdTableViewPartToAdd;
    @FXML
    private TableView<Part> ProdTableViewPartToDelete;
    @FXML
    private TextField ProdTextfieldId;
    @FXML
    private TextField ProdTextfieldName;
    @FXML
    private TextField ProdTextfieldInv;
    @FXML
    private TextField ProdTextfieldPrice;
    @FXML
    private TextField ProdTextfieldMax;
    @FXML
    private TextField ProdTextfieldMin;
    @FXML
    private TextField PartTextfieldSearch;

    @FXML
    private Label ProductFormLabel;

    private FilteredList<Part> filteredAddPartData;

    private ObservableList<Part> observableDelPartData;
    private FilteredList<Part> filteredDelPartData;

    private void _init() {
        this._isAdd = this._dataRow.getId() == 0;
        this.ProductFormLabel.setText(this._isAdd ? "Add Product" : "Modify Product");
    }

    private void _populateForm() {
        this.ProdTextfieldId.textProperty()
                .set(this._isAdd ? "Auto Gen - Disabled" : new Integer(this._dataRow.getId()).toString());
        this.ProdTextfieldName.textProperty().set(this._dataRow.getName());
        this.ProdTextfieldInv.textProperty().set(new Integer(this._dataRow.getStock()).toString());
        this.ProdTextfieldPrice.textProperty().set(this._dataRow.getPrice().toString());
        this.ProdTextfieldMax.textProperty().set(new Integer(this._dataRow.getMax()).toString());
        this.ProdTextfieldMin.textProperty().set(new Integer(this._dataRow.getMin()).toString());

        this._renderTable();
        this._renderData();
    }

    private void _renderData() {
        try {
            // Temporary list that helps us to not modify original list when the user have
            // not click on Save button yet
            this.observableDelPartData = FXCollections.observableArrayList();
            this._dataRow.getAllAssociatedParts().forEach(part -> {
                String className = part.getClass().getName();
                switch (className) {
                    case "models.OutSourced":
                        this.observableDelPartData.add(part);

                        break;
                    case "models.InHouse":
                        this.observableDelPartData.add(part);
                        break;

                    default:
                        break;
                }
            });

            this.filteredDelPartData = new FilteredList<Part>(this.observableDelPartData, p -> true);
            this.ProdTableViewPartToDelete.setItems(this.filteredDelPartData);

            this.filteredAddPartData = new FilteredList<Part>(Main.inventory.getAllParts(),
                    p -> !this._dataRow.getAllAssociatedParts().contains(p) && !this.observableDelPartData.contains(p));
            this.ProdTableViewPartToAdd.setItems(this.filteredAddPartData);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private void _renderTable() {
        TableColumn<Part, Integer> idAddPartColumn = new TableColumn<Part, Integer>("Id");
        idAddPartColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.ProdTableViewPartToAdd.getColumns().add(idAddPartColumn);

        TableColumn<Part, Integer> idDelPartColumn = new TableColumn<Part, Integer>("Id");
        idDelPartColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.ProdTableViewPartToDelete.getColumns().add(idDelPartColumn);

        TableColumn<Part, String> nameAddPartColumn = new TableColumn<Part, String>("Name");
        nameAddPartColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.ProdTableViewPartToAdd.getColumns().add(nameAddPartColumn);

        TableColumn<Part, String> nameDelPartColumn = new TableColumn<Part, String>("Name");
        nameDelPartColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.ProdTableViewPartToDelete.getColumns().add(nameDelPartColumn);

        TableColumn<Part, Double> priceAddPartColumn = new TableColumn<Part, Double>("Price");
        priceAddPartColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.ProdTableViewPartToAdd.getColumns().add(priceAddPartColumn);

        TableColumn<Part, Double> priceDelPartColumn = new TableColumn<Part, Double>("Price");
        priceDelPartColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.ProdTableViewPartToDelete.getColumns().add(priceDelPartColumn);

        TableColumn<Part, Integer> stockAddPartColumn = new TableColumn<Part, Integer>("Stock");
        stockAddPartColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.ProdTableViewPartToAdd.getColumns().add(stockAddPartColumn);

        TableColumn<Part, Integer> stockDelPartColumn = new TableColumn<Part, Integer>("Stock");
        stockDelPartColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.ProdTableViewPartToDelete.getColumns().add(stockDelPartColumn);

        TableColumn<Part, Integer> minAddPartColumn = new TableColumn<Part, Integer>("Min");
        minAddPartColumn.setCellValueFactory(new PropertyValueFactory<>("min"));
        this.ProdTableViewPartToAdd.getColumns().add(minAddPartColumn);

        TableColumn<Part, Integer> minDelPartColumn = new TableColumn<Part, Integer>("Min");
        minDelPartColumn.setCellValueFactory(new PropertyValueFactory<>("min"));
        this.ProdTableViewPartToDelete.getColumns().add(minDelPartColumn);

        TableColumn<Part, Integer> maxAddPartColumn = new TableColumn<Part, Integer>("Max");
        maxAddPartColumn.setCellValueFactory(new PropertyValueFactory<>("max"));
        this.ProdTableViewPartToAdd.getColumns().add(maxAddPartColumn);

        TableColumn<Part, Integer> maxDelPartColumn = new TableColumn<Part, Integer>("Max");
        maxDelPartColumn.setCellValueFactory(new PropertyValueFactory<>("max"));
        this.ProdTableViewPartToDelete.getColumns().add(maxDelPartColumn);
    }

    private void _refreshTables() {
        this.filteredAddPartData.setPredicate(p -> !this.observableDelPartData.contains(p));
        this.ProdTableViewPartToAdd.refresh();
        this.ProdTableViewPartToDelete.refresh();
    }

    private Boolean _validateBeforeSaving() {
        try {
            int max = Integer.parseInt(this.ProdTextfieldMax.getText());
            int min = Integer.parseInt(this.ProdTextfieldMin.getText());

            if (max < min)
                throw new Exception("Max value must be greater or equal than Min value.");

            int parts = this.observableDelPartData.size();
            if (parts <= 0)
                throw new Exception("Product must be assigned Parts.");

            int inv = Integer.parseInt(this.ProdTextfieldInv.getText());
            if (inv > max)
                throw new Exception("Inventory surpasses Max value.");

            return true;
        } catch (Exception e) {
            Main.showMessageBox("No valid values!", e.getMessage()).setAlertType(AlertType.ERROR);
            return false;
        }
    }

    public void setProductRow(Product productRow) {
        this._dataRow = productRow;
        this._init();

        this._populateForm();
    }

    public void onSearchAddPart(ActionEvent event) {
        try {
            String strText = this.PartTextfieldSearch.textProperty().getValue();
            this.filteredAddPartData.setPredicate(row -> {
                if (strText == null || strText.isEmpty())
                    return true;
                return row.getName().toLowerCase().contains(strText.toLowerCase());
            });
            this.ProdTableViewPartToAdd.refresh();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onAddPart(ActionEvent event) {
        try {
            Part selected = this.ProdTableViewPartToAdd.getSelectionModel().getSelectedItem();
            this.observableDelPartData.add(selected);

            this._refreshTables();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onDeletePart(ActionEvent event) {
        try {
            Alert messageBox = Main.showMessageBox("Do you really want to remove this record?", "");
            messageBox.alertTypeProperty().set(AlertType.CONFIRMATION);

            int i = this.ProdTableViewPartToDelete.getSelectionModel().getSelectedIndex();
            if (i < 0) {
                messageBox.alertTypeProperty().set(AlertType.ERROR);
                messageBox.setHeaderText("No Part selected!");
            } else
                messageBox.resultProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.getButtonData().isCancelButton())
                        return;

                    this.observableDelPartData.remove(this.ProdTableViewPartToDelete.getItems().get(i));
                    this._refreshTables();
                });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onSave(ActionEvent event) {
        try {
            if (!this._validateBeforeSaving())
                return;

            String prodMessage = this.ProdTextfieldId.getText();

            if (this._isAdd) {
                ObservableList<Product> inventoryProducts = Main.inventory.getAllProducts();
                this._dataRow.setId(inventoryProducts.get(inventoryProducts.size() - 1).getId() + 1);
                prodMessage = Integer.toString(this._dataRow.getId());
            } else
                this._dataRow.setId(Integer.parseInt(this.ProdTextfieldId.getText()));

            this._dataRow.setName(this.ProdTextfieldName.getText());
            this._dataRow.setStock(Integer.parseInt(this.ProdTextfieldInv.getText()));
            this._dataRow.setPrice(Double.parseDouble(this.ProdTextfieldPrice.getText()));
            this._dataRow.setMax(Integer.parseInt(this.ProdTextfieldMax.getText()));
            this._dataRow.setMin(Integer.parseInt(this.ProdTextfieldMin.getText()));

            this._dataRow.getAllAssociatedParts().clear();
            this._dataRow.getAllAssociatedParts().addAll(this.filteredDelPartData);

            if (this._isAdd)
                Main.inventory.addProduct(this._dataRow);
            else {
                int i = Main.inventory.getAllProducts().indexOf(this._dataRow);
                Main.inventory.getAllProducts().set(i, this._dataRow);
            }

            this._tableView.refresh();
            this.onCancel();

            Main.showMessageBox("Product " + prodMessage, "The product has been updated successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
