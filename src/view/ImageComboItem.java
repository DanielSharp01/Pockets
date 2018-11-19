package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.MalformedURLException;
import java.nio.file.Path;

public class ImageComboItem extends ListCell<Path>
{
    private Pane imagePane;
    private Label label ;
    private HBox hbox;

    @Override
    protected void updateItem(Path item, boolean empty) {
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

            if (item.toString().equals(".NULL."))
            {
                label.setText("No image");
            }
            else
            {

                String urlResource = null;
                try {
                    urlResource = item.toUri().toURL().toExternalForm();
                } catch (MalformedURLException e) {
                    // Should not care
                }

                if (urlResource != null) {
                    imagePane.setStyle("-fx-background-image: url('" + urlResource + "');");
                }

                label.setText(item.getFileName().toString());
            }

            setGraphic(hbox);
        }
    }
}
