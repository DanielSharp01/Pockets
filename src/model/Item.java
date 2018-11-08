package model;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Item class which has the properties of both Expenses and Income sources
 * @see ExpenseItem
 * @see IncomeItem
 */
public abstract class Item extends Entity {
    /**
     * Name of the item
     */
    private String name;

    /**
     * URL to the image (may be on the local file system or be a program resource)
     */
    private URL imageResource;

    /**
     * Background color of image circle as 4 byte integer
     */
    private int color;

    /**
     * Tell if and when this item will recur
     */
    private Recurrence recurrence;

    /**
     * The list of the Tags
     */
    private List<Tag> tags = new ArrayList<Tag>();

    /**
     * Price or income amount depending on type of this item
     */
    private Money money;

    /**
     * @param id Integer key
     */
    public Item(int id) {
        super(id);
    }

    /**
     * @return Name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return URL to the image (may be on the local file system or be a program resource)
     */
    public URL getImageResource() {
        return imageResource;
    }

    /**
     * @param imageResource URL to the image (may be on the local file system or be a program resource)
     */
    public void setImageResource(URL imageResource) {
        this.imageResource = imageResource;
    }

    /**
     * @return Background color of image circle as 4 byte integer
     */
    public int getColor() {
        return color;
    }

    /**
     * @param color Background color of image circle as 4 byte integer
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * @return Tell if and when this item will recur (get added to the history again)
     */
    public Recurrence getRecurrence() {
        return recurrence;
    }

    /**
     * NOTE: null will get converted to {@link NullRecurrence}
     * @param recurrence Tell if and when this item will recur  (get added to the history again)
     */
    public void setRecurrence(Recurrence recurrence) {
        if (recurrence == null) recurrence = new NullRecurrence();
        this.recurrence = recurrence;
    }

    /**
     * @return The list of the Tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
