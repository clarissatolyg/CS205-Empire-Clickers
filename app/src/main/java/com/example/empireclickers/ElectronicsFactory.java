package com.example.empireclickers;

public class ElectronicsFactory implements FactoryInterface {
    private final long profitPerSecond = 30;
    private long count = 0;
    private long costofFactory = 48;

    public ElectronicsFactory() {
    }

    public void purchase(int amount) {
        for (int i = 0; i < amount; i++) {
            this.count += 1;
            double temp = this.costofFactory * 1.1; // increase cost after every purchase
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
