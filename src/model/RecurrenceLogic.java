package model;

import app.Settings;
import model.entities.ExpenseItem;
import model.entities.HistoryEntry;
import model.entities.IncomeSource;
import model.entities.Item;
import utils.DI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class RecurrenceLogic {

    public static void setupTimedRecurrenceChecks()
    {
        if (Settings.getInstance().getLastRecurrenceCheck() == null)
        {
            Settings.getInstance().setLastRecurrenceCheck(LocalDateTime.now());
        }
        checkRecurrencesNow();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkRecurrencesNow();
            }
        }, (60 - LocalDateTime.now().getSecond()) * 1000, 60000);
    }

    public static void checkRecurrencesNow()
    {
        LocalDateTime now = LocalDateTime.now();
        checkRecurrences(Settings.getInstance().getLastRecurrenceCheck(), now);
        Settings.getInstance().setLastRecurrenceCheck(now);
    }

    /**
     * Check recurrences and add recurring items to the history
     * @param lastTime Last time recurrences were checked
     * @param now Current time
     */
    public static void checkRecurrences(LocalDateTime lastTime, LocalDateTime now)
    {
        LocalDate day = lastTime.toLocalDate();

        // Check full days
        while (day.isBefore(now.toLocalDate()))
        {
            checkRecurrenceOnDay(day);
            day = day.plusDays(1);
        }

        // Check this day
        checkRecurrenceToday(now);
    }

    /**
     * Check recurrences on a full day (time does not matter, we assume the full day passed)
     * @param date Day of check
     */
    private static void checkRecurrenceOnDay(LocalDate date)
    {
        List<Item> recurringItems = new ArrayList<>();

        for (IncomeSource item : DI.getRepositories().incomes)
        {
            if (item.getRecurrence().isOccurrence(date)) recurringItems.add(item);
        }

        for (ExpenseItem item : DI.getRepositories().expenses)
        {
            if (item.getRecurrence().isOccurrence(date)) recurringItems.add(item);
        }

        Collections.sort(recurringItems, Comparator.comparing(i -> i.getRecurrence().getLastOccurrence().toLocalTime()));
        for (Item item : recurringItems)
        {
            addToHistory(item, date.atTime(item.getRecurrence().getLastOccurrence().toLocalTime()));
        }
    }

    /**
     * Check recurrences today (time still matters, the day did not pass yet)
     * @param dateTime Current time
     */
    private static void checkRecurrenceToday(LocalDateTime dateTime)
    {
        List<Item> recurringItems = new ArrayList<>();

        for (IncomeSource item : DI.getRepositories().incomes)
        {
            if (item.getRecurrence().isOccurrence(dateTime.toLocalDate()) && !item.getRecurrence().getLastOccurrence().toLocalTime().isAfter(dateTime.toLocalTime()))
                recurringItems.add(item);
        }

        for (ExpenseItem item : DI.getRepositories().expenses)
        {
            if (item.getRecurrence().isOccurrence(dateTime.toLocalDate()) && !item.getRecurrence().getLastOccurrence().toLocalTime().isAfter(dateTime.toLocalTime()))
                recurringItems.add(item);
        }

        Collections.sort(recurringItems, Comparator.comparing(i -> i.getRecurrence().getLastOccurrence().toLocalTime()));
        for (Item item : recurringItems)
        {
            addToHistory(item, dateTime.toLocalDate().atTime(item.getRecurrence().getLastOccurrence().toLocalTime()));
        }
    }

    /**
     * Add a specific item to history at specific time
     * @param item Item to add
     * @param at Time to add it at
     */
    private static void addToHistory(Item item, LocalDateTime at)
    {
        HistoryEntry.Type type =  (item instanceof IncomeSource) ? HistoryEntry.Type.Income : HistoryEntry.Type.Expense;
        DI.getRepositories().history.add(new HistoryEntry(0, item.getId(), type, at));
    }
}
