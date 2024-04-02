package com.example.empireclickers;

public class ClothesFactory implements FactoryInterface{
    private final long profitPerSecond = 7;
    private long count = 0;

    private long costofFactory = 20;

    public ClothesFactory() {}

    public void purchase(int amount) {
        for (int i = 0; i < amount; i++) {
            this.count += 1;
            double temp = this.costofFactory * 1.1; // increase cost of factory after every purchase
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
