package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;

public class ImageComboItem extends ListCell<URL>
{
    private Pane imagePane;
    private Label label ;
    private HBox hbox;

    @Override
    protected void updateItem(URL item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null)
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

            if (!item.toString().equals("mailto:null@null"))
            {
                imagePane.setStyle("-fx-background-image: url('" + item.toExternalForm() + "');");
                String[] urlParts = item.toExternalForm().split("\\/");
                label.setText(urlParts[urlParts.length - 1].split("\\.")[0]);
            }
            else
            {
                label.setText("None");
                imagePane.setStyle("-fx-background-color: #aaa;");
            }
            setGraphic(hbox);
        }
    }
}
