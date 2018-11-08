package model;

/**
 * Represents a Tag with which you can tag Items
 */
public class Tag extends Entity {
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
}
