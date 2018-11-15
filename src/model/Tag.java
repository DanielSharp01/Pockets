package model;

import model.filters.IFilter;
import model.filters.IFilterable;
import utils.DI;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Tag with which you can tag Items
 */
public class Tag extends Entity implements IFilterable {
    /**
     * Name of the tag
     */
    private String name;

    /**
     * Background color of the tag as 4 byte integer
     */
    private int color;

    /**
     * @param id Integer key
     */
    public Tag(int id) {
        super(id);
    }

    /**
     * @return Name of the tag
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Name of the tag
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Background color of the tag as 4 byte integer
     */
    public int getColor() {
        return color;
    }

    /**
     * @param color Background color of the tag as 4 byte integer
     */
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public Collection<String> getWords()
    {
        Set<String> words = new HashSet<>();
        Collections.addAll(words, name.split(" "));
        return words;
    }
}
