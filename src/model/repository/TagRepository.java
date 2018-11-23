package model.repository;

import com.google.gson.JsonArray;
import model.entities.ExpenseItem;
import model.entities.HistoryEntry;
import model.entities.IncomeSource;
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

    /**
     * Determines whether a tag is used on any item or history entry
     * @return True if used, false otherwise
     */
    public boolean isUsed(Tag tag)
    {
        if (!DI.getRepositories().expenses.withTag(tag.getId()).isEmpty()) return true;
        if (!DI.getRepositories().incomes.withTag(tag.getId()).isEmpty()) return true;
        if (!DI.getRepositories().history.withTag(tag.getId()).isEmpty()) return true;

        return false;
    }

    /**
     * Remove the tag from any item or history entry using it
     */
    public void removeTagUsage(Tag tag)
    {
        for (ExpenseItem item : DI.getRepositories().expenses.withTag(tag.getId()))
        {
            item.getTagIds().remove((Integer)tag.getId());
        }

        for (IncomeSource item : DI.getRepositories().incomes.withTag(tag.getId()))
        {
            item.getTagIds().remove((Integer)tag.getId());
        }

        for (HistoryEntry item : DI.getRepositories().history.withTag(tag.getId()))
        {
            item.getTagIds().remove((Integer)tag.getId());
        }
    }

    @Override
    public Tag[] deserializeArray(JsonArray array)
    {
        return DI.gson.fromJson(array.toString(), Tag[].class);
    }
}
