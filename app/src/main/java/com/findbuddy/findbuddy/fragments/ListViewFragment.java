package com.findbuddy.findbuddy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findbuddy.findbuddy.R;

/**
 * Created by abhidhar on 2/19/15.
 */
public class ListViewFragment extends Fragment {

    private static ListViewFragment listViewFragment;

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

        return view;
    }
}
