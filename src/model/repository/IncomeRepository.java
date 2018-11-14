package model.repository;

import model.IncomeItem;
import model.Tag;
import utils.DI;
import utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository interface for {@link IncomeItem}
 */
public class IncomeRepository extends EntityRepository<IncomeItem> {

    /**
     * @param container Parent repository container, used by repositories accessing other repositories
     */
    public IncomeRepository(RepositoryContainer container) {
        super(container);
    }

    /**
     * Filters items by a search term
     * @param searchTerm Search term consisting of words that need to be found in the item's name
     * @return Matching list of items
     */
    public List<IncomeItem> filterBySearch(String searchTerm)
    {
        List<IncomeItem> filtered = new ArrayList<>();
        String[] words = searchTerm.split(" ");
        for (IncomeItem entity : entities.values())
        {
            if (StringUtils.containsAll(entity.getName(), words))
                filtered.add(entity);
        }

        return filtered;
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
