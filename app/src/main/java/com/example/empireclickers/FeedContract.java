package com.example.empireclickers;

import android.provider.BaseColumns;

public final class FeedContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "credits";
        public static final String COLUMN_NAME_TITLE = "credit";

    }

    public static class FactoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "factory";
        public static final String FAC_COUNT = "count";
        public static final String FAC_PROFIT = "profit";
        public static final String FAC_COST = "cost";
        public static final String FAC_TYPE = "fac_type";
    }

}

