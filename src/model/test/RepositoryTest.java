package model.test;

import model.*;
import model.repository.ExpenseRepository;
import model.repository.HistoryRepository;
import model.repository.IncomeRepository;
import model.repository.TagRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {
    @Test
    public void testJsonRepositorySerialization()
    {
        ExpenseRepository repository = new ExpenseRepository(null);
        ExpenseItem item = new ExpenseItem(1);
        item.setName("Coke 0.33ml");
        item.setMoney(new Money("USD", new BigDecimal("0.5")));
        item.setImageResource(null);
        item.setRecurrence(null);
        item.setColor(0xFF000000);
        repository.add(item);
        String json = repository.serialize();
        System.out.println(json);
        ExpenseRepository repository2 = new ExpenseRepository(null);
        repository2.deserialize(json);
        ExpenseItem item1;
        assertNotNull(item1 = repository.findById(1));
        assertSame(item, item1);
        ExpenseItem item2;
        assertNotNull(item2 = repository2.findById(1));
        assertEquals(item1.getId(), item2.getId());
        assertEquals(item1.getName(), item2.getName());
        assertEquals(item1.getColor(), item2.getColor());
        assertEquals(item1.getMoney().getCurrency(), item2.getMoney().getCurrency());
        assertEquals(item1.getMoney().getAmount(), item2.getMoney().getAmount());
        assertEquals(item1.getRecurrence().getClass(), item2.getRecurrence().getClass());
        assertEquals(item1.getImageResource(), item2.getImageResource());
    }

    @Test
    public void testRecurrenceSerialization()
    {
        ExpenseRepository repository = new ExpenseRepository(null);
        ExpenseItem item = new ExpenseItem(1);
        item.setName("Coke 0.33ml");
        item.setRecurrence(new MonthlyRecurrence(LocalDateTime.of(2018, 11, 5, 8, 0, 0), 2));
        item.setColor(0xFF000000);
        repository.add(item);
        String json = repository.serialize();
        System.out.println(json);
        ExpenseRepository repository2 = new ExpenseRepository(null);
        repository2.deserialize(json);
        ExpenseItem item2;
        assertNotNull(item2 = repository2.findById(1));
        assertEquals(item2.getRecurrence().getClass(), MonthlyRecurrence.class);
        assertEquals(item.getRecurrence().getLastOccurrence(), item2.getRecurrence().getLastOccurrence());
        assertEquals(item.getRecurrence().getEveryX(), item2.getRecurrence().getEveryX());
    }

    @Test
    public void testHistoryEntrySerialization()
    {
        HistoryRepository repository = new HistoryRepository(null);
        HistoryEntry entry = new HistoryEntry(1, 1, HistoryEntry.Type.Expense, LocalDateTime.of(2018, 11, 5, 8, 0, 0));
        repository.add(entry);
        String json = repository.serialize();
        System.out.println(json);
        HistoryRepository repository2 = new HistoryRepository(null);
        repository2.deserialize(json);
        HistoryEntry entry2;
        assertNotNull(entry2 = repository2.findById(1));
        assertEquals(entry.getItemId(), entry2.getItemId());
        assertEquals(entry.getItemType(), entry2.getItemType());
        assertEquals(entry.getDate(), entry2.getDate());
    }

    @Test
    public void testTagsSerialization()
    {
        IncomeRepository repository = new IncomeRepository(null);
        IncomeItem item = new IncomeItem(1);
        item.setName("Coke 0.33ml");
        item.setMoney(new Money("USD", new BigDecimal("0.5")));
        item.setImageResource(null);
        item.setRecurrence(null);
        item.getTagIds().add(1);
        item.getTagIds().add(2);
        item.setColor(0xFF000000);
        repository.add(item);
        String json = repository.serialize();
        System.out.println(json);
        IncomeRepository repository2 = new IncomeRepository(null);
        repository2.deserialize(json);
        IncomeItem item1;
        assertNotNull(item1 = repository.findById(1));
        assertSame(item, item1);
        IncomeItem item2;
        assertNotNull(item2 = repository2.findById(1));
        assertEquals(item.getTagIds().get(0), item2.getTagIds().get(0));
        assertEquals(item.getTagIds().get(1), item2.getTagIds().get(1));
    }

    @Test
    public void testByTagRepository()
    {
        IncomeRepository repository = new IncomeRepository(null);
        IncomeItem item = new IncomeItem(1);
        item.getTagIds().add(1);
        item.getTagIds().add(2);
        repository.add(item);
        IncomeItem item2 = new IncomeItem(2);
        item2.getTagIds().add(1);
        repository.add(item2);
        IncomeItem item3 = new IncomeItem(3);
        item3.getTagIds().add(2);
        repository.add(item3);

        List<IncomeItem> items = repository.withTag(2);
        assertTrue(items.contains(item));
        assertFalse(items.contains(item2));
        assertTrue(items.contains(item3));
    }

    @Test
    public void testUpdate()
    {
        TagRepository repository = new TagRepository(null);
        repository.add(new Tag(1));
        Tag tag = new Tag(1);
        tag.setName("Tag changed");
        repository.update(tag);
        assertEquals("Tag changed", repository.findById(1).getName());
    }

    @Test
    public void testDelete()
    {
        TagRepository repository = new TagRepository(null);
        repository.add(new Tag(1));
        repository.delete(1);
        assertNull(repository.findById(1));
        Tag tag = new Tag(2);
        repository.add(tag);
        assertEquals(tag, repository.findById(2));
        repository.delete(tag);
        assertNull(repository.findById(2));
    }
}
