package com.findbuddy.findbuddy.models;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by seshasa on 2/20/15.
 */
public class User {
    private String id;
    private String name;
    private Double lat;
    private Double lon;
    private float accuracy;

    public User(String id, String name, Double lat, Double lon, float accuracy) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.accuracy = accuracy;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public static ArrayList<User> getDummyValues()
    {
        Double longs[] = {-121.82,-121.92,-122.02,-122.12,-122.22};
        Double lats[] = {37.2,37.25,37.3,37.35,37.4};
        ArrayList<User> users = new ArrayList<>();
        for(int i = 1; i <= 5; i++)
        {
            users.add(new User(String.valueOf(i),"user "+String.valueOf(i),lats[i-1],longs[i-1],500));
        }
        return users;

    }
}
