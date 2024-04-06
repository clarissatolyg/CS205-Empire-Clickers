package com.example.empireclickers;

public class FactoryModel {
    String _id;
    long cost;
    long count;
    long profit;
    String fac_type;
    public FactoryModel(String id, long cost, long count, long profit, String fac_type) {
        _id = id;
        this.cost = cost;
        this.count = count;
        this.profit = profit;
        this.fac_type = fac_type;
    }

}
