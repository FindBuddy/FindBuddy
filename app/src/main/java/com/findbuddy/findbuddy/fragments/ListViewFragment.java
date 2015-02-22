package com.findbuddy.findbuddy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.findbuddy.findbuddy.R;
import com.findbuddy.findbuddy.models.UserList;
import com.parse.ParseUser;

/**
 * Created by abhidhar on 2/19/15.
 */
public class ListViewFragment extends Fragment {

    private static ListViewFragment listViewFragment;
    private UserList<ParseUser> users;

    private ListView lvFriends;

    FriendsListAdapter adapter;

    public static Fragment newInstance() {
        if(listViewFragment == null) {
            listViewFragment = new ListViewFragment();
        }
        return  listViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        lvFriends = (ListView) view.findViewById(R.id.lvFriends);


        lvFriends.setAdapter(adapter);
        
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.users = (UserList) getArguments().getSerializable("users");

        adapter = new FriendsListAdapter(getActivity(), "current user", users);

        //adapter.addAll(users);
    }
}
