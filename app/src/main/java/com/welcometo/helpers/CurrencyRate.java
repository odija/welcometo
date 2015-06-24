package com.welcometo.helpers;

public class CurrencyRate {
    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        this.mCurrency = currency;
    }

    private String mCurrency;

    public float getRate() {
        return mRate;
    }

    public void setRate(float rate) {
        this.mRate = rate;
    }

    private float mRate;
}
