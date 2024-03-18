package org.example.feature.currency;

public enum Currency {
    EUR(840),
    USD(978),
    UAH(980);


    private int currencyCode;

    Currency(int currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currencyCode=" + currencyCode +
                '}';
    }

    public static interface CurrencyService {
        double getRate(Currency currency);
    }
}
