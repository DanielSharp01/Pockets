package model.test;

import model.ExpenseItem;
import model.HistoryEntry;
import model.IncomeSource;
import model.Tag;
import model.repository.RepositoryContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utils.DI;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTest {

    @BeforeAll
    public static void before()
    {
        DI.swapRepositories(new RepositoryContainer());

        Tag tag = new Tag(1);
        tag.setName("Food");
        DI.getRepositories().tags.add(tag);
        tag = new Tag(2);
        tag.setName("Drink");
        DI.getRepositories().tags.add(tag);
        tag = new Tag(3);
        tag.setName("Recurrent expenses");
        DI.getRepositories().tags.add(tag);
        tag = new Tag(4);
        tag.setName("University");
        DI.getRepositories().tags.add(tag);
        tag = new Tag(5);
        tag.setName("Food & Drink");
        DI.getRepositories().tags.add(tag);

        IncomeSource source = new IncomeSource(1);
        source.setName("Scholarship");
        source.getTagIds().add(4);
        DI.getRepositories().incomes.add(source);
        source = new IncomeSource(2);
        source.setName("Job");
        DI.getRepositories().incomes.add(source);

        ExpenseItem expense = new ExpenseItem(1);
        expense.setName("Pizza");
        expense.getTagIds().add(1);
        expense.getTagIds().add(5);
        DI.getRepositories().expenses.add(expense);
        expense = new ExpenseItem(2);
        expense.setName("Coke");
        expense.getTagIds().add(2);
        expense.getTagIds().add(5);
        DI.getRepositories().expenses.add(expense);
        expense = new ExpenseItem(3);
        expense.setName("Rent");
        expense.getTagIds().add(3);
        DI.getRepositories().expenses.add(expense);

        int cnt = 1;
        for (int i = 1; i <= 3; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                DI.getRepositories().history.add(new HistoryEntry(cnt++, i, HistoryEntry.Type.Expense, LocalDateTime.now()));
            }
        }

        for (int i = 1; i <= 3; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                DI.getRepositories().history.add(new HistoryEntry(cnt++, i, HistoryEntry.Type.Income, LocalDateTime.now()));
            }
        }
    }

    static SearchTestParam[] dataSearchForTags() {
        return new SearchTestParam[] {
                new SearchTestParam("University", new Integer[] {4}),
                new SearchTestParam("Food", new Integer[] {1, 5}),
                new SearchTestParam("Drink", new Integer[] {2, 5}),
                new SearchTestParam("Recu expen", new Integer[] {3}),
                new SearchTestParam("University food", new Integer[] {}),
        };
    }

    @ParameterizedTest
    @MethodSource("dataSearchForTags")
    public void testSearchForTags(SearchTestParam param)
    {
        List<Tag> filtered = DI.getRepositories().tags.filterBySearch(param.getSearchTerm());
        for (Tag tag : DI.getRepositories().tags)
        {
            if (param.getIdsExpected().contains(tag.getId()))
                assertTrue(filtered.contains(tag));
            else
                assertFalse(filtered.contains(tag));
        }
    }

    static SearchTestParam[] dataSearchForIncomeSources() {
        return new SearchTestParam[] {
                new SearchTestParam("University", new Integer[] {1}),
                new SearchTestParam("Job", new Integer[] {2}),
                new SearchTestParam("Scholarship", new Integer[] {1}),
                new SearchTestParam("Job scholarship", new Integer[] {}),
        };
    }

    @ParameterizedTest
    @MethodSource("dataSearchForIncomeSources")
    public void testSearchForIncomeSources(SearchTestParam param)
    {
        List<IncomeSource> filtered = DI.getRepositories().incomes.filterBySearch(param.getSearchTerm());
        for (IncomeSource source : DI.getRepositories().incomes)
        {
            if (param.getIdsExpected().contains(source.getId()))
                assertTrue(filtered.contains(source));
            else
                assertFalse(filtered.contains(source));
        }
    }

    static SearchTestParam[] dataSearchForExpenseItems() {
        return new SearchTestParam[] {
                new SearchTestParam("Pizza", new Integer[] {1}),
                new SearchTestParam("Pizza Food & Drink", new Integer[] {1}),
                new SearchTestParam("Food", new Integer[] {1, 2}),
                new SearchTestParam("Recu exp", new Integer[] {3}),
                new SearchTestParam("Pizza rent", new Integer[] {}),
        };
    }

    @ParameterizedTest
    @MethodSource("dataSearchForExpenseItems")
    public void testSearchForExpenseItems(SearchTestParam param)
    {
        List<ExpenseItem> filtered = DI.getRepositories().expenses.filterBySearch(param.getSearchTerm());
        for (ExpenseItem expense : DI.getRepositories().expenses)
        {
            if (param.getIdsExpected().contains(expense.getId()))
                assertTrue(filtered.contains(expense));
            else
                assertFalse(filtered.contains(expense));
        }
    }

    static SearchTestParam[] dataSearchForHistoryEntries() {
        return new SearchTestParam[] {
                new SearchTestParam("University", new Integer[] {7, 8}),
                new SearchTestParam("Job", new Integer[] {9, 10}),
                new SearchTestParam("Scholarship", new Integer[] {7, 8}),
                new SearchTestParam("Job scholarship", new Integer[] {}),
                new SearchTestParam("Pizza", new Integer[] {1, 2}),
                new SearchTestParam("Pizza Food & Drink", new Integer[] {1, 2}),
                new SearchTestParam("Food", new Integer[] {1, 2, 3, 4}),
                new SearchTestParam("Recu exp", new Integer[] {5, 6}),
                new SearchTestParam("Pizza rent", new Integer[] {}),
        };
    }

    @ParameterizedTest
    @MethodSource("dataSearchForHistoryEntries")
    public void testSearchForHistoryEntries(SearchTestParam param)
    {
        List<HistoryEntry> filtered = DI.getRepositories().history.filterBySearch(param.getSearchTerm());
        for (HistoryEntry entry : DI.getRepositories().history)
        {
            if (param.getIdsExpected().contains(entry.getId()))
                assertTrue(filtered.contains(entry));
            else
                assertFalse(filtered.contains(entry));
        }
    }
}
