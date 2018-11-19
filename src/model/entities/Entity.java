package model.entities;

import model.filters.IFilterable;

/**
 * Entity with an integer key
 */
public abstract class Entity implements IFilterable {
    /**
     * Integer key
     */
    private int id;

    /**
     * @param id Integer key
     */
    public Entity(int id) {
        this.id = id;
    }

    /**
     * @return  Integer key
     */
    public int getId() {
        return id;
    }

    /**
     * @param id Integer key
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Entity)) return false;
        if (obj == this) return true;
        return this.getId() == ((Entity) obj).getId();
    }
}
