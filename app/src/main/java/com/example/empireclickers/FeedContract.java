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
}

