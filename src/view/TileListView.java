package view;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.TilePane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Turns an ordinary {@link TilePane} into a list view
 */
public class TileListView {
    /**
     * TilePane whose children are the items
     */
    private TilePane contents;

    /**
     * Model items to show
     */
    private ObservableList items;

    /**
     * Change listener currently used on the items
     */
    private ListChangeListener listener;

    /**
     * TileList holders that show model items
     */
    private List<ViewHolder> holders = new ArrayList<>();

    /**
     * Factory method that creates TileListCells
     */
    private Callback<TileListView, ViewHolder> factory = listView -> new DefaultViewHolder();

    /**
     * @param content TilePane whose children are the items
     */
    public TileListView(TilePane content)
    {
        this.contents = content;
    }

    /**
     * @param factory Factory method that creates TileListCells
     */
    public void setFactory(Callback<TileListView, ViewHolder> factory) {
        this.factory = factory == null ? listView -> new DefaultViewHolder() : factory;

        this.contents.getChildren().clear();
        this.holders.clear();

        if (this.items != null)
        {
            for (Object item : items)
            {
                ViewHolder holder = factory.call(this);
                holder.update(item);
                holders.add(holder);
                contents.getChildren().add(holder.getNode());
            }
        }
    }

    /**
     * @return Model items to show
     */
    public ObservableList getItems() {
        return items;
    }

    /**
     * @param items Model items to show
     */
    public void setItems(ObservableList items)
    {
        if (this.items != null)
        {
            this.items.removeListener(listener);
        }

        this.contents.getChildren().clear();
        this.holders.clear();

        if (items == null) return;

        this.items = items;

        // Reconstruct list
        setFactory(factory);

        listener = c -> {
            if (c.wasAdded())
            {
              ViewHolder holder = factory.call(this);
              holder.update(c.getList().get(c.getFrom()));
              holders.add(c.getFrom(), holder);
              contents.getChildren().add(c.getFrom(), holder.getNode());
            }
            else if (c.wasRemoved())
            {
              holders.remove(c.getFrom());
              contents.getChildren().remove(c.getFrom());
            }
            else if (c.wasUpdated())
            {
              holders.get(c.getFrom()).update(c.getList().get(c.getFrom()));
            }
        };
        this.items.addListener(listener);
    }
}
