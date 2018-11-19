package view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import utils.DI;

public class SceneFactory {
    public static Scene getScene(Parent root, double width, double height)
    {
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(DI.styles.getResource("Roboto.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("general.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("dialog.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("list.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("scroll-pane.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("items.css").toExternalForm());
        return scene;
    }
}
