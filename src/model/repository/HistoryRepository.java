package model.repository;

import model.HistoryEntry;
import model.Item;
import model.Tag;
import utils.DI;
import utils.StringUtils;

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
     * Filters entries by the search term
     * @param searchTerm Search term consisting of words that need to be found in the entry's name
     * @return Matching list of entries
     */
    public List<HistoryEntry> filterBySearch(String searchTerm)
    {
        List<HistoryEntry> filtered = new ArrayList<>();
        String[] words = searchTerm.split(" ");
        for (HistoryEntry entity : entities.values())
        {
            Item item;
            if (entity.getItemType() == HistoryEntry.Type.Expense)
                item = container.expenses.findById(entity.getItemId());
            else
                item = container.incomes.findById(entity.getItemId());

            if (item != null && StringUtils.containsAll(item.getName(), words))
                filtered.add(entity);
        }

        return filtered;
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
