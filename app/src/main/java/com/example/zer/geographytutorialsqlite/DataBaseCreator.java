package com.example.zer.geographytutorialsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DataBaseCreator extends SQLiteOpenHelper {
    public static final String DB_NAME = "db_name";
    public static final int DB_VERSION = 1;

    public static class Subregion implements BaseColumns {
        public static final String TABLE_NAME = "Subregions";
        public static final String SUBREGION_NAME = "subregion_name";
    }

    public static class Countries implements BaseColumns {
        public static final String TABLE_NAME = "Countries";
        public static final String SUBREGION_ID = "subregion_id";
        public static final String COUNTRY_NAME = "country_name";
        public static final String CAPITAL = "capital";
    }

    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "Users";
        public static final String USER_NAME = "user_name";
        public static final String USER_PASS = "user_pass";
    }

    static String SCRIPT_CREATE_TBL_USERS = "CREATE TABLE " + Users.TABLE_NAME + " (" +
            Users._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Users.USER_NAME + " TEXT," +
            Users.USER_PASS + " TEXT" +
            ");";

    static String SCRIPT_CREATE_TBL_COUNTRIES = "CREATE TABLE " + Countries.TABLE_NAME + " (" +
            Countries._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Countries.SUBREGION_ID + " INTEGER REFERENCES Subregions (_id) ON DELETE CASCADE, " +
            Countries.COUNTRY_NAME + " TEXT, " + Countries.CAPITAL + " TEXT" +
            ");";

    static String SCRIPT_CREATE_TBL_SUBREGIONS = "CREATE TABLE " + Subregion.TABLE_NAME + " (" +
            Subregion._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Subregion.SUBREGION_NAME + " TEXT" +
            ");";

    public DataBaseCreator(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCRIPT_CREATE_TBL_USERS);
        db.execSQL(SCRIPT_CREATE_TBL_SUBREGIONS);
        db.execSQL(SCRIPT_CREATE_TBL_COUNTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //update, if need
    }
}

