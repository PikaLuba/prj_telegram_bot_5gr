package org.example.feature.ui;

import org.example.feature.currency.Currency;

public class PrettyCurrencyService {
    public  String convert(double rate, Currency currency, int priz, int bankId) {
        if (priz == 0) {

            String bankName = "";
            if (bankId == 1) {
                bankName = "в Приватбанке";
            } else if (bankId == 2) {
                bankName = "в НБУ";
            } else {
                bankName = "в Монобанке";
            }

            String template = "Курс " + bankName + " ${currency} / UAH";
            return template
                    .replace("${currency}", currency.name());
        } else if (priz == 2) {
            String template = "Покупка: ${rate}";
            // для округления до 2 знаков после запятой
            float roundedRate = Math.round(rate * 100d) / 100f;
            return template
                    .replace("${rate}", roundedRate + "");
        } else {
            String template = "Продажа ${rate}";
            // для округления до 2 знаков после запятой
            float roundedRate = Math.round(rate * 100d) / 100f;
            return template
                    .replace("${rate}", roundedRate + "");
        }
    }
}

