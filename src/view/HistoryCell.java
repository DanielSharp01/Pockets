package view;

import javafx.scene.control.ListCell;
import model.HistoryEntry;
import utils.DI;
import utils.FXMLInflater;

public class HistoryCell extends ListCell<HistoryEntry> {

    private static FXMLInflater inflater;

    static
    {
        inflater = DI.layouts.getFXMLInflater("history-item.fxml");
    }

    @Override
    protected void updateItem(HistoryEntry item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null)
        {
            setGraphic(null);
        }
        else
        {
            setGraphic(inflater.inflate());
            prefWidth(USE_PREF_SIZE);
        }
    }
}
