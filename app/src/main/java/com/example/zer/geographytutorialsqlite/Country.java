package com.example.zer.geographytutorialsqlite;

import java.io.Serializable;

public class Country implements Serializable {

    public String name;
    public String capital;
    public String subregion;

    public Country(String name, String capital, String subregion) {
        this.name = name;
        this.capital = capital;
        this.subregion = subregion;
    }
}
