import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ItemsTileController {
    @FXML
    private TilePane itemContents;

    @FXML
    private VBox items;

    private AnchorPane selectedPane = null;

    @FXML
    public void initialize()
    {
        for (int i = 0; i < 50; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("res/layouts/item.fxml"));

            items.setOnMouseClicked(e ->
            {
                if (selectedPane != null)
                {
                    selectedPane.getStyleClass().remove("selected");
                }
            });

            try {
                AnchorPane pane = loader.load();
                pane.setOnMouseClicked(e -> {
                    if (selectedPane != null)
                    {
                        selectedPane.getStyleClass().remove("selected");
                    }
                    pane.getStyleClass().add("selected");
                    selectedPane = pane;
                    e.consume();
                });
                itemContents.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
