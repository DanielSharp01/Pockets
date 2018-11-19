package model;

import app.Settings;
import utils.DI;

import java.math.BigDecimal;

/**
 * Represents an amount of money in a specific currency
 */
public class Money {
    /**
     * 3 letter code (ISO 4217) of the currency (eg. USD)
     */
    private String currency;

    /**
     * Amount of money in the specified currency
     */
    private BigDecimal amount;

    /**
     * Represents an amount of money in a specific currency
     * @param currency 3 letter code (ISO 4217) of the currency (eg. USD)
     * @param amount Amount of money in the specified currency
     */
    public Money(String currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static Money parse(String text)
    {
        // Split into character number character

        int parseState = 0; // 0 - begun, 1 - [number] word, 2 - number [word], 3 - [word] number, 4 - word [number], 5 - ended
        String word = "";
        String number = "";

        for (char c : text.toCharArray())
        {
            if (Character.isDigit(c))
            {
                if (parseState == 3) parseState = 4;

                if (parseState == 0) parseState = 1;
                else if (parseState != 1 && parseState != 4) return null; // Number state expected



                number += c;
            }
            else if (Character.isWhitespace(c))
            {
                if (parseState == 1) parseState = 2;
                else if (parseState == 3) parseState = 4;
                else if (parseState == 2 || parseState == 4) parseState = 5;
            }
            else if (c == '.')
            {
                if (parseState != 1 && parseState != 4) return null; // Number state expected
                if (number.contains(".")) return null; // Already has a decimal dot
                number += c;
            }
            else
            {
                if (parseState == 1) parseState = 2;

                if (parseState == 0) parseState = 3;
                else if (parseState != 2 && parseState != 3) return null; // Word state expected

                word += c;
            }
        }

        if (number.isEmpty()) return null;

        String[] numberSplit = number.split("\\.");
        if (numberSplit.length > 1 && numberSplit[1].length() > 2)
            return null; // Don't allow more than 2 decimals

        String code = CurrencySymbol.codeFor(word);
        if (code == null) code = word;
        if (code.isEmpty()) code = "USD"; //TODO: DI.settings.baseCurrency;

        // TODO: Check if it's legal code
        return new Money(code, new BigDecimal(number));
    }

    /**
     * @return 3 letter code (ISO 4217) of the currency (eg. USD)
     */
    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        CurrencySymbol symbol = CurrencySymbol.symbolFor(currency);

        if (symbol != null)
        {
            if (symbol.isAlwaysAfter() || Settings.getInstance().areCurrencySymbolsAfter())
            {
                return amount.toString() + symbol.getSymbol();
            }
            else
            {
                return symbol.getSymbol() + amount.toString();
            }
        }

        return amount.toString() + " " + currency;
    }

    /**
     * @return mount of money in the specified currency
     */
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public Money clone()
    {
        return new Money(getCurrency(), new BigDecimal(getAmount().toString()));
    }
}
