package view;

import controller.item.HistoryItemController;
import javafx.scene.control.ListCell;
import model.entities.HistoryEntry;
import utils.DI;

public class HistoryCell extends ListCell<HistoryEntry> {

    private FXMLTuple fxmlTuple;
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
            fxmlTuple = inflater.inflate();
            setGraphic(fxmlTuple.getRoot());
            prefWidth(USE_PREF_SIZE);
            ((HistoryItemController)fxmlTuple.getController()).setContent(item);
        }
    }
}
