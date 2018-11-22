package app;

import controller.list.ExpenseListController;
import controller.list.TagListController;
import controller.list.TileController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.CurrencyConverter;
import utils.DI;
import view.Dialogs;
import view.FXMLTuple;
import view.SceneFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) {

        if (!Settings.load() || !DI.getRepositories().load())
        {
            Dialogs.showErrorOk("Fatal error!",
                    "One or more json files can't be loaded! The program is now going to exit.");
            return;
        }

        DI.currencyConverter.requestAPI(!Settings.getInstance().areUsingApi());

        FXMLTuple itemList = DI.layouts.getFXMLInflater("items-tile.fxml").inflate();
        ((TileController)itemList.getController()).setEntityListController(new ExpenseListController());
        FXMLTuple settings = DI.layouts.getFXMLInflater("settings.fxml").inflate();

        primaryStage.setTitle("Pockets 0.0.2");
        primaryStage.setMinWidth(450);
        Scene scene = SceneFactory.getScene(itemList.getRoot(), 1600, 900);
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
