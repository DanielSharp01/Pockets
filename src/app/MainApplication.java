package app;

import controller.ItemEditDialogController;
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
        FXMLTuple itemsTile = DI.layouts.getFXMLInflater("item-edit.fxml").inflate();
        //((ItemsTileController)itemsTile.getController()).setRepository(DI.getRepositories().expenses, listView -> new ItemHolder());
        ((ItemEditDialogController)itemsTile.getController()).setModel(DI.getRepositories().expenses.findById(1));
        primaryStage.setOnCloseRequest(e ->
        {
            if (!((ItemEditDialogController)itemsTile.getController()).tryCancel())
                e.consume();
        });

        primaryStage.setTitle("Pockets 0.0.1");
        primaryStage.setMinWidth(380);
        Scene scene = new Scene(itemsTile.getRoot(), 380, 700);
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
