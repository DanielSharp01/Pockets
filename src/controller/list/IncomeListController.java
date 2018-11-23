package controller.list;

import controller.edit.EditController;
import controller.edit.EditDialogStage;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.entities.HistoryEntry;
import model.entities.IncomeSource;
import model.entities.Item;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.DI;
import view.*;

/**
 * List controller for income sources
 */
public class IncomeListController extends EntityListController<IncomeSource> {

    private static final FXMLInflater editDialogInflater = DI.layouts.getFXMLInflater("item-edit.fxml");
    /**
     * Filtered list of income sources
     */
    private FilteredList<IncomeSource> filteredItems;

    public IncomeListController() {
        filteredItems = DI.getRepositories().incomes.asFilteredList();
    }

    @Override
    public FilteredList<IncomeSource> getFilteredList() {
        return filteredItems;
    }

    @Override
    public Callback<ListView, ListCell<HistoryEntry>> getCellFactory() {
        throw new NotImplementedException();
    }

    @Override
    public Callback<TileListView, ViewHolder<IncomeSource>> getHolderFactory() {
        return l -> (ViewHolder)(new ItemHolder((EntityListController) this));
    }

    @Override
    public void newEntity() {
        FXMLTuple tuple = editDialogInflater.inflate();
        EditDialogStage<Item> editDialog = new EditDialogStage<>(tuple.getRoot(), 380, 600, (EditController<Item>) tuple.getController());
        editDialog.setTitle("Add income source");
        IncomeSource item = new IncomeSource(0);
        item.setName(currentFilter);
        editDialog.showAndWaitForSubmit(item);
    }

    @Override
    public void edit(IncomeSource model) {
        FXMLTuple tuple = editDialogInflater.inflate();
        EditDialogStage<Item> editDialog = new EditDialogStage<>(tuple.getRoot(), 380, 600, (EditController<Item>) tuple.getController());
        editDialog.setTitle("Edit income source");
        editDialog.showAndWaitForSubmit(model);
    }

    @Override
    public void delete(IncomeSource model) {
        if (DI.getRepositories().incomes.isUsedInHistory(model))
        {
            model.setDisabled(true);
            DI.getRepositories().incomes.update(model);
        }
    }
}
