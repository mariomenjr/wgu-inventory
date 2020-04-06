package part;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import models.Part;
import repository.ModalBase;

public class PartModal extends ModalBase<Part> {

    PartController screenController;

    public PartModal(Part rowData, TableView<Part> tableView) {
        super();
        this.rowData = rowData;
        this.tableView = tableView;
    }

    @Override
    public void setRowController(FXMLLoader loader) {
        this.screenController = loader.getController();

        this.screenController.setWindowsInstance(this.windowInstance);
        this.screenController.setPartRow(this.rowData);
        this.screenController.setTableView(this.tableView);
    }
}
