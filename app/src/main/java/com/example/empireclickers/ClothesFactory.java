package com.example.empireclickers;

public class ClothesFactory implements FactoryInterface{
    private static ClothesFactory instance;
    private final long profitPerSecond = 7;
    private long count = 0;

    private long costofFactory = 20;

    private ClothesFactory() {}

    public static synchronized ClothesFactory getInstance() {
        if (instance == null) {
            instance = new ClothesFactory();
        }
        return instance;
    }
    public void purchase(MoneyWrapper moneyWrapper) {
        while(moneyWrapper.getMoney().longValue() >= this.costofFactory){
            moneyWrapper.deductMoney(this.costofFactory);
            this.count += 1;
            this.costofFactory = Math.round(Math.ceil(this.costofFactory * 1.1));
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
    public void setCount(long count) { this.count = count; }
    public void setCost(long cost) { costofFactory = cost; }
}
