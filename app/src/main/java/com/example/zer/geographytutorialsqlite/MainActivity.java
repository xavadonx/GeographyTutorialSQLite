package com.example.zer.geographytutorialsqlite;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    public static final String SHAREDPREFS = "com.example.zer.geographytutorialsqlite";
    public static final String SALT = "example.zer";
    private boolean isAvailableData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        if(!isAvailableData) {
            Retrofit.getCountries(new Callback<List<Country>>() {
                @Override
                public void success(List<Country> countryList, Response response) {
                    for (Country c : countryList) {
                        Db.getInstance(MainActivity.this).insertSubregion(c.subregion);
                        Db.getInstance(MainActivity.this).insertCountries(c.subregion, c.name, c.capital);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(MainActivity.this, "ошибка", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static String md5(String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
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
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
