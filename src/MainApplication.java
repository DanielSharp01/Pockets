import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent items = FXMLLoader.load(getClass().getResource("res/layouts/items-tile.fxml"));
        primaryStage.setTitle("Pockets 0.0.1");
        primaryStage.setMinWidth(380);
        Scene scene = new Scene(items, 800, 600);
        scene.getStylesheets().add(getClass().getResource("res/Roboto/Roboto.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("res/styles/general.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("res/styles/card.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("res/styles/items.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
