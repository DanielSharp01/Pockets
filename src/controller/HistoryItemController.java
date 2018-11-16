package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.entities.HistoryEntry;
import model.entities.Item;
import model.entities.Tag;
import utils.DI;

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
        if (item.getImageResource() != null)
        {
            backgroundPane.setStyle("-fx-background-color: " + String.format("#%06X", (0xFFFFFF & item.getColor())) + "; -fx-background-image: url('" + item.getImageResource().toExternalForm() + "');");
        }
        else
        {
            backgroundPane.setStyle("-fx-background-color: " + String.format("#%06X", (0xFFFFFF & item.getColor())) + ";");
        }

        priceLabel.setText((model.getItemType() == HistoryEntry.Type.Income ? "+" : "-") + item.getMoney().toString());
        dateLabel.setText(model.getDate().format(DI.defaultDateTimeFormatter));

        tagBox.getChildren().clear();

        for (int tagId : item.getTagIds())
        {
            Tag tag = DI.getRepositories().tags.findById(tagId);
            if (tag != null)
            {
                Label label = new Label();
                label.setText(tag.getName());
                label.setStyle("-fx-background-color: " + String.format("#%06X", (0xFFFFFF & tag.getColor())) + ";");
                label.getStyleClass().add("tag-label");
                tagBox.getChildren().add(label);
            }
        }
    }
}
