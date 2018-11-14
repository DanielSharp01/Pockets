package model.repository;

import model.HistoryEntry;
import model.Tag;
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
     * Filters entries by the search term
     * @param searchTerm Search term consisting of words that need to be found in the entry's name
     * @return Matching list of entries
     */
    public List<HistoryEntry> filterBySearch(String searchTerm)
    {
        // TODO: Implement
        return new ArrayList<>();
    }

    @Override
    public void deserialize(String json)
    {
        for (HistoryEntry entity : DI.gson.fromJson(json, HistoryEntry[].class))
        {
            entities.put(entity.getId(), entity);
        }
    }
}
