package controller.item;

import app.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.entities.HistoryEntry;
import model.entities.Item;
import model.entities.Tag;
import utils.ColorUtils;
import utils.DI;

import java.net.MalformedURLException;
import java.util.LinkedHashSet;
import java.util.Set;

public class HistoryItemController {
    @FXML
    Pane mainPanel;

    @FXML
    Pane backgroundPane;

    @FXML
    Label nameLabel;

    @FXML
    Label priceLabel;

    @FXML
    Label dateLabel;

    @FXML
    Pane tagBox;

    public void setContent(HistoryEntry model)
    {
        Item item = model.getItem();

        if (model.getItemType() == HistoryEntry.Type.Income)
        {
            mainPanel.getStyleClass().remove("expense");
            mainPanel.getStyleClass().add("income");
        }
        else
        {
            mainPanel.getStyleClass().remove("income");
            mainPanel.getStyleClass().add("expense");
        }

        nameLabel.setText(item.getName());

        String urlResource = null;
        if (model.getItem() != null && model.getItem().getImageResource() != null)
        {
            try {
                urlResource = model.getItem().getImageResource().toUri().toURL().toExternalForm();
            } catch (MalformedURLException e) {
                // Should not care
            }
        }

        if (urlResource != null)
        {
            backgroundPane.setStyle("-fx-background-color: " + ColorUtils.toHex(model.getItem().getColor()) + "; -fx-background-image: url('" + urlResource + "');");
        }
        else
        {
            backgroundPane.setStyle("-fx-background-color: " + ColorUtils.toHex(model.getItem().getColor()) + ";");
        }

        priceLabel.setText((model.getItemType() == HistoryEntry.Type.Income ? "+" : "-") + item.getMoney().toString());
        dateLabel.setText(model.getDate().format(Settings.getInstance().getDateTimeFormatter()));

        tagBox.getChildren().clear();

        Set<Integer> tagIds = new LinkedHashSet<>();
        tagIds.addAll(item.getTagIds());
        tagIds.addAll(model.getTagIds());

        for (int tagId : tagIds)
        {
            Tag tag = DI.getRepositories().tags.findById(tagId);
            if (tag != null)
            {
                Label label = new Label();
                label.setText(tag.getName());
                label.setStyle("-fx-background-color: " + ColorUtils.toHex(tag.getColor()) + ";");
                label.getStyleClass().add("tag-label");
                tagBox.getChildren().add(label);
            }
        }
    }
}
