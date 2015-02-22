package com.findbuddy.findbuddy.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.findbuddy.findbuddy.R;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by abhidhar on 2/5/15.
 */
public class FriendsListAdapter extends ArrayAdapter<ParseUser> {
    private String mUserId;

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

        tvUserName.setText(ParseUser.getCurrentUser().getUsername() + " (lat: " + ParseUser.getCurrentUser().get("lat") + ", lon: " + ParseUser.getCurrentUser().get("lon") + ")");

        return convertView;


    }

}