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

        contents.getChildren().clear();
        holders.clear();

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
            while (c.next()) {
                if (c.wasRemoved())
                {
                    // All bets are off, we can't get index information of removed items -> regenerate the whole list

                    contents.getChildren().clear();
                    holders.clear();
                    for (Object item : c.getList())
                    {
                        ViewHolder holder = factory.call(this);
                        holder.update(item);
                        holders.add(holder);
                        contents.getChildren().add(holder.getNode());
                    }
                    break; // Don't look at any other changes
                }
                else if (c.wasAdded())
                {
                    int index = c.getFrom();
                    for (Object item : c.getAddedSubList())
                    {
                        ViewHolder holder = factory.call(this);
                        holder.update(item);
                        holders.add(index, holder);
                        contents.getChildren().add(index, holder.getNode());
                        index++;
                    }

                }
                else if (c.wasUpdated())
                {
                    for (int i = c.getFrom(); i < c.getTo(); i++)
                    {
                        holders.get(i).update(c.getList().get(i));
                    }
                }
            }
        };
        this.items.addListener(listener);
    }
}
