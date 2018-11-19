package controller.list;

import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.entities.Entity;
import model.entities.HistoryEntry;
import model.filters.SatisfyFilter;
import view.TileListView;
import view.ViewHolder;

/**
 * Supplies the list and manages communication with the repository
 * @param <T> Type of the entity
 */
public abstract class EntityListController<T extends Entity> {

    protected String currentFilter = "";

    /**
     * Filtered observable list returned to the list or tile controller
     * @return Filtered observable list
     */
    public abstract FilteredList<T> getFilteredList();

    /**
     * Safe to not implement if you don't need it
     * @return ListCell factory for a JavaFX ListView
     */
    public abstract Callback<ListView, ListCell<HistoryEntry>> getCellFactory();

    /**
     * Safe to not implement if you don't need it
     * @return ViewHolder factory for a TiledListView
     */
    public abstract Callback<TileListView, ViewHolder<T>> getHolderFactory();

    /**
     * Filter TextField was changed on the parent ListController
     * @param newFilter New filter
     */
    public void filterTextChanged(String newFilter)
    {
        SatisfyFilter filter = new SatisfyFilter(newFilter);
        currentFilter = newFilter;
        filter.setFilteredListPredicate(getFilteredList());
    }

    /**
     * Add button was pressed on the parent ListController
     */
    public abstract void newEntity();

    /**
     * Edit request was made by the view for the specified model
     * @param model Model to edit
     */
    public abstract void edit(T model);

    /**
     * Delete request was made by the view for the specified model
     * @param model Model to delete
     */
    public abstract void delete(T model);
}
