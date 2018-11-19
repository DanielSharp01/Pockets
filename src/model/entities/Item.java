package model.entities;

import javafx.scene.paint.Color;
import model.Money;
import model.NullRecurrence;
import model.Recurrence;
import model.filters.IFilterable;
import utils.DI;

import java.net.URL;
import java.nio.file.Path;
import java.util.*;

/**
 * Abstract Item class which has the properties of both Expenses and Income sources
 * @see ExpenseItem
 * @see IncomeSource
 */
public abstract class Item extends Entity implements IFilterable, Cloneable {
    /**
     * Name of the item
     */
    private String name;

    /**
     * Path to the image (may be on the local file system or be a program resource)
     */
    private Path imageResource;

    /**
     * Background color of image circle
     */
    private Color color;

    /**
     * Tell if and when this item will recur
     */
    private Recurrence recurrence = new NullRecurrence();

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
     * @return Path to the image (may be on the local file system or be a program resource)
     */
    public Path getImageResource() {
        return imageResource;
    }

    /**
     * @param imageResource Path to the image (may be on the local file system or be a program resource)
     */
    public void setImageResource(Path imageResource) {
        this.imageResource = imageResource;
    }

    /**
     * @return Background color of image circle
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color Background color of image circle
     */
    public void setColor(Color color) {
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
    public abstract Item clone();

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
