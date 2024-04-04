package com.example.empireclickers;

public class ElectronicsFactory implements FactoryInterface {
    private static ElectronicsFactory instance;
    private final long profitPerSecond = 30;
    private long count = 0;
    private long costofFactory = 48;

    private ElectronicsFactory() {}

    public static synchronized ElectronicsFactory getInstance() {
        if (instance == null) {
            instance = new ElectronicsFactory();
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
