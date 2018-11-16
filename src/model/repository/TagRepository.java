package model.repository;

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
    public void deserialize(String json)
    {
        for (Tag entity : DI.gson.fromJson(json, Tag[].class))
        {
            entities.put(entity.getId(), entity);
        }
    }
}
