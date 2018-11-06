import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ItemsTileController {
    @FXML
    private TilePane itemContents;

    @FXML
    private Pane items;

    private Pane selectedPane = null;

    @FXML
    public void initialize()
    {
        for (int i = 0; i < 50; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("res/layouts/tag.fxml"));

            items.setOnMouseClicked(e ->
            {
                if (selectedPane != null)
                {
                    selectedPane.getStyleClass().remove("selected");
                }
            });

            try {
                Pane pane = loader.load();
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
