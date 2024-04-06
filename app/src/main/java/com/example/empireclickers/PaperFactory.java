package com.example.empireclickers;

public class PaperFactory implements FactoryInterface {
    private static PaperFactory instance;
    private final long profitPerSecond = 20;
    private long count = 0;
    private long costofFactory = 40;

    private PaperFactory() {
    }
    public static synchronized PaperFactory getInstance() {
        if (instance == null) {
            instance = new PaperFactory();
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
