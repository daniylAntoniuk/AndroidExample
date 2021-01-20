package com.example.smarthome.Network.models;

import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.example.smarthome.R;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A product entry in the list of products.
 */
public class StoreEntry {
    private static final String TAG = StoreEntry.class.getSimpleName();
    public int id;
    public String name;
    public String image;
    public String adress;
    public String description;
    private double coordinate1;
    private double coordinate2;
    public StoreEntry(int id, String name, String image, String adress, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.adress = adress;
        this.description = description;
    }

    public StoreEntry(int id, String name, String image, String adress, String description, double coordinate1, double coordinate2) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.adress = adress;
        this.description = description;
        this.coordinate1 = coordinate1;
        this.coordinate2 = coordinate2;
    }

    public double getCoordinate1() {
        return coordinate1;
    }

    public void setCoordinate1(double coordinate1) {
        this.coordinate1 = coordinate1;
    }

    public double getCoordinate2() {
        return coordinate2;
    }

    public void setCoordinate2(double coordinate2) {
        this.coordinate2 = coordinate2;
    }

    public static String getTAG() {
        return TAG;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Loads a raw JSON at R.raw.products and converts it into a list of ProductEntry objects
     */

}
