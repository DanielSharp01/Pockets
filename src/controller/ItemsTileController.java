package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import utils.DI;
import utils.FXMLInflater;

import java.io.IOException;

public class ItemsTileController {
    @FXML
    private TilePane itemContents;

    @FXML
    public void initialize()
    {
        FXMLInflater inflater = DI.layouts.getFXMLInflater("item.fxml");
        for (int i = 0; i < 50; i++) {
            itemContents.getChildren().add(inflater.inflate());
        }
    }
}
