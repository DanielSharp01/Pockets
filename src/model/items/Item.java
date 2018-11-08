package model.items;

import model.recurrences.Recurrence;

import java.net.URL;

/**
 * Abstract Item class which has the properties of both Expenses and Income sources
 * /@see ExpenseItem
 * /@see IncomeItem
 */
public abstract class Item {

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
     * Tell if and when this item will recur (null means no recurrences)
     */
    private Recurrence recurrence;


    protected Item(String name, URL imageResource, int color, Recurrence recurrence) {
        this.name = name;
        this.imageResource = imageResource;
        this.color = color;
        this.recurrence = recurrence;
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
     * @return Tell if and when this item will recur (null means no recurrences)
     */
    public Recurrence getRecurrence() {
        return recurrence;
    }

    /**
     * @param recurrence Tell if and when this item will recur (null means no recurrences)
     */
    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }
}
