package view;

import javafx.scene.control.ListCell;
import model.items.HistoryItem;

public class HistoryCell extends ListCell<HistoryItem> {

    private static FXMLInflater inflater;

    static
    {
        inflater = new FXMLInflater(HistoryCell.class.getResource("../res/layouts/history-item.fxml"));
    }

    @Override
    protected void updateItem(HistoryItem item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null)
        {
            setGraphic(null);
        }
        else
        {
            setGraphic(inflater.Inflate());
            prefWidth(USE_PREF_SIZE);
        }
    }
}
