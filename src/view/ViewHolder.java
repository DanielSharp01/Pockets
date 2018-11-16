package view;

import javafx.scene.Node;

/**
 * Updatable node
 * @param <T> Model type
 */
public abstract class ViewHolder<T> {
    /**
     * Update this Node with a model
     * @param model Model to update with
     */
    public abstract void update(T model);

    /**
     * @return Node associated with this ViewHolder
     */
    public abstract Node getNode();
}
