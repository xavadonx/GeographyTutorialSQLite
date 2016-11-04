package com.example.zer.geographytutorialsqlite;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;

public class Retrofit {

    private static final String ENDPOINT = "http://restcountries.eu/rest";
    private static ApiInterface apiInterface;

    static {
        initialize();
    }

    interface ApiInterface {
        @GET("/v1/all")
        void getCountries(Callback<List<Country>> callback);
    }

    public static void initialize() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        apiInterface = restAdapter.create(ApiInterface.class);
    }

    public static void getCountries(Callback<List<Country>> callback) {
        apiInterface.getCountries(callback);
    }
}
