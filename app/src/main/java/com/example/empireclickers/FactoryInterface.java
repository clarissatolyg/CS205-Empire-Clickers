package com.example.empireclickers;

import java.io.Serializable;

public interface FactoryInterface extends Serializable {

    public void purchase(MoneyWrapper money);

    public long netProfitPerSecond();

    public long getProfitPerSecond();
    public long getCount();
    public long getCostofFactory();

}
