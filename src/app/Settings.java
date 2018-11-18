package app;

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
}
