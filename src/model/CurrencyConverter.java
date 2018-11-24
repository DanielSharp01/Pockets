package model;

import app.Settings;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import utils.FileAssistedJsonAPI;
import utils.JsonAPI;
import view.Dialogs;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Converts between two currencies using the USD as an intermediary currency
 */
public class CurrencyConverter {

    /**
     * Currency Code -&gt; how much of this currency a dollar is worth
     */
    private HashMap<String, BigDecimal> currencyRates = new HashMap<>();

    /**
     * Conversion data was loaded correctly and currency converter can be used
     */
    private boolean canUse = false;

    /**
     * Sets how much dollars the specified currency is worth
     * @param currencyCode ISO code of the currency
     * @param value Value of the currency in dollars
     */
    public void setCurrencyRate(String currencyCode, BigDecimal value)
    {
        currencyRates.put(currencyCode, value);
    }

    /**
     * Convert from a currency to another currency
     * @param fromCode Input currency's code
     * @param fromValue Value in the input currency
     * @param toCode New currency's code
     * @return Value in the new currency
     */
    public BigDecimal convert(String fromCode, BigDecimal fromValue, String toCode)
    {
        if (fromCode.equals(toCode)) return fromValue; // Because conversion rates are not accurate

        return fromValue.divide(currencyRates.get(fromCode), 2, RoundingMode.HALF_UP).multiply(currencyRates.get(toCode));
    }

    /**
     * Deserializes rates from a JSON file and replaces the current rates
     * @param json JSON string
     */
    public void deserializeRates(String json)
    {
        if (json == null) return;

        canUse = true;

        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : object.get("rates").getAsJsonObject().entrySet())
        {
            currencyRates.put(entry.getKey(), new BigDecimal(entry.getValue().getAsString()));
        }
    }

    /**
     * JSON file containing currency rates
     */
    private static final String currencyRatesFile = "currency-rates.json";

    /**
     * API request URL
     */
    private static final String apiRequestURL = "https://openexchangerates.org/api/latest.json?app_id=<API-KEY>&base=USD";

    private static FileAssistedJsonAPI api = null;

    /**
     * Requests the file assisted JSON API for the currency rates and updates them
     * @param onlyFile Only reads the file does not request the API
     * @return True on success, false on failure
     */
    public boolean requestAPI(boolean onlyFile)
    {
        if (api == null)
        {
            api = new FileAssistedJsonAPI(
                    onlyFile ? null : apiRequestURL,
                    Settings.getInstance().getRealApiKey(),
                    Paths.get(currencyRatesFile), 7200);
        }
        try {
            deserializeRates(api.requestString());
            if (!canUse)
            {
                Dialogs.showWarningOk("No currency converter data.", "The currency converter can't be used as there" +
                        "is no currency-rates.json file in the app's folder. Please consider turning on the API once to download" +
                        "the data at least once, or provide one manually. See the user documentation for more details.");
            }
            return canUse;
        } catch (IOException e) {
            return false;
        } catch (JsonAPI.APIKeyNotFoundException e) {
            Dialogs.showWarningOk("API key not found.", "You set to use the Open Exchange Rates API but did not supply a valid API key," +
                    "check the settings or your environment variables. Keep in mind that you must restart this application after setting and environment variable.");
            return false;
        }
    }

    /**
     * @return Conversion data was loaded correctly and currency converter can be used
     */
    public boolean canUseConverter() {
        return canUse;
    }
}
