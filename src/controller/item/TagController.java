package controller.item;

import controller.list.EntityListController;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import model.entities.Tag;
import utils.ColorUtils;

/**
 * Controller for {@link view.TagHolder}'s content
 */
public class TagController {
    @FXML
    Pane mainPanel;

    @FXML
    Label nameLabel;

    /**
     * Model of the controller
     */
    private Tag model;

    /**
     * Sets the parent lists's controller, used for context menus
     * @param listController The parent lists's controller
     */
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

    /**
     * @param model Model of the controller
     */
    public void setModel(Tag model)
    {
        this.model = model;
        mainPanel.setStyle("tag-color: " + ColorUtils.toHex(model.getColor()) + ";");
        nameLabel.setText(model.getName());
    }
}
