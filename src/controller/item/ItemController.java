package controller.item;

import controller.list.EntityListController;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import model.entities.IncomeSource;
import model.entities.Item;
import model.entities.Tag;
import utils.ColorUtils;
import utils.DI;

import java.net.MalformedURLException;

public class ItemController {
    @FXML
    Pane mainPanel;

    @FXML
    Pane backgroundPane;

    @FXML
    Label nameLabel;

    @FXML
    Label priceLabel;

    @FXML
    Label recurrenceLabel;

    @FXML
    Pane tagBox;

    private Item model;

    public void setListController(EntityListController<Item> listController)
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

    public void setContent(Item model)
    {
        this.model = model;
        if (model instanceof IncomeSource)
        {
            mainPanel.getStyleClass().remove("expense");
            mainPanel.getStyleClass().add("income");
        }
        else
        {
            mainPanel.getStyleClass().remove("income");
            mainPanel.getStyleClass().add("expense");
        }

        nameLabel.setText(model.getName());

        String urlResource = null;
        if (model.getImageResource() != null) {
            try {
                urlResource = model.getImageResource().toUri().toURL().toExternalForm();
            } catch (MalformedURLException e) {
                // Should not care
            }
        }

        if (urlResource != null)
        {
            backgroundPane.setStyle("-fx-background-color: " + ColorUtils.toHex(model.getColor()) + "; -fx-background-image: url('" + urlResource + "');");
        }
        else
        {
            backgroundPane.setStyle("-fx-background-color: " + ColorUtils.toHex(model.getColor()) + ";");
        }

        priceLabel.setText(model.getMoney().toString());
        recurrenceLabel.setText(model.getRecurrence().toString());

        tagBox.getChildren().clear();

        for (int tagId : model.getTagIds())
        {
            Tag tag = DI.getRepositories().tags.findById(tagId);
            if (tag != null)
            {
                Label label = new Label();
                label.setText(tag.getName());
                label.setStyle("tag-color: " + ColorUtils.toHex(tag.getColor()) + ";");
                label.getStyleClass().add("tag-label");
                tagBox.getChildren().add(label);
            }
        }
    }
}
