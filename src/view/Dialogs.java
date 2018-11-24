package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.util.Optional;

/**
 * Contains static methods to construct Alert dialogs (also known as MessageBox)
 */
public class Dialogs {
    /**
     * Shows a warning dialog with a yes, no button
     * @param title Title of the alert dialog
     * @param message Message of the alert dialog
     * @return Optionally which button was pressed if it was pressed
     */
    public static Optional<ButtonType> showWarningYesNo(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING, message,
                new ButtonType("Yes", ButtonBar.ButtonData.YES),
                new ButtonType("No", ButtonBar.ButtonData.NO));
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(null);
        alert.setTitle(title);
        return alert.showAndWait();
    }

    /**
     * Shows an error dialog with an OK button
     * @param title Title of the alert dialog
     * @param message Message of the alert dialog
     */
    public static void showErrorOk(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE));
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.showAndWait();
    }

    /**
     * Shows a warning dialog with an OK button
     * @param title Title of the alert dialog
     * @param message Message of the alert dialog
     */
    public static void showWarningOk(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message, new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE));
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
