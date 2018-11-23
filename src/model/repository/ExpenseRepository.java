package model.repository;

import com.google.gson.JsonArray;
import model.entities.ExpenseItem;
import model.entities.HistoryEntry;
import model.filters.SatisfyFilter;
import utils.DI;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository interface for {@link ExpenseItem}
 */
public class ExpenseRepository extends EntityRepository<ExpenseItem> {

    /**
     * @param container Parent repository container, used by repositories accessing other repositories
     */
    public ExpenseRepository(RepositoryContainer container) {
        super(container);
    }

    /**
     * Filters items by a search term
     * @param searchTerm Search term consisting of words that need to be found in the item's name
     * @return Matching list of items
     */
    public List<ExpenseItem> filterBySearch(String searchTerm)
    {
        SatisfyFilter filter = new SatisfyFilter(searchTerm);
        return (List<ExpenseItem>) filter.filter(entities.values());
    }

    /**
     * Gets all items with a given tag
     * @param tagId Tag's id to get tagged items of
     * @return Matching list of items
     */
    public List<ExpenseItem> withTag(int tagId)
    {
        List<ExpenseItem> list = new ArrayList<>();
        for (ExpenseItem item : entities.values())
        {
            if (item.getTagIds().contains(tagId))
                list.add(item);
        }

        return list;
    }

    /**
     * Determines whether this item is used in any history entry
     * @return True if used, false otherwise
     */
    public boolean isUsedInHistory(ExpenseItem item)
    {
        for (HistoryEntry entry : DI.getRepositories().history)
        {
            if (entry.getItemType() == HistoryEntry.Type.Expense && entry.getItemId() == item.getId())
                return true;
        }

        return false;
    }

    @Override
    public ExpenseItem[] deserializeArray(JsonArray array)
    {
        return DI.gson.fromJson(array.toString(), ExpenseItem[].class);
    }
}
