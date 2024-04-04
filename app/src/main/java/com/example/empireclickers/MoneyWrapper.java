package com.example.empireclickers;
import java.io.Serializable;
import java.math.BigInteger;

public class MoneyWrapper implements Serializable{

    private static MoneyWrapper instance;
    private BigInteger money;

    private MoneyWrapper(){
        this.money = BigInteger.ZERO;
    }

    public static synchronized MoneyWrapper getInstance() {
        if (instance == null) {
            instance = new MoneyWrapper();
        }
        return instance;
    }
    synchronized public BigInteger getMoney() {
        return money;
    }

    synchronized public void addMoney(long amount) {
        this.money = this.money.add(BigInteger.valueOf(amount));
    }

    synchronized public void deductMoney(long amount){
        this.money = this.money.subtract(BigInteger.valueOf(amount));
    }

}
