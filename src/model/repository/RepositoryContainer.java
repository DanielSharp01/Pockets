package model.repository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Container containing all repositories
 * Saves and loads them from files as well
 */
public class RepositoryContainer {

    /**
     * Expenses JSON file
     */
    private static final String expensesJSONFile = "expenses.json";

    /**
     * Expense repository
     */
    public final ExpenseRepository expenses = new ExpenseRepository(this);

    /**
     * Incomes JSON file
     */
    private static final String incomesJSONFile = "incomes.json";

    /**
     * Income repository
     */
    public final IncomeRepository incomes = new IncomeRepository(this);

    /**
     * Tags JSON file
     */
    private static final String tagsJSONFile = "tags.json";

    /**
     * Tag repository
     */
    public final TagRepository tags = new TagRepository(this);

    /**
     * History JSON file
     */
    private static final String historyJSONFile = "history.json";

    /**
     * History repository
     */
    public final HistoryRepository history = new HistoryRepository(this);

    /**
     * Saves repository contents into JSON files
     * @return True if successful, false if exceptions occurred
     */
    public boolean save()
    {
        boolean successful = true;
        try
        {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(expensesJSONFile), StandardCharsets.UTF_8);
            writer.write(expenses.serialize());
            writer.close();
        }
        catch (IOException e)
        {
            successful = false;
        }

        try
        {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(incomesJSONFile), StandardCharsets.UTF_8);
            writer.write(incomes.serialize());
            writer.close();
        }
        catch (IOException e)
        {
            successful = false;
        }

        try
        {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(tagsJSONFile), StandardCharsets.UTF_8);
            writer.write(tags.serialize());
            writer.close();
        }
        catch (IOException e)
        {
            successful = false;
        }

        try
        {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(historyJSONFile), StandardCharsets.UTF_8);
            writer.write(history.serialize());
            writer.close();
        }
        catch (IOException e)
        {
            successful = false;
        }

        return successful;
    }

    /**
     * Loads repository contents from JSON files
     * @return True if successful, false if exceptions occurred
     */
    public boolean load()
    {
        boolean successful = true;
        try
        {
            if (Files.exists(Paths.get(expensesJSONFile)))
                expenses.deserialize(new String(Files.readAllBytes(Paths.get(expensesJSONFile)), StandardCharsets.UTF_8));
        }
        catch (IOException e)
        {
            successful = false;
        }

        try
        {
            if (Files.exists(Paths.get(incomesJSONFile)))
                incomes.deserialize(new String(Files.readAllBytes(Paths.get(incomesJSONFile)), StandardCharsets.UTF_8));
        }
        catch (IOException e)
        {
            successful = false;
        }

        try
        {
            if (Files.exists(Paths.get(tagsJSONFile)))
                tags.deserialize(new String(Files.readAllBytes(Paths.get(tagsJSONFile)), StandardCharsets.UTF_8));
        }
        catch (IOException e)
        {
            successful = false;
        }

        try
        {
            if (Files.exists(Paths.get(historyJSONFile)))
                history.deserialize(new String(Files.readAllBytes(Paths.get(historyJSONFile)), StandardCharsets.UTF_8));
        }
        catch (IOException e)
        {
            successful = false;
        }

        return successful;
    }
}
