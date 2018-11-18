package model.test;

import model.*;
import model.entities.ExpenseItem;
import model.entities.IncomeSource;
import model.entities.Tag;
import model.repository.ExpenseRepository;
import model.repository.IncomeRepository;
import model.repository.TagRepository;
import org.junit.jupiter.api.Test;
import utils.ColorUtils;

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
        item.setRecurrence(new MonthlyRecurrence(LocalDateTime.of(2018, 11, 4, 8, 0, 0), 2));
        item.setColor(ColorUtils.fromHex("#FF000000"));
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
        assertEquals(item1.getRecurrence().getLastOccurrence(), item2.getRecurrence().getLastOccurrence());
        assertEquals(item1.getRecurrence().getEveryX(), item2.getRecurrence().getEveryX());
        assertEquals(item1.getImageResource(), item2.getImageResource());
    }

    @Test
    public void testByTagRepository()
    {
        IncomeRepository repository = new IncomeRepository(null);
        IncomeSource item = new IncomeSource(1);
        item.getTagIds().add(1);
        item.getTagIds().add(2);
        repository.add(item);
        IncomeSource item2 = new IncomeSource(2);
        item2.getTagIds().add(1);
        repository.add(item2);
        IncomeSource item3 = new IncomeSource(3);
        item3.getTagIds().add(2);
        repository.add(item3);

        List<IncomeSource> items = repository.withTag(2);
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
