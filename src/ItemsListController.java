import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ItemsListController {
    @FXML
    private ListView itemContents;

    @FXML
    public void initialize()
    {
        for (int i = 0; i < 50; i++) {
            /*FXMLLoader loader = new FXMLLoader(getClass().getResource("res/layouts/history-item.fxml"));
            try {
                Pane pane = loader.load();*/
                itemContents.getItems().add("LOL");
            /*} catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }
}
