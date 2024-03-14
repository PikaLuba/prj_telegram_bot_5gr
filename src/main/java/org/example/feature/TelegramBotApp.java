package org.example.feature;

import org.example.feature.telegram.TelegramBotService;

public class TelegramBotApp {
    public static void main(String[] args) {
        // 1)
        // Currency.CurrencyService currencyService = new PrivateBankCurrencyService();
        // double rate = currencyService.getRate(Currency.USD);
        // System.out.println("rate= " + rate);

        // 2)
        // Currency.CurrencyService currencyService = new PrivateBankCurrencyService();
        // Currency currency = Currency.USD;
        // double rate = currencyService.getRate(currency);
        // String prettyText = new PrettyCurrencyService().convert(rate, currency);
        // System.out.println(prettyText);

        // 3)
        TelegramBotService botService = new TelegramBotService();


    }
}

