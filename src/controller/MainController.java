package controller;

import controller.list.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import utils.DI;
import view.FXMLTuple;

/**
 * Controller for the main window
 */
public class MainController {

    /**
     * Sub scenes that are possible in the main scene (on the right)
     */
    private enum Scenes
    {
        History,
        Income,
        Expense,
        Tag,
        Settings
    }

    @FXML
    private Pane scenePane;
    @FXML
    private Label historyMenuLabel;
    @FXML
    private Label incomeMenuLabel;
    @FXML
    private Label expenseMenuLabel;
    @FXML
    private Label tagMenuLabel;
    @FXML
    private Label settingsMenuLabel;

    /**
     * Currently selected sub scene
     */
    private Scenes currentScene;

    /**
     * Selected label (has the selected class)
     */
    private Label selectedLabel;

    @FXML
    private void initialize()
    {
        FXMLTuple fxml = DI.layouts.getFXMLInflater("items-list.fxml").inflate();
        ((ListController)fxml.getController()).setEntityListController(new HistoryListController());
        VBox.setVgrow(fxml.getRoot(), Priority.ALWAYS);
        scenePane.getChildren().add(fxml.getRoot());
        currentScene = Scenes.History;
        historyMenuLabel.getStyleClass().add("selected");
        selectedLabel = historyMenuLabel;
    }

    public void historyMenuClicked(MouseEvent e) {
        if (currentScene != Scenes.History)
        {
            scenePane.getChildren().clear();
            FXMLTuple fxml = DI.layouts.getFXMLInflater("items-list.fxml").inflate();
            ((ListController)fxml.getController()).setEntityListController(new HistoryListController());
            VBox.setVgrow(fxml.getRoot(), Priority.ALWAYS);
            scenePane.getChildren().add(fxml.getRoot());
            currentScene = Scenes.History;
            selectedLabel.getStyleClass().remove("selected");
            historyMenuLabel.getStyleClass().add("selected");
            selectedLabel = historyMenuLabel;
        }
    }

    public void incomeMenuClicked(MouseEvent e) {
        if (currentScene != Scenes.Income)
        {
            scenePane.getChildren().clear();
            FXMLTuple fxml = DI.layouts.getFXMLInflater("items-tile.fxml").inflate();
            ((TileController)fxml.getController()).setEntityListController(new IncomeListController());
            VBox.setVgrow(fxml.getRoot(), Priority.ALWAYS);
            scenePane.getChildren().add(fxml.getRoot());
            currentScene = Scenes.Income;
            selectedLabel.getStyleClass().remove("selected");
            incomeMenuLabel.getStyleClass().add("selected");
            selectedLabel = incomeMenuLabel;
        }
    }

    public void expenseMenuClicked(MouseEvent e) {
        if (currentScene != Scenes.Expense)
        {
            scenePane.getChildren().clear();
            FXMLTuple fxml = DI.layouts.getFXMLInflater("items-tile.fxml").inflate();
            ((TileController)fxml.getController()).setEntityListController(new ExpenseListController());
            VBox.setVgrow(fxml.getRoot(), Priority.ALWAYS);
            scenePane.getChildren().add(fxml.getRoot());
            currentScene = Scenes.Expense;
            selectedLabel.getStyleClass().remove("selected");
            expenseMenuLabel.getStyleClass().add("selected");
            selectedLabel = expenseMenuLabel;
        }
    }

    public void tagMenuClicked(MouseEvent e) {
        if (currentScene != Scenes.Tag)
        {
            scenePane.getChildren().clear();
            FXMLTuple fxml = DI.layouts.getFXMLInflater("items-tile.fxml").inflate();
            ((TileController)fxml.getController()).setEntityListController(new TagListController());
            VBox.setVgrow(fxml.getRoot(), Priority.ALWAYS);
            scenePane.getChildren().add(fxml.getRoot());
            currentScene = Scenes.Tag;
            selectedLabel.getStyleClass().remove("selected");
            tagMenuLabel.getStyleClass().add("selected");
            selectedLabel = tagMenuLabel;
        }
    }

    public void settingsMenuClicked(MouseEvent e) {
        if (currentScene != Scenes.Settings)
        {
            scenePane.getChildren().clear();
            FXMLTuple fxml = DI.layouts.getFXMLInflater("settings.fxml").inflate();
            VBox.setVgrow(fxml.getRoot(), Priority.ALWAYS);
            scenePane.getChildren().add(fxml.getRoot());
            currentScene = Scenes.Settings;
            selectedLabel.getStyleClass().remove("selected");
            settingsMenuLabel.getStyleClass().add("selected");
            selectedLabel = settingsMenuLabel;
        }
    }
}
