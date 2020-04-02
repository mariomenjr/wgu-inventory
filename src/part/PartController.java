package part;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.RowConstraints;
import models.InHouse;
import models.OutSourced;
import models.Part;

public class PartController {

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
    private TextField PartTextfieldCompanyName;
    @FXML
    private RowConstraints RowCompanyName;

    private ToggleGroup _tG;
    private Part _partRow;

    private boolean _isPartOutSourced = false;
    private boolean _isPartInHouse = false;

    private void _init() {
        this._tG = new ToggleGroup();
        this.PartInHouseRadio.setToggleGroup(_tG);
        this.PartOutsourcedRadio.setToggleGroup(_tG);

        this._tG.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if ((Toggle) newValue == (Toggle) this.PartInHouseRadio) {
                _reactToggleGroup("models.InHouse");
            } else if ((Toggle) newValue == (Toggle) this.PartOutsourcedRadio) {
                _reactToggleGroup("models.OutSourced");
            } else {
            }
            System.out.println("x");
        });
    }

    private void _reactToggleGroup(String className) {
        switch (className) {
            case "models.OutSourced":
                this._tG.selectToggle(this.PartOutsourcedRadio);
                this._isPartOutSourced = true;
                this.PartTextfieldCompanyName.setVisible(true);
                break;

            case "models.InHouse":
                this._tG.selectToggle(this.PartInHouseRadio);
                this._isPartInHouse = true;
                this.PartTextfieldCompanyName.setVisible(false);
                break;

            default:
                break;
        }
    }

    private void _populateForm() {
        this.PartTextfieldId.textProperty().set(new Integer(this._partRow.getId()).toString());
        this.PartTextfieldName.textProperty().set(this._partRow.getName());
        this.PartTextfieldInv.textProperty().set(new Integer(this._partRow.getStock()).toString());
        this.PartTextfieldPrice.textProperty().set(this._partRow.getPrice().toString());
        this.PartTextfieldMax.textProperty().set(new Integer(this._partRow.getMax()).toString());
        this.PartTextfieldMin.textProperty().set(new Integer(this._partRow.getMin()).toString());

        // this.PartTextfieldCompanyName.textProperty().set(this._partRow.get().toString());
    }

    public void setPartRow(Part parRow) {
        this._partRow = parRow;
        this._init();

        this._reactToggleGroup(_partRow.getClass().getName());
        this._populateForm();
    }

    public void onTextfieldKeyTyped(KeyEvent event) {
        try {
            System.out.println(event.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

}