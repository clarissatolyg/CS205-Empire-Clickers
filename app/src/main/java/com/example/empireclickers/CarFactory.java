package com.example.empireclickers;

public class CarFactory implements FactoryInterface{
    private final long profitPerSecond = 40;
    private long count = 0;
    private long costofFactory = 55;
    public CarFactory() {}

    public void purchase(int amount) {
        for (int i = 0; i < amount; i++) {
            this.count += 1;
            double temp = this.costofFactory * 1.1;
            this.costofFactory = Math.round(Math.ceil(temp));
        }
    }

    public long netProfitPerSecond() {
        return this.count * this.profitPerSecond;
    }

    public long getProfitPerSecond() {
        return profitPerSecond;
    }

    public long getCount() {
        return count;
    }

    public long getCostofFactory() {
        return costofFactory;
    }
}
