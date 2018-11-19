package model.repository;

import com.google.gson.JsonArray;
import model.entities.HistoryEntry;
import model.filters.SatisfyFilter;
import utils.DI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Repository interface for {@link HistoryEntry}
 */
public class HistoryRepository extends EntityRepository<HistoryEntry> {

    /**
     * @param container Parent repository container, used by repositories accessing other repositories
     */
    public HistoryRepository(RepositoryContainer container) {
        super(container);
    }

    /**
     * Selects entries of the specific day
     * @param date Day to select entries of
     * @return Matching list of entries
     */
    public List<HistoryEntry> forDay(LocalDate date)
    {
        List<HistoryEntry> list = new ArrayList<>();
        for (HistoryEntry item : entities.values())
        {
            if (DAYS.between(item.getDate(), date) == 0)
                list.add(item);
        }

        return list;
    }

    /**
     * Gets all items with a given tag
     * @param tagId Tag's id to get tagged items of
     * @return Matching list of items
     */
    public List<HistoryEntry> withTag(int tagId)
    {
        List<HistoryEntry> list = new ArrayList<>();
        for (HistoryEntry item : entities.values())
        {
            if (item.getTagIds().contains(tagId))
                list.add(item);
        }

        return list;
    }

    /**
     * Filters entries by the search term
     * @param searchTerm Search term consisting of words that need to be found in the entry's name
     * @return Matching list of entries
     */
    public List<HistoryEntry> filterBySearch(String searchTerm)
    {
        SatisfyFilter filter = new SatisfyFilter(searchTerm);
        return (List<HistoryEntry>) filter.filter(entities.values());
    }

    @Override
    public HistoryEntry[] deserializeArray(JsonArray array)
    {
        return DI.gson.fromJson(array.toString(), HistoryEntry[].class);
    }
}
