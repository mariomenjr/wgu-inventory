package part;

import java.awt.Event;
import java.util.regex.Pattern;

import home.Main;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import models.InHouse;
import models.OutSourced;
import models.Part;
import repository.ControllerBase;

public class PartController extends ControllerBase<Part> {

    @FXML
    private RadioButton PartInHouseRadio;
    @FXML
    private RadioButton PartOutsourcedRadio;
    @FXML
    private TextField PartTextfieldId;
    @FXML
    private TextField PartTextfieldName;
    @FXML
    private TextField PartTextfieldInv;
    @FXML
    private TextField PartTextfieldPrice;
    @FXML
    private TextField PartTextfieldMax;
    @FXML
    private TextField PartTextfieldMin;
    @FXML
    private Label PartLabelCompanyName;
    @FXML
    private TextField PartTextfieldCompanyName;

    private EventHandler<KeyEvent> keyEventHandlerValidateInteger = new EventHandler<KeyEvent>() {
        public void handle(KeyEvent event) {
            onTextfieldKeyTypedIsValidWhenInteger(event);
        }
    };

    private void _init() {
        this._tG = new ToggleGroup();
        this.PartInHouseRadio.setToggleGroup(_tG);
        this.PartOutsourcedRadio.setToggleGroup(_tG);

        this._tG.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if ((Toggle) newValue == (Toggle) this.PartInHouseRadio) {
                _reactToggleGroup("models.InHouse");
            } else if ((Toggle) newValue == (Toggle) this.PartOutsourcedRadio) {
                _reactToggleGroup("models.OutSourced");
            }
        });

        this._isAdd = this._dataRow.getId() == 0;
    }

    private void _reactToggleGroup(String className) {
        switch (className) {
            case "models.OutSourced":
                this._tG.selectToggle(this.PartOutsourcedRadio);
                this.PartLabelCompanyName.setText("Company Name");
                this.PartTextfieldCompanyName.removeEventHandler(KeyEvent.KEY_PRESSED,
                        this.keyEventHandlerValidateInteger);
                break;

            case "models.InHouse":
                this._tG.selectToggle(this.PartInHouseRadio);
                this.PartLabelCompanyName.setText("Machine Id");
                this.PartTextfieldCompanyName.addEventHandler(KeyEvent.KEY_PRESSED,
                        this.keyEventHandlerValidateInteger);
                break;

            default:
                break;
        }
    }

    private void _populateForm(String className) {
        this.PartTextfieldId.textProperty()
                .set(this._isAdd ? "Auto Gen - Disabled" : new Integer(this._dataRow.getId()).toString());
        this.PartTextfieldName.textProperty().set(this._dataRow.getName());
        this.PartTextfieldInv.textProperty().set(new Integer(this._dataRow.getStock()).toString());
        this.PartTextfieldPrice.textProperty().set(this._dataRow.getPrice().toString());
        this.PartTextfieldMax.textProperty().set(new Integer(this._dataRow.getMax()).toString());
        this.PartTextfieldMin.textProperty().set(new Integer(this._dataRow.getMin()).toString());

        switch (className) {
            case "models.OutSourced":
                this.PartTextfieldCompanyName.textProperty()
                        .set(((OutSourced) this._dataRow).getCompanyName().toString());
                this.PartTextfieldCompanyName.removeEventHandler(KeyEvent.KEY_PRESSED,
                        this.keyEventHandlerValidateInteger);
                break;

            case "models.InHouse":
                this.PartTextfieldCompanyName.textProperty()
                        .set(Integer.toString(((InHouse) this._dataRow).getMachineId()));

                this.PartTextfieldCompanyName.addEventHandler(KeyEvent.KEY_PRESSED,
                        this.keyEventHandlerValidateInteger);
                break;

            default:
                break;
        }
    }

    public void setPartRow(Part parRow) {
        this._dataRow = parRow;
        this._init();

        String className = _dataRow.getClass().getName();

        this._reactToggleGroup(className);
        this._populateForm(className);
    }

    public void onSave(ActionEvent event) {
        String className = this._dataRow.getClass().getName();
        Boolean isInHouse = this._tG.getSelectedToggle() == this.PartInHouseRadio;

        System.out.println(className);

        if (this._isAdd) {
            ObservableList<Part> inventoryParts = Main.inventory.getAllParts();
            this._dataRow.setId(inventoryParts.get(inventoryParts.size() - 1).getId() + 1);
        } else
            this._dataRow.setId(Integer.parseInt(this.PartTextfieldId.getText()));

        this._dataRow.setName(this.PartTextfieldName.getText());
        this._dataRow.setStock(Integer.parseInt(this.PartTextfieldInv.getText()));
        this._dataRow.setPrice(Double.parseDouble(this.PartTextfieldPrice.getText()));
        this._dataRow.setMax(Integer.parseInt(this.PartTextfieldMax.getText()));
        this._dataRow.setMin(Integer.parseInt(this.PartTextfieldMin.getText()));

        switch (className) {
            case "models.OutSourced":
                if (isInHouse) {
                    InHouse inHouse = new InHouse(this._dataRow.getId(), this._dataRow.getName(),
                            this._dataRow.getPrice(), this._dataRow.getStock(), this._dataRow.getMin(),
                            this._dataRow.getMax(), 0);
                    inHouse.setMachineId(Integer.parseInt(this.PartTextfieldCompanyName.getText()));

                    if (this._isAdd)
                        Main.inventory.addPart((OutSourced) this._dataRow);
                    else {
                        int i = Main.inventory.getAllParts().indexOf(this._dataRow);
                        Main.inventory.getAllParts().set(i, inHouse);
                    }
                } else {
                    ((OutSourced) this._dataRow).setCompanyName(this.PartTextfieldCompanyName.getText());
                    if (this._isAdd)
                        Main.inventory.addPart((OutSourced) this._dataRow);
                }
                break;

            case "models.InHouse":
                if (isInHouse) {
                    ((InHouse) this._dataRow).setMachineId(Integer.parseInt(this.PartTextfieldCompanyName.getText()));
                    if (this._isAdd)
                        Main.inventory.addPart((InHouse) this._dataRow);
                } else {
                    OutSourced outSourced = new OutSourced(this._dataRow.getId(), this._dataRow.getName(),
                            this._dataRow.getPrice(), this._dataRow.getStock(), this._dataRow.getMin(),
                            this._dataRow.getMax(), "");
                    outSourced.setCompanyName(this.PartTextfieldCompanyName.getText());

                    if (this._isAdd)
                        Main.inventory.addPart((OutSourced) this._dataRow);
                    else {
                        int i = Main.inventory.getAllParts().indexOf(this._dataRow);
                        Main.inventory.getAllParts().set(i, outSourced);
                    }
                }
                break;

            default:
                break;
        }

        this._tableView.refresh();
        this.onCancel();

        Main.showMessageBox("Part " + this.PartTextfieldId.getText(), "The part has been updated successfully!");
    }
}