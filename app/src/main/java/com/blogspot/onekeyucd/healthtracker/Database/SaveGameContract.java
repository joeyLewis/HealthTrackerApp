package com.blogspot.onekeyucd.healthtracker.Database;

import android.provider.BaseColumns;

public final class SaveGameContract {

    public SaveGameContract() {}

    public static abstract class SaveGameEntry implements BaseColumns {
        public static final String TABLE_NAME = "saved_game";
        public static final String COLUMN_NAME_PLAYER_NAME = "player_name";
        public static final String COLUMN_NAME_PLAYER_HP = "player_hp";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SaveGameEntry.TABLE_NAME
                            + " ("
                            + SaveGameEntry._ID + " INTEGER PRIMARY KEY, "
                            + SaveGameEntry.COLUMN_NAME_PLAYER_NAME + TEXT_TYPE + COMMA_SEP
                            + SaveGameEntry.COLUMN_NAME_PLAYER_HP + INT_TYPE
                            + " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SaveGameEntry.TABLE_NAME;
}
