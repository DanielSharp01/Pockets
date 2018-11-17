package model.test;

import model.*;
import model.entities.ExpenseItem;
import model.entities.HistoryEntry;
import model.entities.IncomeSource;
import model.entities.Tag;
import org.junit.jupiter.api.Test;
import utils.ColorUtils;
import utils.DI;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializationTest {
    @Test
    public void testRecurrenceSerialization()
    {
        Recurrence recurrence = new WeeklyRecurrence(LocalDateTime.of(2018, 11, 4, 8, 0, 0), 2);
        String json = DI.gson.toJson(recurrence, Recurrence.class);
        System.out.println(json);
        Recurrence recurrence2 = DI.gson.fromJson(json, Recurrence.class);
        assertEquals(recurrence.getClass(), recurrence2.getClass());
        assertEquals(recurrence.getLastOccurrence(), recurrence2.getLastOccurrence());
        assertEquals(recurrence.getEveryX(), recurrence2.getEveryX());
    }

    @Test
    public void testTagSerialization()
    {
        Tag tag = new Tag(1);
        tag.setName("Tag");
        tag.setColor(ColorUtils.fromHex("#505050"));
        String json = DI.gson.toJson(tag, Tag.class);
        System.out.println(json);
        Tag tag2 = DI.gson.fromJson(json, Tag.class);
        assertEquals(tag.getId(), tag2.getId());
        assertEquals(tag.getName(), tag2.getName());
        assertEquals(tag.getColor(), tag2.getColor());
    }

    @Test
    public void testIncomeSourceSerialization() throws MalformedURLException
    {
        IncomeSource source = new IncomeSource(1);
        source.setName("Income source");
        source.setMoney(new Money("USD", new BigDecimal("2")));
        source.setColor(ColorUtils.fromHex("#505050"));
        source.setImageResource(new URL("https://keep.google.com"));
        source.setRecurrence(new DailyRecurrence(LocalDateTime.of(2018, 11, 4, 8, 0, 0), 2));
        String json = DI.gson.toJson(source, IncomeSource.class);
        System.out.println(json);
        IncomeSource source2 =  DI.gson.fromJson(json, IncomeSource.class);
        assertEquals(source.getId(), source2.getId());
        assertEquals(source.getName(), source2.getName());
        assertEquals(source.getColor(), source2.getColor());
        assertEquals(source.getMoney().getCurrency(), source2.getMoney().getCurrency());
        assertEquals(source.getMoney().getAmount(), source2.getMoney().getAmount());
        assertEquals(source.getImageResource(), source2.getImageResource());
        assertEquals(source.getRecurrence().getClass(), source2.getRecurrence().getClass());
        assertEquals(source.getRecurrence().getLastOccurrence(), source2.getRecurrence().getLastOccurrence());
        assertEquals(source.getRecurrence().getEveryX(), source2.getRecurrence().getEveryX());
    }

    @Test
    public void testExpenseItemSerialization() throws MalformedURLException
    {
        ExpenseItem expense = new ExpenseItem(1);
        expense.setName("Expense item");
        expense.setMoney(new Money("USD", new BigDecimal("2")));
        expense.setColor(ColorUtils.fromHex("#505050"));
        expense.setImageResource(new URL("https://keep.google.com"));
        expense.setRecurrence(new DailyRecurrence(LocalDateTime.of(2018, 11, 4, 8, 0, 0), 2));
        String json = DI.gson.toJson(expense, ExpenseItem.class);
        System.out.println(json);
        ExpenseItem expense2 =  DI.gson.fromJson(json, ExpenseItem.class);
        assertEquals(expense.getId(), expense2.getId());
        assertEquals(expense.getName(), expense2.getName());
        assertEquals(expense.getColor(), expense2.getColor());
        assertEquals(expense.getMoney().getCurrency(), expense2.getMoney().getCurrency());
        assertEquals(expense.getMoney().getAmount(), expense2.getMoney().getAmount());
        assertEquals(expense.getImageResource(), expense2.getImageResource());
        assertEquals(expense.getRecurrence().getClass(), expense2.getRecurrence().getClass());
        assertEquals(expense.getRecurrence().getLastOccurrence(), expense2.getRecurrence().getLastOccurrence());
        assertEquals(expense.getRecurrence().getEveryX(), expense2.getRecurrence().getEveryX());
    }

    @Test
    public void testHistoryItemSerialization()
    {
        HistoryEntry entry = new HistoryEntry(1, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 11, 4, 8, 21, 23));
        String json = DI.gson.toJson(entry, HistoryEntry.class);
        System.out.println(json);
        HistoryEntry entry2 =  DI.gson.fromJson(json, HistoryEntry.class);
        assertEquals(entry.getId(), entry2.getId());
        assertEquals(entry.getItemId(), entry2.getItemId());
        assertEquals(entry.getItemType(), entry2.getItemType());
        assertEquals(entry.getDate(), entry2.getDate());
    }
}
