package model.repository;

import model.Tag;
import utils.DI;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository interface for {@link Tag}
 */
public class TagRepository extends EntityRepository<Tag> {

    /**
     * Filters tags by a search term
     * @param searchTerm Search term consisting of words that need to be found in the Tag's name
     * @return Matching list of tags
     */
    public List<Tag> filterBySearch(String searchTerm)
    {
        // TODO: Implement
        return new ArrayList<>();
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
