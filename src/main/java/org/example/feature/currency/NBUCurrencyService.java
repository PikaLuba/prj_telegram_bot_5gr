package org.example.feature.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.feature.dto.CurrencyItemNBU;
import org.example.feature.dto.CurrencyItemPB;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class NBUCurrencyService implements CurrencyService {
    @Override
    public double getRateBuy(Currency currency, int priz) {
        String url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
        String json;

        // get JSON
        try {
            json = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can't connect to NBUAPI");
        }

        //Convert json => Java Object
        Type typeToken = TypeToken
                .getParameterized(List.class, CurrencyItemNBU.class)
                .getType();

        List<CurrencyItemNBU> currencyItems = new Gson().fromJson(json, typeToken);

        // Find UAH/USD
        double result = 0.0;
        if (priz == 2) {
            result = currencyItems.stream()
                    .filter(it -> it.getCc() == currency)
                    .map(CurrencyItemNBU::getRate)
                    .findFirst()
                    .orElseThrow();
        } else {
            result = currencyItems.stream()
                    .filter(it -> it.getCc() == currency)
                    .map(CurrencyItemNBU::getRate)
                    .findFirst()
                    .orElseThrow();
        }
        return result;
    }
}

