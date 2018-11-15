package model.test;

import model.ExpenseItem;
import model.HistoryEntry;
import model.IncomeItem;
import model.Tag;
import model.repository.IncomeRepository;
import model.repository.RepositoryContainer;
import model.repository.TagRepository;
import org.junit.jupiter.api.Test;
import utils.DI;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTest {
    @Test
    public void testBasicSearch() {
        TagRepository repository = new TagRepository(null);
        Tag tag = new Tag(1);
        tag.setName("Tag with multiple words");
        repository.add(tag);
        tag = new Tag(2);
        tag.setName("Tag with words");
        repository.add(tag);
        tag = new Tag(3);
        tag.setName("Tag multiple words");
        repository.add(tag);
        List<Tag> searchResult = repository.filterBySearch("Tag");
        assertTrue(searchResult.contains(repository.findById(1)));
        assertTrue(searchResult.contains(repository.findById(2)));
        assertTrue(searchResult.contains(repository.findById(3)));
        searchResult = repository.filterBySearch("Tag multiple");
        assertTrue(searchResult.contains(repository.findById(1)));
        assertFalse(searchResult.contains(repository.findById(2)));
        assertTrue(searchResult.contains(repository.findById(3)));
        searchResult = repository.filterBySearch("Ta multip");
        assertTrue(searchResult.contains(repository.findById(1)));
        assertFalse(searchResult.contains(repository.findById(2)));
        assertTrue(searchResult.contains(repository.findById(3)));
    }

    @Test
    public void testHistorySearch() {
        RepositoryContainer container = new RepositoryContainer();
        DI.swapRepositories(container);
        IncomeItem inc = new IncomeItem(1);
        inc.setName("Income item");
        container.incomes.add(inc);
        ExpenseItem exp = new ExpenseItem(1);
        exp.setName("Expense item");
        container.expenses.add(exp);

        container.history.add(new HistoryEntry(1, 1, HistoryEntry.Type.Income, LocalDateTime.now()));
        container.history.add(new HistoryEntry(2, 1, HistoryEntry.Type.Income, LocalDateTime.now()));
        container.history.add(new HistoryEntry(3, 1, HistoryEntry.Type.Expense, LocalDateTime.now()));

        List<HistoryEntry> searchResult = container.history.filterBySearch("item");
        assertTrue(searchResult.contains(container.history.findById(1)));
        assertTrue(searchResult.contains(container.history.findById(2)));
        assertTrue(searchResult.contains(container.history.findById(3)));
        searchResult = container.history.filterBySearch("Income");
        assertTrue(searchResult.contains(container.history.findById(1)));
        assertTrue(searchResult.contains(container.history.findById(2)));
        assertFalse(searchResult.contains(container.history.findById(3)));
        searchResult = container.history.filterBySearch("Expense");
        assertFalse(searchResult.contains(container.history.findById(1)));
        assertFalse(searchResult.contains(container.history.findById(2)));
        assertTrue(searchResult.contains(container.history.findById(3)));
        searchResult = container.history.filterBySearch("Expense item income");
        assertFalse(searchResult.contains(container.history.findById(1)));
        assertFalse(searchResult.contains(container.history.findById(2)));
        assertFalse(searchResult.contains(container.history.findById(3)));
    }

    @Test
    public void testBasicTagSearch() {
        RepositoryContainer container = new RepositoryContainer();
        DI.swapRepositories(container);

        Tag tag = new Tag(1);
        tag.setName("Food");
        container.tags.add(tag);
        tag = new Tag(2);
        tag.setName("Drink");
        container.tags.add(tag);

        ExpenseItem item = new ExpenseItem(1);
        item.setName("Pizza");
        item.getTagIds().add(1);
        container.expenses.add(item);
        item = new ExpenseItem(2);
        item.setName("Coke");
        item.getTagIds().add(2);
        container.expenses.add(item);

        List<ExpenseItem> searchResult = container.expenses.filterBySearch("Food");
        assertTrue(searchResult.contains(container.expenses.findById(1)));
        assertFalse(searchResult.contains(container.expenses.findById(2)));

        searchResult = container.expenses.filterBySearch("Drink");
        assertFalse(searchResult.contains(container.expenses.findById(1)));
        assertTrue(searchResult.contains(container.expenses.findById(2)));

    }
}
