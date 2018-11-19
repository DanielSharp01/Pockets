package controller.item;

import controller.list.EntityListController;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import model.entities.Tag;
import utils.ColorUtils;

public class TagController {
    @FXML
    Pane mainPanel;

    @FXML
    Label nameLabel;

    private Tag model;

    public void setListController(EntityListController<Tag> listController)
    {
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(e -> listController.edit(model));
        contextMenu.getItems().add(edit);
        MenuItem delete = new MenuItem("Delete");
        delete.setOnAction(e -> listController.delete(model));
        contextMenu.getItems().add(delete);

        mainPanel.setOnContextMenuRequested(e -> contextMenu.show(mainPanel, e.getScreenX(), e.getScreenY()));
    }

    public void setContent(Tag model)
    {
        this.model = model;
        mainPanel.setStyle("-fx-background-color: " + ColorUtils.toHex(model.getColor()) + ";");
        nameLabel.setText(model.getName());
    }
}
