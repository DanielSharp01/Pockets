package app;

import controller.list.HistoryListController;
import controller.list.IncomeListController;
import controller.list.ListController;
import controller.list.TileController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.RecurrenceLogic;
import utils.DI;
import view.Dialogs;
import view.FXMLTuple;
import view.SceneFactory;

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

        RecurrenceLogic.setupTimedRecurrenceChecks();

        FXMLTuple itemList = DI.layouts.getFXMLInflater("items-list.fxml").inflate();
        ((ListController)itemList.getController()).setEntityListController(new HistoryListController());
        FXMLTuple itemTile = DI.layouts.getFXMLInflater("items-tile.fxml").inflate();
        ((TileController)itemTile.getController()).setEntityListController(new IncomeListController());
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
