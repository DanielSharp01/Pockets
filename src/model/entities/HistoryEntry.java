package model.entities;

import model.filters.IFilterable;
import utils.DI;

import java.time.LocalDateTime;
import java.util.*;

/**
 * History entry of an item with a date
 */
public class HistoryEntry extends Entity implements IFilterable, Cloneable {

    /**
     * Type of HistoryEntry item
     */
    public enum Type
    {
        Expense,
        Income
    }

    /**
     * Id of the item
     */
    private int itemId;

    /**
     * Type of the item
     */
    private Type itemType;

    /**
     * DatePart when this entry was added
     */
    private LocalDateTime date;

    /**
     * @param id Integer key
     * @param itemId Id of the item
     * @param itemType Type of the item
     * @param date DatePart when this entry was added
     */
    public HistoryEntry(int id, int itemId, Type itemType, LocalDateTime date) {
        super(id);
        this.itemId = itemId;
        this.itemType = itemType;
        this.date = date;
    }

    /**
     * @return Id of the item
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * @param itemId Id of the item
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * @return Type of the item
     */
    public Type getItemType() {
        return itemType;
    }

    /**
     * @param itemType Type of the item
     */
    public void setItemType(Type itemType) {
        this.itemType = itemType;
    }

    /**
     * @return DatePart when this entry was added
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * @param date DatePart when this entry was added
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * The list of the Tag ids
     */
    private List<Integer> tagIds = new ArrayList<>();

    public Item getItem()
    {
        return itemType == Type.Income
                ? DI.getRepositories().incomes.findById(itemId)
                : DI.getRepositories().expenses.findById(itemId);
    }

    /**
     * @return The list of the Tag ids
     */
    public List<Integer> getTagIds() {
        return tagIds;
    }

    @Override
    public Collection<String> getWords() {
        Set<String> words = new HashSet<>();
        Item item = getItem();
        if (item != null)
        {
            words.addAll(item.getWords());
        }

        for (int tagId : tagIds)
        {
            Tag tag = DI.getRepositories().tags.findById(tagId);
            if (tag != null)
            {
                words.addAll(tag.getWords());
            }
        }

        return words;
    }

    @Override
    public HistoryEntry clone()
    {
        return new HistoryEntry(getId(), getItemId(), getItemType(), getDate());
    }
}
