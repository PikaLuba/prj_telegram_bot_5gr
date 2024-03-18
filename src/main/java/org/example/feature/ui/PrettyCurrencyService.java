package org.example.feature.ui;

import org.example.feature.currency.Currency;

import java.math.BigDecimal;

public class PrettyCurrencyService {
    // rate - кількість
    // currency - назва валюти (USD)

    // priz = 0 - 1 строка message
    // priz = 1 - 3 строка message (продаж)
    // priz = 2 - 2 строка message (купівля)

    // bankId = 1 - Приватбанк
    // bankId = 2 - НБУ
    // bankId = 3 - Монобанк

    // prettyId = 2 - кількість знаків після коми
    // prettyId = 3
    // prettyId = 4

    private String bankName = "";
    private BigDecimal roundedRate;
    private  String template = "";

    private void createBankName (int bankId) {
        String bankName = "";
        if (bankId == 1) {
            bankName = "у Приватбанку";
        } else if (bankId == 2) {
            bankName = "у НБУ";
        } else {
            bankName = "у Монобанку";
        }
    }

    private void createRoundRate(double rate,int prettyId) {
        roundedRate = new BigDecimal(rate);
        roundedRate = roundedRate.setScale(prettyId, BigDecimal.ROUND_DOWN);
    }

    public  String convert(double rate, Currency currency, int priz, int bankId, int prettyId) {

        if (priz == 0) {
            createBankName(bankId);
            template = "Курс " + bankName + " ${currency} / UAH";
            return template
                    .replace("${currency}", currency.name());

        } else if (priz == 2) {
            template = "Купівля: ${rate}";
            createRoundRate(rate, prettyId);
            return template
                    .replace("${rate}", roundedRate + "");

        } else {
            template = "Продаж ${rate}";
            createRoundRate(rate, prettyId);
            return template
                    .replace("${rate}", roundedRate + "");

        }
    }
}

