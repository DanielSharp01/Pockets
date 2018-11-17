package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.entities.Tag;
import utils.ColorUtils;

public class TagComboItem extends ListCell<Tag>
{
    private Pane imagePane;
    private Label label;
    private HBox hbox;

    @Override
    protected void updateItem(Tag item, boolean empty)
    {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        }
        else
        {
            hbox = new HBox();
            hbox.setSpacing(6);
            imagePane = new Pane();
            imagePane.setPrefWidth(16);
            imagePane.setPrefHeight(16);
            imagePane.getStyleClass().add("combo-image");
            label = new Label();
            label.setAlignment(Pos.CENTER_LEFT);
            hbox.getChildren().add(imagePane);
            hbox.getChildren().add(label);
            imagePane.setStyle("-fx-background-color: " + ColorUtils.toHex(item.getColor()) + ";");
            label.setText(item.getName());

            setGraphic(hbox);
        }
    }
}
