package part;

import javafx.fxml.FXMLLoader;
import models.Part;
import repository.ModalBase;

public class PartModal extends ModalBase {

    Part rowData;
    PartController screenController;

    public PartModal(Part rowData) {
        super();
        this.rowData = rowData;
    }

    @Override
    public void setRowController(FXMLLoader loader) {
        screenController = loader.getController();
        screenController.setPartRow(this.rowData);
    }
}
