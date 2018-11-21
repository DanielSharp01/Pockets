package app;

import utils.DI;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

/**
 * Contains app settings
 */
public class Settings {

    /**
     * Date format used throughout the application
     */
    private String dateFormat = "yyyy-M-d H:mm[:ss]";

    /**
     * Date time formatter matching the set date format
     */
    private transient DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);

    /**
     * Determines whether currency symbols are always displayed after the number
     */
    private boolean currencySymbolsAfter = false;

    /**
     * @return Date format used throughout the application
     */
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * Also sets {@link Settings#dateTimeFormatter} to match the date format
     * @param dateFormat Date format used throughout the application
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * @return Determines whether currency symbols are always displayed after the number
     */
    public boolean areCurrencySymbolsAfter() {
        return currencySymbolsAfter;
    }

    /**
     * @param currencySymbolsAfter Determines whether currency symbols are always displayed after the number
     */
    public void setCurrencySymbolsAfter(boolean currencySymbolsAfter) {
        this.currencySymbolsAfter = currencySymbolsAfter;
    }

    /**
     * @return Date time formatter matching the set date format
     */
    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    /**
     * Display currency throughout the application
     */
    private String displayCurrency;

    /**
     * @return Display currency throughout the application
     */
    public String getDisplayCurrency() {
        return displayCurrency;
    }

    /**
     * @param displayCurrency Display currency throughout the application
     */
    public void setDisplayCurrency(String displayCurrency) {
        this.displayCurrency = displayCurrency;
    }

    /**
     * Global settings object, contains app settings
     */
    private static transient Settings settings;

    /**
     * @return Global settings object, contains app settings
     */
    public static Settings getInstance()
    {
       return settings;
    }

    /**
     * Settings json file
     */
    private static transient final String jsonFile = "app-settings.json";

    /**
     * Loads the global settings from the JSON file
     * @return Whether the load was successful or not
     */
    public static boolean load()
    {
        try
        {
            if (Files.exists(Paths.get(jsonFile)))
                settings = DI.gson.fromJson(new String(Files.readAllBytes(Paths.get(jsonFile)), StandardCharsets.UTF_8), Settings.class);
            else
                settings = new Settings();
        }
        catch (IOException e)
        {
            return false;
        }

        return true;
    }

    /**
     * Saves the setting onto the file system as JSON
     * @return Whether the save was successful or not
     */
    public boolean save()
    {
        try
        {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(jsonFile), StandardCharsets.UTF_8);

            writer.write(DI.gson.toJson(this, Settings.class));
            writer.close();
        }
        catch (IOException e)
        {
            return false;
        }

        return true;
    }
}
