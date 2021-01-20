package com.example.smarthome.Network.models;

public class GoodsEntry {
    private static final String TAG = StoreEntry.class.getSimpleName();
    public int id;
    public String name;
    public String image;
    public String description;
    public double price;

    public GoodsEntry(int id, String name, String image, String description, double price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
    }
}
