package model;

import model.filters.IFilterable;
import utils.DI;

import java.net.URL;
import java.util.*;

/**
 * Abstract Item class which has the properties of both Expenses and Income sources
 * @see ExpenseItem
 * @see IncomeItem
 */
public abstract class Item extends Entity implements IFilterable {
    /**
     * Name of the item
     */
    private String name;

    /**
     * URL to the image (may be on the local file system or be a program resource)
     */
    private URL imageResource;

    /**
     * Background color of image circle as 4 byte integer RGBUnused
     */
    private int color;

    /**
     * Tell if and when this item will recur
     */
    private Recurrence recurrence;

    /**
     * The list of the Tag ids
     */
    private List<Integer> tagIds = new ArrayList<>();

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
     * @return Background color of image circle as 4 byte integer RGBUnused
     */
    public int getColor() {
        return color;
    }

    /**
     * @param color Background color of image circle as 4 byte integer RGBUnused
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
     * @return Price or income amount depending on type of this item
     */
    public Money getMoney() {
        return money;
    }

    /**
     * @param money Price or income amount depending on type of this item
     */
    public void setMoney(Money money) {
        this.money = money;
    }

    /**
     * @return The list of the Tag ids
     */
    public List<Integer> getTagIds() {
        return tagIds;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public Collection<String> getWords()
    {
        Set<String> words = new HashSet<>();
        Collections.addAll(words, name.split(" "));
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
}
