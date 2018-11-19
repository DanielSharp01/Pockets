package controller.list;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import view.TileListView;

public class TileController {
    @FXML
    private TilePane itemContents;
    private TileListView listView;

    @FXML
    private TextField filterField;

    /**
     * Entity list controller which supplies the list and manages communication with the repository
     */
    private EntityListController listController;

    @FXML
    public void initialize()
    {
        listView = new TileListView(itemContents);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> listController.filterTextChanged(newValue));
    }

    /**
     * @param listController Entity list controller which supplies the list and manages communication with the repository
     */
    public void setEntityListController(EntityListController listController)
    {
        this.listController = listController;
        listView.setItems(listController.getFilteredList());
        listView.setFactory(listController.getHolderFactory());
    }

    @FXML
    private void addButtonActionPerformed(ActionEvent e) {
        listController.newEntity();
    }
}
