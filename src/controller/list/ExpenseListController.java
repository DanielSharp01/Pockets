package controller.list;

import controller.edit.EditController;
import controller.edit.EditDialogStage;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.entities.ExpenseItem;
import model.entities.HistoryEntry;
import model.entities.Item;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.DI;
import view.*;

/**
 * List controller for income sources
 */
public class ExpenseListController extends EntityListController<ExpenseItem> {

    private static final FXMLInflater editDialogInflater = DI.layouts.getFXMLInflater("item-edit.fxml");
    /**
     * Filtered list of expense items
     */
    private FilteredList<ExpenseItem> filteredItems;

    public ExpenseListController() {
        filteredItems = DI.getRepositories().expenses.asFilteredList();
    }

    @Override
    public FilteredList<ExpenseItem> getFilteredList() {
        return filteredItems;
    }

    @Override
    public Callback<ListView, ListCell<HistoryEntry>> getCellFactory() {
        throw new NotImplementedException();
    }

    @Override
    public Callback<TileListView, ViewHolder<ExpenseItem>> getHolderFactory() {
        return l -> (ViewHolder)(new ItemHolder());
    }

    @Override
    public void newEntity() {
        FXMLTuple tuple = editDialogInflater.inflate();
        EditDialogStage<Item> editDialog = new EditDialogStage<>(tuple.getRoot(), 380, 600, (EditController<Item>) tuple.getController());
        ExpenseItem item = new ExpenseItem(0);
        item.setName(currentFilter);
        editDialog.showAndWaitForSubmit(item);
    }
}