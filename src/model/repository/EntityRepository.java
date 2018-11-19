package model.repository;

import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import model.entities.Entity;
import utils.DI;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

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
     * Next id for an added entity
     */
    private int autoIncrement = 1;

    /**
     * @param container Parent repository container, used by repositories accessing other repositories
     */
    public EntityRepository(RepositoryContainer container)
    {
        this.container = container;
    }

    /**
     * Add to the repository (something that did not exist in it before)
     * NOTE: If the Entity id is 0, this method generates a new auto-incremented id
     * @param entity Entity to add
     * @return True on success, false if Entity already exists
     */
    public boolean add(T entity) {
        if (entities.containsKey(entity.getId()))
            return false;

        if (entity.getId() == 0)
            entity.setId(autoIncrement++);

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
     * @return Next id for an added entity
     */
    public int getAutoIncrement() {
        return autoIncrement;
    }

    /**
     * Serializes this repository into a JSON array
     * @return A JSON array string
     */
    public String serialize()
    {
        JsonObject object = new JsonObject();
        JsonElement array = DI.gson.toJsonTree(entities.values().toArray());
        object.add("autoIncrement", new JsonPrimitive(autoIncrement));
        object.add("entities", array);
        return object.toString();
    }

    /**
     * Deserializes a JSON into this repository
     */
    public void deserialize(String json)
    {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        autoIncrement = object.get("autoIncrement").getAsInt();
        for (T entity : deserializeArray(object.getAsJsonArray("entities")))
        {
            add(entity);
        }
    }

    /**
     * Returns an entity array from a JsonArray
     * NOTE: Because Java generics is garbage and uses type erasure this has to be implemented in the concrete classes
     * @param array JsonArray
     * @return Entity array which will be added to entities
     */
    protected abstract T[] deserializeArray(JsonArray array);

    @Override
    public Iterator<T> iterator()
    {
        return entities.values().iterator();
    }

    /**
     * @return The entities in an observable list
     */
    public ObservableList<T> asObservableList()
    {
        return observableList;
    }

    /**
     * @return The entities in an filtered observable list (with match all predicate as default)
     */
    public FilteredList<T> asFilteredList()
    {
        return new FilteredList<>(observableList, s -> true);
    }
}
