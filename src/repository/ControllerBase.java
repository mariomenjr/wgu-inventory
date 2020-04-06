package repository;

import java.util.regex.Pattern;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public abstract class ControllerBase<T> {

    protected Stage _windowInstance;
    protected ToggleGroup _tG;
    protected TableView<T> _tableView;
    protected T _dataRow;

    protected boolean _isAdd = true;

    protected boolean checkIfControlKey(KeyEvent event) {
        KeyCode key = event.getCode();

        return key == KeyCode.BACK_SPACE || key == KeyCode.DELETE || key == KeyCode.LEFT || key == KeyCode.RIGHT
                || key == KeyCode.TAB || event.isControlDown();
    }

    public void onTextfieldKeyTypedIsValidWhenInteger(KeyEvent event) {
        try {
            Boolean isNotValid = !Pattern.compile("[0-9]").matcher(event.getText()).matches();

            if (isNotValid && !this.checkIfControlKey(event))
                throw new Error(); // preventDefault-ish
        } catch (Exception e) {
        }
    }

    public void onTextfieldKeyTypedIsValidWhenPrice(KeyEvent event) {
        try {
            KeyCode key = event.getCode();
            String text = ((TextField) event.getSource()).getText();
            Boolean isNotValid = !Pattern.compile("[0-9]").matcher(event.getText()).matches();

            if (isNotValid && !this.checkIfControlKey(event)) {
                if (text.contains(".") || key != KeyCode.DECIMAL)
                    throw new Error(); // preventDefault-ish
            }
        } catch (Exception e) {
        }
    }

    public void onCancel() {
        this._windowInstance.close();
    }

    public void setWindowsInstance(Stage windowInstance) {
        this._windowInstance = windowInstance;
    }

    public void setTableView(TableView<T> tableView) {
        this._tableView = tableView;
    }
}