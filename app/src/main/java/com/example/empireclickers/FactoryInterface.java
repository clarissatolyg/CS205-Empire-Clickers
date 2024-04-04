package com.example.empireclickers;

public interface FactoryInterface {

    public void purchase(MoneyWrapper money);

    public long netProfitPerSecond();

    public long getProfitPerSecond();
    public long getCount();
    public long getCostofFactory();

}
