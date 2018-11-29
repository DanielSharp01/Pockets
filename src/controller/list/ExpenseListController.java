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
        return l -> (ViewHolder)(new ItemHolder((EntityListController) this));
    }

    @Override
    public void newEntity() {
        FXMLTuple tuple = editDialogInflater.inflate();
        EditDialogStage<Item> editDialog = new EditDialogStage<>(tuple.getRoot(), 380, 600, (EditController<Item>) tuple.getController());
        editDialog.setTitle("Add expense item");
        ExpenseItem item = new ExpenseItem(0);
        item.setName(currentFilter);
        editDialog.showAndWaitForSubmit(item);
    }

    @Override
    public void edit(ExpenseItem model) {
        FXMLTuple tuple = editDialogInflater.inflate();
        EditDialogStage<Item> editDialog = new EditDialogStage<>(tuple.getRoot(), 380, 600, (EditController<Item>) tuple.getController());
        editDialog.setTitle("Edit expense item");
        editDialog.showAndWaitForSubmit(model);
    }

    @Override
    public void delete(ExpenseItem model) {
        if (DI.getRepositories().expenses.isUsedInHistory(model))
        {
            model.setDisabled(true);
            DI.getRepositories().expenses.update(model);
        }
        else
        {
            DI.getRepositories().expenses.delete(model);
        }
    }
}
