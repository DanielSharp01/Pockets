package model.repository;

import model.IncomeItem;
import model.Tag;
import utils.DI;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository interface for {@link IncomeItem}
 */
public class IncomeRepository extends EntityRepository<IncomeItem> {

    /**
     * Filters items by a search term
     * @param searchTerm Search term consisting of words that need to be found in the item's name
     * @return Matching list of items
     */
    public List<IncomeItem> filterBySearch(String searchTerm)
    {
        // TODO: Implement
        return new ArrayList<>();
    }

    /**
     * Gets all items with a given tag
     * @param tagId Tag's id to get tagged items of
     * @return Matching list of items
     */
    public List<IncomeItem> withTag(int tagId)
    {
        List<IncomeItem> list = new ArrayList<>();
        for (IncomeItem item : entities.values())
        {
            if (item.getTagIds().contains(tagId))
                list.add(item);
        }

        return list;
    }

    @Override
    public void deserialize(String json)
    {
        for (IncomeItem entity : DI.gson.fromJson(json, IncomeItem[].class))
        {
            entities.put(entity.getId(), entity);
        }
    }
}
