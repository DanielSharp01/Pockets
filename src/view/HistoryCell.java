package view;

import controller.item.HistoryItemController;
import controller.list.EntityListController;
import javafx.scene.control.ListCell;
import model.entities.HistoryEntry;
import utils.DI;

/**
 * ListCell for HistoryEntry
 */
public class HistoryCell extends ListCell<HistoryEntry> {

    /**
     * FXMLInflater that loads the underlying FXMLTuple
     */
    private static FXMLInflater inflater = DI.layouts.getFXMLInflater("history-item.fxml");

    /**
     * Underlying FXML node and controller
     */
    private FXMLTuple fxmlTuple;

    /**
     * ListController of the parent list
     */
    private EntityListController<HistoryEntry> listController;

    /**
     * @param listController ListController of the parent list
     */
    public HistoryCell(EntityListController<HistoryEntry> listController)
    {
        this.listController = listController;
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
            ((HistoryItemController)fxmlTuple.getController()).setModel(item);
            ((HistoryItemController)fxmlTuple.getController()).setListController(listController);
        }
    }
}
