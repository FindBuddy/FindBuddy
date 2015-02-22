package com.findbuddy.findbuddy.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by seshasa on 2/20/15.
 */
@ParseClassName("Friend")
public class User extends ParseObject {
    private String userId;
    private String name;
    private double lat;
    private double lon;
    private float accuracy;
    private String updateTime;


    public User() {}

    public User(String userId, String name, double lat, double lon, float accuracy) {
        this.userId = userId;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.accuracy = accuracy;

        setUserId(userId);
        setName(name);
        setLat(lat);
        setLon(lon);
        setAccuracy(accuracy);

    }

    public String getUserId() {
        return getString("userId");
    }

    public void setUserId(String userId) {
        put("userId", userId);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public double getLat() {
        return getDouble("lat");
    }

    public void setLat(double lat) {
        put("lat", lat);
    }

    public double getLon() {
        return getDouble("lon");
    }

    public void setLon(double lon) {
        put("lon", lon);
    }

    public int getAccuracy() {
        return getInt("accuracy");
    }

    public void setAccuracy(float accuracy) {
        put("accuracy", accuracy);
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public static UserList<User> getDummyValues()
    {
        double longs[] = {-121.82,-121.92,-122.02,-122.12,-122.22};
        double lats[] = {37.2,37.25,37.3,37.35,37.4};
        UserList<User> users = new UserList<>();
        for(int i = 1; i <= 5; i++)
        {
            users.add(new User(String.valueOf(i),"user "+String.valueOf(i),lats[i-1],longs[i-1],500));
        }
        return users;

    }
}
