package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.TilePane;
import javafx.util.Callback;
import model.repository.EntityRepository;
import view.TileListView;
import view.ViewHolder;

public class ItemsTileController {
    @FXML
    private TilePane itemContents;
    private TileListView listView;

    @FXML
    public void initialize()
    {
        listView = new TileListView(itemContents);
    }

    public void setRepository(EntityRepository repository, Callback<TileListView, ViewHolder> holderFactory)
    {
        listView.setItems(repository.asObservable());
        listView.setFactory(holderFactory);
    }
}
