package model.repository;

import com.google.gson.reflect.TypeToken;
import model.ExpenseItem;
import model.Tag;
import utils.DI;

import java.lang.reflect.Type;
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
        // TODO: Implement
        return new ArrayList<>();
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

    @Override
    public void deserialize(String json)
    {
        for (ExpenseItem entity : DI.gson.fromJson(json, ExpenseItem[].class))
        {
            entities.put(entity.getId(), entity);
        }
    }
}
