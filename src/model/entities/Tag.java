package model.entities;

import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Tag with which you can tag Items
 */
public class Tag extends Entity implements Cloneable {
    /**
     * Name of the tag
     */
    private String name = "";

    /**
     * Background color of the tag
     */
    private Color color = Color.GRAY;

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
     * @return Background color of the tag
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color Background color of the tag
     */
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Collection<String> getWords()
    {
        Set<String> words = new HashSet<>();
        Collections.addAll(words, name.split(" "));
        return words;
    }

    @Override
    public Tag clone()
    {
        Tag c = new Tag(getId());
        c.setName(getName());
        c.setColor(getColor());
        return c;
    }
}
