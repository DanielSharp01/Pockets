package model.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entities.Entity;
import utils.DI;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public abstract class EntityRepository<T extends Entity> implements Iterable<T> {
    /**
     * Parent repository container, used by repositories accessing other repositories
     */
    protected RepositoryContainer container;

    /**
     * Map of id -> Entity
     */
    protected Map<Integer, T> entities = new LinkedHashMap<>();

    /**
     * Observable list used by JavaFX
     */
    private ObservableList<T> observableList = FXCollections.observableArrayList();

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
        observableList.add(entity);
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

        for (int i = 0; i < observableList.size(); i++)
        {
            if (observableList.get(i).getId() == entity.getId()) {
                observableList.set(i, entity);
                break;
            }
        }

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
        observableList.remove(entity);
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

        T entity = entities.remove(id);
        observableList.remove(entity);
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

    @Override
    public Iterator<T> iterator()
    {
        return entities.values().iterator();
    }

    public ObservableList<T> asObservable()
    {
        return observableList;
    }
}
