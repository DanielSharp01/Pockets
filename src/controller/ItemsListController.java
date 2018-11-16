package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.entities.HistoryEntry;
import utils.DI;
import view.HistoryCell;

public class ItemsListController {
    @FXML
    private ListView itemContents;

    @FXML
    public void initialize()
    {
        itemContents.setCellFactory((Callback<ListView<HistoryEntry>, HistoryCell>) listView -> new HistoryCell());
        itemContents.setItems(DI.getRepositories().history.asObservable());

    }
}
