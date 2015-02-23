package com.findbuddy.findbuddy.services;

import android.location.Address;
import android.location.Geocoder;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by abhidhar on 2/22/15.
 */
public class FindBuddyUtils {

    static SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, ''yy hh:mm aaa");

    public static String getUserReadableDateStr(Date lastUpdatedAt) {
        df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        String dateStr = df.format(lastUpdatedAt);
        return dateStr;
    }

    public static String getRelativeTimeAgo(Date lastUpdatedAt) {
        df.setLenient(true);

        String relativeDate = "";
        long dateMillis = lastUpdatedAt.getTime();
        relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();


        return relativeDate;
    }

    public static String getAddress(Geocoder geocoder, Object latitude1, Object longitude1) {
        double latitude = Double.parseDouble(latitude1.toString());
        double longitude = Double.parseDouble(longitude1.toString());

        try {
            List<Address> addresses;
            String result = "";
            if (latitude != 0 || longitude != 0) {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getAddressLine(1);
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getAddressLine(2);
                Log.d("TAG", "address = " + address + ", city =" + city + ", country = " + country);
                result = city;
                return result;  // just return the first result
            } else {
                return "N/A";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "N/A";
        }
    }
}
