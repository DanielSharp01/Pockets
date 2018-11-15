package model.repository;

import model.Entity;
import utils.DI;

import java.util.HashMap;
import java.util.Map;
public abstract class EntityRepository<T extends Entity>
{
    /**
     * Parent repository container, used by repositories accessing other repositories
     */
    protected RepositoryContainer container;

    /**
     * Map of id -> Entity
     */
    protected Map<Integer, T> entities = new HashMap<>();

    /**
     * @param container Parent repository container, used by repositories accessing other repositories
     */
    public EntityRepository(RepositoryContainer container)
    {
        this.container = container;
    }

    /**
     * Add to the repository (something that did not exist in it before)
     * @param entity Entity to add
     * @return True on success, false if Entity already exists
     */
    public boolean add(T entity) {
        if (entities.containsKey(entity.getId()))
            return false;

        entities.put(entity.getId(), entity);
        return true;
    }

    /**
     * Update an existing Entity in the repository, finds by id and then updates to match the object specified
     * NOTE: To update IDs you have to first delete and then add back
     * @param entity Entity to update
     * @return True on success, false if Entity already exists
     */
    public boolean update(T entity) {
        if (!entities.containsKey(entity.getId()))
            return false;

        entities.put(entity.getId(), entity);
        return true;
    }

    /**
     * Deletes an existing Entity from the repository, finds by id and then deletes
     * @param entity Entity to delete
     * @return True on success, false if Entity already exists
     */
    public boolean delete(T entity) {
        if (!entities.containsKey(entity.getId()))
            return false;

        entities.remove(entity.getId());
        return true;
    }

    /**
     * Deletes an existing Entity by id from the repository, finds by id and then deletes
     * @param id Id of the Entity
     * @return True on success, false if Entity already exists
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
