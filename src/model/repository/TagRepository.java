package model.repository;

import model.Tag;
import utils.DI;
import utils.StringUtils;

import java.util.ArrayList;
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
        List<Tag> filtered = new ArrayList<>();
        String[] words = searchTerm.split(" ");
        for (Tag entity : entities.values())
        {
            if (StringUtils.containsAll(entity.getName(), words))
                filtered.add(entity);
        }

        return filtered;
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
