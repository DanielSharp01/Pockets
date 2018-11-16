package model;

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
        this.amount = amount;
    }

    /**
     * @return 3 letter code (ISO 4217) of the currency (eg. USD)
     */
    public String getCurrency() {
        return currency;
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
