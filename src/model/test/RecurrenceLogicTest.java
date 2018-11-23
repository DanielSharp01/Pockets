package model.test;

import model.DailyRecurrence;
import model.RecurrenceLogic;
import model.WeeklyRecurrence;
import model.entities.ExpenseItem;
import model.entities.HistoryEntry;
import model.entities.IncomeSource;
import model.repository.RepositoryContainer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DI;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecurrenceLogicTest {

    @BeforeEach
    public void testSetup()
    {
        RepositoryContainer container = new RepositoryContainer();
        DI.swapRepositories(container);
        ExpenseItem item = new ExpenseItem(1);
        item.setRecurrence(new DailyRecurrence(LocalDateTime.of(2018, 6, 1, 8, 0, 0)));
        container.expenses.add(item);
        item = new ExpenseItem(2);
        item.setRecurrence(new DailyRecurrence(LocalDateTime.of(2018, 6, 1, 8, 0, 0), 2));
        container.expenses.add(item);
        IncomeSource source = new IncomeSource(1);
        source.setRecurrence(null);
        container.incomes.add(source);
        source = new IncomeSource(2);
        source.setRecurrence(new WeeklyRecurrence(LocalDateTime.of(2018, 6, 1, 8, 0, 0), 1));
        container.incomes.add(source);
    }

    @AfterEach
    public void testTearDown()
    {
        DI.swapRepositories(null);
    }

    private boolean hasHistoryEntry(HistoryEntry testEntry)
    {
        for (HistoryEntry entry : DI.getRepositories().history)
        {
            if (entry.getItemId() == testEntry.getItemId() && entry.getItemType() == testEntry.getItemType() && entry.getDate().equals(testEntry.getDate()))
                return true;
        }

        return false;
    }

    @Test
    public void testToday()
    {
        RecurrenceLogic.checkRecurrences(
                LocalDateTime.of(2018, 6, 2, 7, 0, 0),
                LocalDateTime.of(2018, 6, 2, 9, 0, 0));

        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 2, 8, 0, 0))));
        assertEquals(1, DI.getRepositories().history.asObservableList().size());
    }

    @Test
    public void testBeforeDoesNotTrigger()
    {
        RecurrenceLogic.checkRecurrences(
                LocalDateTime.of(2018, 5, 1, 0, 0, 0),
                LocalDateTime.of(2018, 6, 2, 7, 0, 0));

        assertEquals(0, DI.getRepositories().history.asObservableList().size());
    }

    @Test
    public void testTwoDays()
    {
        RecurrenceLogic.checkRecurrences(
                LocalDateTime.of(2018, 6, 1, 7, 0, 0),
                LocalDateTime.of(2018, 6, 3, 9, 0, 0));

        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 2, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 3, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 2, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 3, 8, 0, 0))));
        assertEquals(3, DI.getRepositories().history.asObservableList().size());
    }

    @Test
    public void testWeekly()
    {
        RecurrenceLogic.checkRecurrences(
                LocalDateTime.of(2018, 6, 1, 7, 0, 0),
                LocalDateTime.of(2018, 6, 8, 9, 0, 0));

        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 2, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 3, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 4, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 5, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 6, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 7, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 8, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 2, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 3, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 2, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 5, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 2, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 7, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 2, HistoryEntry.Type.Income, LocalDateTime.of(2018, 6, 8, 8, 0, 0))));
        assertEquals(LocalDateTime.of(2018, 6, 8, 8, 0, 0), DI.getRepositories().expenses.findById(1).getRecurrence().getLastOccurrence());
        assertEquals(11, DI.getRepositories().history.asObservableList().size());
    }

    @Test
    public void testDoesNotQuiteReach()
    {
        RecurrenceLogic.checkRecurrences(
                LocalDateTime.of(2018, 6, 1, 7, 0, 0),
                LocalDateTime.of(2018, 6, 8, 6, 59, 59));

        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 2, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 3, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 4, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 5, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 6, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 7, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 2, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 3, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 2, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 5, 8, 0, 0))));
        assertTrue(hasHistoryEntry(new HistoryEntry(0, 2, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 6, 7, 8, 0, 0))));
        assertEquals(LocalDateTime.of(2018, 6, 7, 8, 0, 0), DI.getRepositories().expenses.findById(1).getRecurrence().getLastOccurrence());
        assertEquals(9, DI.getRepositories().history.asObservableList().size());
    }
}
