package org.example.feature.dto;
import org.example.feature.currency.Currency;

public class CurrencyItemNBU {
    private Currency cc;
    private float rate;

    public Currency getCc() {
        return cc;
    }

    public void setCc(Currency cc) {
        this.cc = cc;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}

