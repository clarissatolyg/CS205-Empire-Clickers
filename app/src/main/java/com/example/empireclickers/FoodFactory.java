package com.example.empireclickers;

public class FoodFactory implements FactoryInterface {
    private final long profitPerSecond = 3;
    private long count = 0;
    private long costofFactory = 10;

    public FoodFactory() {
    }

    public void purchase(MoneyWrapper moneyWrapper) {
        while(moneyWrapper.getMoney().longValue() >= this.costofFactory){
            moneyWrapper.deductMoney(this.costofFactory);
            this.count += 1;
            this.costofFactory = Math.round(Math.ceil(this.costofFactory * 1.1));
        }
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
