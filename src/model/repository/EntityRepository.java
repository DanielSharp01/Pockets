package model.repository;

import com.google.gson.reflect.TypeToken;
import model.Entity;
import utils.DI;

import java.lang.reflect.Type;
import java.util.*;

public abstract class EntityRepository<T extends Entity>
{
    protected Map<Integer, T> entities = new HashMap<>();

    /**
     * Add to the repository (something that did not exist in it before)
     * @param entity Item to add
     * @return True on success, false if item already exists
     */
    public boolean add(T entity) {
        if (entities.containsKey(entity.getId()))
            return false;

        entities.put(entity.getId(), entity);
        return true;
    }

    /**
     * Update an existing item in the repository, finds by id and then updates to match the object specified
     * NOTE: To update IDs you have to first delete and then add back
     * @param entity Item to update
     * @return True on success, false if item already exists
     */
    public boolean update(T entity) {
        if (!entities.containsKey(entity.getId()))
            return false;

        entities.put(entity.getId(), entity);
        return true;
    }

    /**
     * Deletes an existing item from the repository, finds by id and then deletes
     * @param entity Item to delete
     * @return True on success, false if item already exists
     */
    public boolean delete(T entity) {
        if (!entities.containsKey(entity.getId()))
            return false;

        entities.remove(entity.getId());
        return true;
    }

    /**
     * Deletes an existing item by id from the repository, finds by id and then deletes
     * @param id Id of the item
     * @return True on success, false if item already exists
     */
    public boolean delete(int id) {
        if (!entities.containsKey(id))
            return false;

        entities.remove(id);
        return true;
    }

    /**
     * Finds an entity by it's integer id
     * @param id Integer key
     * @return Found entity, null if not found
     */
    public T findById(int id) {
        return entities.get(id);
    }

    /**
     * Serializes this repository into a JSON array
     * @return A JSON array string
     */
    public String serialize()
    {
        return DI.gson.toJson(entities.values().toArray());
    }

    /**
     * Deserializes a JSON string into this repository
     * NOTE: Because Java generics is garbage and uses type erasure this has to be implemented in the concrete classes
     * @param json A JSON array string
     */
    public abstract void deserialize(String json);
}
