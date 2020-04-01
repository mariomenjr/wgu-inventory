package part;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import models.Part;

public class PartController {

    @FXML
    private TextField PartTextfieldId;

    private Part partRow;

    public PartController() {
        super();
    }

    public void setPartRow(Part parRow) {
        this.partRow = parRow;
        System.out.println("Set");

        this.PartTextfieldId.textProperty().set(new Integer(this.partRow.getId()).toString());
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