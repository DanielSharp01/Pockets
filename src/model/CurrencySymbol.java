package model;

import app.Settings;

import java.util.HashMap;

/**
 * Represents a currency symbol
 */
public class CurrencySymbol {
    /**
     * Symbol for the currency
     */
    private String symbol;

    /**
     * Must always be put after the number. When set to false a setting called {@link Settings#currencySymbolsAfter} can force the symbol to be always after the number.
     */
    private boolean alwaysAfter;

    /**
     * Currency ISO code -> CurrencySymbol mapping
     */
    private static final HashMap<String, CurrencySymbol> currencySignHashMap = new HashMap<>();

    /**
     * Currency symbol string -> Currency ISO code mapping
     */
    private static final HashMap<String, String> reverseCurrencySignHashMap = new HashMap<>();

    static
    {
        currencySignHashMap.put("USD", new CurrencySymbol("$", false));
        reverseCurrencySignHashMap.put("$", "USD");
        currencySignHashMap.put("GBP", new CurrencySymbol("£", false));
        reverseCurrencySignHashMap.put("£", "GBP");
        currencySignHashMap.put("EUR", new CurrencySymbol("€", false));
        reverseCurrencySignHashMap.put("€", "EUR");
        currencySignHashMap.put("HUF", new CurrencySymbol("Ft", true));
        reverseCurrencySignHashMap.put("Ft", "HUF");
    }

    /**
     * Retrieves the symbol for a currency code
     * @param currencyCode Currency ISO code
     * @return CurrencySymbol object if exists, null otherwise
     */
    public static CurrencySymbol symbolFor(String currencyCode)
    {
        return currencySignHashMap.get(currencyCode);
    }

    /**
     * Retrieves the currency code for a symbol
     * @param currencySymbol Symbol string
     * @return Currency ISO code if symbol exists, null otherwise
     */
    public static String codeFor(String currencySymbol)
    {
        return reverseCurrencySignHashMap.get(currencySymbol);
    }

    /**
     * @param symbol Symbol for the currency
     * @param alwaysAfter Must always be put after the number. When set to false a setting called {@link Settings#currencySymbolsAfter} can force the symbol to be always after the number.
     */
    public CurrencySymbol(String symbol, boolean alwaysAfter) {
        this.symbol = symbol;
        this.alwaysAfter = alwaysAfter;
    }

    /**
     * @return Symbol for the currency
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @return Must always be put after the number. When set to false a setting called {@link Settings#currencySymbolsAfter} can force the symbol to be always after the number.
     */
    public boolean isAlwaysAfter() {
        return alwaysAfter;
    }
}
