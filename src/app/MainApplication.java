package app;

import controller.ItemsTileController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Money;
import model.entities.ExpenseItem;
import model.entities.HistoryEntry;
import model.entities.IncomeSource;
import model.entities.Tag;
import utils.DI;
import view.FXMLTuple;
import view.ItemHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Tag tag = new Tag(1);
        tag.setName("Food");
        tag.setColor(0xFF8F00);
        DI.getRepositories().tags.add(tag);
        tag = new Tag(2);
        tag.setName("Drink");
        tag.setColor(0xFF7043);
        DI.getRepositories().tags.add(tag);
        tag = new Tag(3);
        tag.setName("Recurrent expenses");
        tag.setColor(0xA1887F);
        DI.getRepositories().tags.add(tag);
        tag = new Tag(4);
        tag.setName("University");
        tag.setColor(0xFF7043);
        DI.getRepositories().tags.add(tag);
        tag = new Tag(5);
        tag.setColor(0xFF7043);
        tag.setName("Food & Drink");
        DI.getRepositories().tags.add(tag);

        IncomeSource source = new IncomeSource(1);
        source.setMoney(new Money("USD", new BigDecimal("200")));
        source.setName("Scholarship");
        source.setColor(0xFF7043);
        source.getTagIds().add(4);
        DI.getRepositories().incomes.add(source);
        source = new IncomeSource(2);
        source.setMoney(new Money("USD", new BigDecimal("600")));
        source.setName("Job");
        source.setColor(0xFF7043);
        DI.getRepositories().incomes.add(source);

        ExpenseItem expense = new ExpenseItem(1);
        expense.setName("Pizza");
        expense.setMoney(new Money("USD", new BigDecimal("10")));
        expense.getTagIds().add(1);
        expense.getTagIds().add(5);
        expense.setColor(0xFF7043);
        DI.getRepositories().expenses.add(expense);
        expense = new ExpenseItem(2);
        expense.setName("Coke");
        expense.setImageResource(DI.resources.getResource("item-images/coke-can.png"));
        expense.setMoney(new Money("USD", new BigDecimal("2")));
        expense.getTagIds().add(2);
        expense.getTagIds().add(5);
        expense.setColor(0xA1887F);
        DI.getRepositories().expenses.add(expense);
        expense = new ExpenseItem(3);
        expense.setName("Rent");
        expense.setColor(0xA1887F);
        expense.setMoney(new Money("USD", new BigDecimal("600")));
        expense.getTagIds().add(3);
        DI.getRepositories().expenses.add(expense);

        int cnt = 1;
        for (int i = 1; i <= 3; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                DI.getRepositories().history.add(new HistoryEntry(cnt++, i, HistoryEntry.Type.Expense, LocalDateTime.now()));
            }
        }

        for (int i = 1; i <= 2; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                DI.getRepositories().history.add(new HistoryEntry(cnt++, i, HistoryEntry.Type.Income, LocalDateTime.now()));
            }
        }

        FXMLTuple itemsTile = DI.layouts.getFXMLInflater("items-tile.fxml").inflate();
        ((ItemsTileController)itemsTile.getController()).setRepository(DI.getRepositories().expenses, listView -> new ItemHolder());

        primaryStage.setTitle("Pockets 0.0.1");
        primaryStage.setMinWidth(380);
        Scene scene = new Scene(itemsTile.getRoot(), 900, 600);
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
