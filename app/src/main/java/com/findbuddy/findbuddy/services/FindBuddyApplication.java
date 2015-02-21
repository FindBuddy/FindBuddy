package com.findbuddy.findbuddy.services;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by abhidhar on 2/5/15.
 */
public class FindBuddyApplication extends Application {
    public static final String YOUR_APPLICATION_ID = "FsMb9lhz9jT25SiBTRpau8PxkqHbXez1271LamgD";
    public static final String YOUR_CLIENT_KEY = "2EdAWbyaQvvVmRejQvvkWJqbFSDTi4tSVkkZivQr";
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
    }
}