package model.repository;

import com.google.gson.JsonArray;
import model.entities.IncomeSource;
import model.filters.SatisfyFilter;
import utils.DI;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository interface for {@link IncomeSource}
 */
public class IncomeRepository extends EntityRepository<IncomeSource> {

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
    public List<IncomeSource> filterBySearch(String searchTerm)
    {
        SatisfyFilter filter = new SatisfyFilter(searchTerm);
        return (List<IncomeSource>) filter.filter(entities.values());
    }

    /**
     * Gets all items with a given tag
     * @param tagId Tag's id to get tagged items of
     * @return Matching list of items
     */
    public List<IncomeSource> withTag(int tagId)
    {
        List<IncomeSource> list = new ArrayList<>();
        for (IncomeSource item : entities.values())
        {
            if (item.getTagIds().contains(tagId))
                list.add(item);
        }

        return list;
    }

    @Override
    public IncomeSource[] deserializeArray(JsonArray array)
    {
        return DI.gson.fromJson(array.toString(), IncomeSource[].class);
    }
}
