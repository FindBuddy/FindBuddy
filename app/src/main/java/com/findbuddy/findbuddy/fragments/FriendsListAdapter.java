package com.findbuddy.findbuddy.fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.findbuddy.findbuddy.R;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by abhidhar on 2/5/15.
 */
public class FriendsListAdapter extends ArrayAdapter<ParseUser> {
    private String mUserId;

    Geocoder geocoder = new Geocoder(getContext());

    SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, ''yy hh:mm aaa");

    public FriendsListAdapter(Context context, String userId, List<ParseUser> users) {
        super(context, 0, users);
        this.mUserId = userId;

        df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ParseUser user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friend_item, parent, false);
        }
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
        TextView tvAccuracy = (TextView) convertView.findViewById(R.id.tvAccuracy);

        String address = getAddress(user.get("lat"), user.get("lon"));
        String accuracy = user.get("accuracy").toString();
        Date lastUpdatedAt = user.getUpdatedAt();
        String dateStr = df.format(lastUpdatedAt);


        tvUserName.setText(user.getUsername());
        tvAddress.setText(address);
        tvAccuracy.setText("Accuracy: " + accuracy + "m \nLast Updated: " + dateStr);


        return convertView;


    }

    public String getAddress(Object latitude1, Object longitude1) {
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
                Toast.makeText(getContext(), "latitude and longitude are null",
                        Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "N/A";
        }
    }

}