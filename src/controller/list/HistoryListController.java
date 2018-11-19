package controller.list;

import controller.edit.EditController;
import controller.edit.EditDialogStage;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.entities.HistoryEntry;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.DI;
import view.*;

import java.time.LocalDateTime;

/**
 * List controller for the history list
 */
public class HistoryListController extends EntityListController<HistoryEntry> {

    private static final FXMLInflater editDialogInflater = DI.layouts.getFXMLInflater("history-edit.fxml");
    /**
     * Filtered list of history entries
     */
    private FilteredList<HistoryEntry> filteredHistory;

    public HistoryListController() {
        filteredHistory = DI.getRepositories().history.asFilteredList();
    }

    @Override
    public FilteredList<HistoryEntry> getFilteredList() {
        return filteredHistory;
    }

    @Override
    public Callback<ListView, ListCell<HistoryEntry>> getCellFactory() {
        return l -> new HistoryCell(this);
    }

    @Override
    public Callback<TileListView, ViewHolder<HistoryEntry>> getHolderFactory() {
        throw new NotImplementedException();
    }

    @Override
    public void newEntity() {
        FXMLTuple tuple = editDialogInflater.inflate();
        EditDialogStage<HistoryEntry> editDialog = new EditDialogStage<>(tuple.getRoot(), 380, 500, (EditController<HistoryEntry>) tuple.getController());
        editDialog.setTitle("Add history entry");
        HistoryEntry historyEntry = new HistoryEntry(0, 0, HistoryEntry.Type.Expense, LocalDateTime.now());
        editDialog.showAndWaitForSubmit(historyEntry);
    }

    @Override
    public void edit(HistoryEntry model) {
        FXMLTuple tuple = editDialogInflater.inflate();
        EditDialogStage<HistoryEntry> editDialog = new EditDialogStage<>(tuple.getRoot(), 380, 500, (EditController<HistoryEntry>) tuple.getController());
        editDialog.setTitle("Edit history entry");
        editDialog.showAndWaitForSubmit(model);
    }

    @Override
    public void delete(HistoryEntry model) {
        DI.getRepositories().history.delete(model);
    }
}
