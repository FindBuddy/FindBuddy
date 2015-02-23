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
import com.findbuddy.findbuddy.services.FindBuddyUtils;
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

    public FriendsListAdapter(Context context, String userId, List<ParseUser> users) {
        super(context, 0, users);
        this.mUserId = userId;
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

        String address = FindBuddyUtils.getAddress(geocoder, user.get("lat"), user.get("lon"));

        String accuracy = user.get("accuracy").toString();
        Date lastUpdatedAt = user.getUpdatedAt();
        String dateStr = FindBuddyUtils.getUserReadableDateStr(lastUpdatedAt);


        tvUserName.setText(user.getUsername());
        tvAddress.setText(address);
        tvAccuracy.setText("Accuracy: " + accuracy + "m \nLast Updated: " + dateStr);
        return convertView;
    }
}