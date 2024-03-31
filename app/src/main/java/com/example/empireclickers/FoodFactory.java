package com.example.empireclickers;

public class FoodFactory implements FactoryInterface {
    private final long profitPerSecond = 3;
    private long count = 0;
    private long costofFactory = 10;

    public FoodFactory() {
    }

    public void purchase(int amount) {
        this.count += amount;
        double temp = this.costofFactory * Math.pow(1.1, amount);
        this.costofFactory = Math.round(Math.ceil(temp));
    }

    public long netProfitPerSecond(){
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
