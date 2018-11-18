package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.entities.IncomeSource;
import model.entities.Item;
import model.entities.Tag;
import utils.ColorUtils;
import utils.DI;

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

    public void setContent(Item model)
    {
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
        if (model.getImageResource() != null)
        {
            backgroundPane.setStyle("-fx-background-color: " + ColorUtils.toHex(model.getColor()) + "; -fx-background-image: url('" + model.getImageResource().toExternalForm() + "');");
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
                label.setStyle("-fx-background-color: " + ColorUtils.toHex(tag.getColor()) + ";");
                label.getStyleClass().add("tag-label");
                tagBox.getChildren().add(label);
            }
        }
    }
}