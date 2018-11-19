package controller.list;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ListController {
    @FXML
    private ListView itemContents;

    @FXML
    private TextField filterField;

    /**
     * Entity list controller which supplies the list and manages communication with the repository
     */
    private EntityListController listController;

    @FXML
    public void initialize()
    {
        filterField.textProperty().addListener((observable, oldValue, newValue) -> listController.filterTextChanged(newValue));
    }

    /**
     * @param listController Entity list controller which supplies the list and manages communication with the repository
     */
    public void setEntityListController(EntityListController listController)
    {
        this.listController = listController;
        itemContents.setItems(listController.getFilteredList());
        itemContents.setCellFactory(listController.getCellFactory());
    }

    @FXML
    private void addButtonActionPerformed(ActionEvent e) {
        listController.newEntity();
    }
}
