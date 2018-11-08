package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.items.HistoryItem;
import view.HistoryCell;

public class ItemsListController {
    @FXML
    private ListView itemContents;

    @FXML
    public void initialize()
    {
        itemContents.setCellFactory((Callback<ListView<HistoryItem>, HistoryCell>) listView -> new HistoryCell());

        for (int i = 0; i < 50; i++) {
            itemContents.getItems().add(new HistoryItem());
        }
    }
}
