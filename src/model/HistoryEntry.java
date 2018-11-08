package model;

import java.time.LocalDateTime;

/**
 * History entry of an item with a date
 */
public class HistoryEntry extends Entity {
    /**
     * Item of this HistoryEntry
     */
    private Item item;

    /**
     * Date of this HistoryEntry
     */
    private LocalDateTime date;

    /**
     * @param id Integer key
     * @param item Item of this HistoryEntry
     * @param date Date of this HistoryEntry
     */
    public HistoryEntry(int id, Item item, LocalDateTime date) {
        super(id);
        this.item = item;
        this.date = date;
    }

    /**
     * @return Item of this HistoryEntry
     */
    public Item getItem() {
        return item;
    }

    /**
     * @param item Item of this HistoryEntry
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * @return Date of this HistoryEntry
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * @param date Date of this HistoryEntry
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
