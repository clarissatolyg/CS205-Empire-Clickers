package com.example.empireclickers;

public class CarFactory implements FactoryInterface{
    private static CarFactory instance;
    private final long profitPerSecond = 40;
    private long count = 0;
    private long costofFactory = 55;
    private CarFactory() {}
    public static synchronized CarFactory getInstance() {
        if (instance == null) {
            instance = new CarFactory();
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
}
