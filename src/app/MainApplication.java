package app;

import controller.list.TagListController;
import controller.list.TileController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DI;
import view.Dialogs;
import view.FXMLTuple;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        if (!Settings.load() || !DI.getRepositories().load())
        {
            Dialogs.showErrorOk("Fatal error!",
                    "One or more json files can't be loaded! The program is now going to exit.");
            return;
        }
        FXMLTuple itemList = DI.layouts.getFXMLInflater("items-tile.fxml").inflate();

        ((TileController)itemList.getController()).setEntityListController(new TagListController());

        primaryStage.setTitle("Pockets 0.0.1");
        primaryStage.setMinWidth(450);
        Scene scene = new Scene(itemList.getRoot(), 1600, 900);
        scene.getStylesheets().add(DI.styles.getResource("Roboto.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("general.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("dialog.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("list.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("scroll-pane.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("items.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnHidden(e ->
        {
            if (!DI.getRepositories().save() | !Settings.getInstance().save())
            {
                Dialogs.showErrorOk("Fatal error!", "Could not save changes to the file system!");
            }
        });
    }


    public static void main(String[] args) {

        launch(args);
    }
}
