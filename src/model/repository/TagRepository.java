package model.repository;

import com.google.gson.JsonArray;
import model.entities.Tag;
import model.filters.SatisfyFilter;
import utils.DI;

import java.util.List;

/**
 * Repository interface for {@link Tag}
 */
public class TagRepository extends EntityRepository<Tag> {

    /**
     * @param container Parent repository container, used by repositories accessing other repositories
     */
    public TagRepository(RepositoryContainer container) {
        super(container);
    }

    /**
     * Filters tags by a search term
     * @param searchTerm Search term consisting of words that need to be found in the Tag's name
     * @return Matching list of tags
     */
    public List<Tag> filterBySearch(String searchTerm)
    {
        SatisfyFilter filter = new SatisfyFilter(searchTerm);
        return (List<Tag>) filter.filter(entities.values());
    }

    @Override
    public Tag[] deserializeArray(JsonArray array)
    {
        return DI.gson.fromJson(array.toString(), Tag[].class);
    }
}
