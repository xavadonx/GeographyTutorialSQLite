package com.example.zer.geographytutorialsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.zer.geographytutorialsqlite.MainActivity.SHAREDPREFS;

public class Db {

    private SQLiteDatabase database;
    private DataBaseCreator dbCreator;

    private static Db instance;

    private Db(Context context) {
        dbCreator = new DataBaseCreator(context);
        if (database == null || !database.isOpen()) {
            database = dbCreator.getWritableDatabase();
        }
    }

    public static Db getInstance(Context context) {
        if (instance == null) {
            instance = new Db(context);
        }
        return instance;
    }

    public long insertUser(String username, String userpass) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseCreator.Users.USER_NAME, username);
        cv.put(DataBaseCreator.Users.USER_PASS, userpass);
        return database.insert(DataBaseCreator.Users.TABLE_NAME, null, cv);
    }

    public boolean isAvailableData() {
        boolean result = false;
        String query = "select count(" + DataBaseCreator.Countries._ID + ") from " + DataBaseCreator.Subregion.TABLE_NAME;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            try {
                cursor.moveToFirst();
                result = cursor.getInt(0) > 0;
            } catch (Exception ex) {
                Log.d("isAvailableData", ex.getMessage() + "\n" + query);
            } finally {
                cursor.close();
            }
        }

        return result;
    }

    public void insertCountries(String subregion, String name, String capital) {
        String query = "insert into " + DataBaseCreator.Countries.TABLE_NAME + "(" +
                DataBaseCreator.Countries.SUBREGION_ID + ", " +
                DataBaseCreator.Countries.COUNTRY_NAME + ", " +
                DataBaseCreator.Countries.CAPITAL + ") " +
                "values((select " + DataBaseCreator.Subregion._ID + " from " + DataBaseCreator.Subregion.TABLE_NAME +
                " where " + DataBaseCreator.Subregion.SUBREGION_NAME + " = '" + subregion.replace("'", "") + "'), '" +
                name.replace("'", "") + "', '" + capital.replace("'", "") + "')";
        try {
            database.execSQL(query);
        }
        catch (Exception ex) {
            Log.d("insertCountries", ex.getMessage() + "\n" + query);
        }
    }

    public void insertSubregion(String subregion) {
        int regionId = -1;
        String query = "select " + DataBaseCreator.Subregion._ID + " from " + DataBaseCreator.Subregion.TABLE_NAME +
                " where " + DataBaseCreator.Subregion.SUBREGION_NAME + " = '" + subregion.replace("'", "") + "'";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            try {
                cursor.moveToFirst();
                regionId = cursor.getInt(0);
            } catch (Exception ex) {
                Log.d("insertSubregion", ex.getMessage() + "\n" + query);
            } finally {
                cursor.close();
            }
        }
        if(regionId < 0) {
            query = "insert into " + DataBaseCreator.Subregion.TABLE_NAME + "(" + DataBaseCreator.Subregion.SUBREGION_NAME + ") " +
                    "values('" + subregion.replace("'", "") + "');";
            try {
                database.execSQL(query);
            }
            catch (Exception ex) {
                Log.d("insertSubregion", ex.getMessage() + "\n" + query);
            }
        }
    }

    public List<String> getCountriesNames() {
        List<String> result = new ArrayList<>();
        String query = "select " + DataBaseCreator.Countries.COUNTRY_NAME + " from " + DataBaseCreator.Countries.TABLE_NAME +
                " order by " + DataBaseCreator.Countries._ID;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            try {
                cursor.moveToFirst();
                result.add(cursor.getString(0));
            } catch (Exception ex) {
                Log.d("getCountriesNames", ex.getMessage() + "\n" + query);
            } finally {
                cursor.close();
            }
        }
        return result;
    }
}
