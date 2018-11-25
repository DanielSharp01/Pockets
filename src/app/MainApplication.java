package app;

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

        FXMLTuple mainLayout = DI.layouts.getFXMLInflater("main-layout.fxml").inflate();
        primaryStage.setTitle("Pockets 1.0");
        primaryStage.setMinWidth(700);
        Scene scene = SceneFactory.getScene(mainLayout.getRoot(), 1600, 900);
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
