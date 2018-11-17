package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.nio.file.Paths;

public class ImageComboItem extends ListCell<URL>
{
    private Pane imagePane;
    private Label label ;
    private HBox hbox;

    @Override
    protected void updateItem(URL item, boolean empty) {
        super.updateItem(item, empty);

        if (empty)
        {
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

            if (item != null)
            {
                imagePane.setStyle("-fx-background-imagePane: url('" + item.toExternalForm() + "');");
            }
            else
            {
                imagePane.setStyle("-fx-background-color: #aaa;");
            }

            label.setText(item == null ? "None" : Paths.get(item.toExternalForm()).getFileName().toString().split(".", 2)[0]);
            setGraphic(hbox);
        }
    }
}
