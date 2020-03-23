package home;

import javafx.stage.Stage;
import utils.Modal;

public class Controller {

    public void exitApplication() {
        System.exit(0);
    }

    public void openAddPartForm()  {
        try {
            Stage st = Modal.openScreen("../part/Form.fxml");
            System.out.println(st.getTitle());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void openUpdatePartForm()  {
        try {
            Stage st = Modal.openScreen("../part/Form.fxml");
            System.out.println(st.getTitle());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void openAddProductForm()  {
        try {
            Stage st = Modal.openScreen("../product/Form.fxml");
            System.out.println(st.getTitle());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public void openUpdateProductForm()  {
        try {
            Stage st = Modal.openScreen("../product/Form.fxml");
            System.out.println(st.getTitle());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
