package app;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DI;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent items = DI.layouts.getFXMLLoader("items-list.fxml").load();
        primaryStage.setTitle("Pockets 0.0.1");
        primaryStage.setMinWidth(380);
        Scene scene = new Scene(items, 800, 600);
        scene.getStylesheets().add(DI.styles.getResource("Roboto.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("general.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("list.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("scroll-pane.css").toExternalForm());
        scene.getStylesheets().add(DI.styles.getResource("items.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
