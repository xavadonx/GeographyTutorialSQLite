package com.example.zer.geographytutorialsqlite;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    public static final String SHAREDPREFS = "com.example.zer.geographytutorialsqlite";
    public static final String SALT = "example.zer";

    public static String user = "";
    private static String salt;

    private boolean isAvailableData;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences(SHAREDPREFS, Context.MODE_PRIVATE);
        saveSalt();

        isAvailableData = Db.getInstance(this).isAvailableData();

        LogIn logIn = LogIn.newInstance();
//        SignUp signUp = SignUp.newInstance();
//        leftFragment.setSender(rightFragment);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.am_logincontainer, logIn, LogIn.class.getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isAvailableData) {
            Retrofit.getCountries(new Callback<List<Country>>() {
                @Override
                public void success(List<Country> countryList, Response response) {
                    for (Country c : countryList) {
                        if (!c.subregion.equals("") && !c.capital.equals("")) {
                            Db.getInstance(MainActivity.this).insertSubregion(c.subregion);
                            Db.getInstance(MainActivity.this).insertCountries(c.subregion, c.name, c.capital);
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(MainActivity.this, "ошибка", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String md5(String s) {
        String result = "";
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());

            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();

            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);

                while (h.length() < 2) {
                    h = "0" + h;
                }

                hexString.append(h);
            }
            result = hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String createPass(String pass) {
        String passHash = md5(pass);
        String saltHash = md5(salt);
        String newPassHash = passHash + saltHash;
        String result = md5(newPassHash);
        return result;
    }

    private void saveSalt() {
        if (sp.getString(SALT, "Unknown").equals("Unknown")) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(SALT, UUID.randomUUID().toString());
            editor.apply();
        } else {
            salt = sp.getString(SALT, "Unknown");
        }
    }
}
