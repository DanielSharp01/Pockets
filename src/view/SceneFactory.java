package view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import utils.DI;

/**
 * Convenience factory for scenes that adds all the stylesheets to them
 */
public class SceneFactory {

    /**
     * Creates a scene with all the stylesheets attached
     * @param root Root node of the scene
     * @param width Width of the scene
     * @param height Height of the scene
     * @return Scene with all the stylesheets attached
     */
    public static Scene getScene(Parent root, double width, double height)
    {
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(DI.styles.getResource("Roboto.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("general.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("main-panel.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("dialog.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("list.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("scroll-pane.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("items.css").toExternalForm());
        return scene;
    }
}
