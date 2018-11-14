package model;

import java.time.LocalDateTime;

/**
 * History entry of an item with a date
 */
public class HistoryEntry extends Entity {

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
     * Date when this entry was added
     */
    private LocalDateTime date;

    /**
     * @param id Integer key
     * @param itemId Id of the item
     * @param itemType Type of the item
     * @param date Date when this entry was added
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
     * @return Date when this entry was added
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * @param date Date when this entry was added
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
