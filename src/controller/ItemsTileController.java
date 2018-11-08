package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

import java.io.IOException;

public class ItemsTileController {
    @FXML
    private TilePane itemContents;

    @FXML
    public void initialize()
    {
        for (int i = 0; i < 50; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../res/layouts/item.fxml"));
            try {
                Pane pane = loader.load();
                itemContents.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
