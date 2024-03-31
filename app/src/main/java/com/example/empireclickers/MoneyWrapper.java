package com.example.empireclickers;
import java.io.Serializable;
import java.math.BigInteger;

public class MoneyWrapper  implements  Serializable{

        private BigInteger money;

        public MoneyWrapper(int money){
            this.money = BigInteger.ZERO;
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
