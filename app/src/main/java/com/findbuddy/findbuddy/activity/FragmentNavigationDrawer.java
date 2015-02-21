/*
** FragmentNavigationDrawer object for use with support-v7 library 
** using compatibility fragments and support actionbar
*/

package com.findbuddy.findbuddy.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.findbuddy.findbuddy.R;
import com.findbuddy.findbuddy.fragments.ListViewFragment;
import com.findbuddy.findbuddy.fragments.MapViewFragment;
import com.findbuddy.findbuddy.models.User;
import com.findbuddy.findbuddy.models.UserList;

import java.util.ArrayList;
import java.util.List;

public class FragmentNavigationDrawer extends DrawerLayout {
    private ActionBarDrawerToggle drawerToggle;
    private ListView lvDrawer;
    private ArrayAdapter<String> drawerAdapter;
    private ArrayList<FragmentNavItem> drawerNavItems;
    private int drawerContainerRes;

    private UserList<User> users;

    Fragment oldFragment = null;

    public FragmentNavigationDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FragmentNavigationDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FragmentNavigationDrawer(Context context) {
        super(context);
    }

    // setupDrawerConfiguration((ListView) findViewById(R.id.lvDrawer), R.layout.drawer_list_item, R.id.flContent);
    public void setupDrawerConfiguration(ListView drawerListView, int drawerItemRes, int drawerContainerRes) {
        // Setup navigation items array
        drawerNavItems = new ArrayList<FragmentNavItem>();
        // Set the adapter for the list view
        drawerAdapter = new ArrayAdapter<String>(getActivity(), drawerItemRes,
                new ArrayList<String>());
        this.drawerContainerRes = drawerContainerRes;
        // Setup drawer list view and related adapter
        lvDrawer = drawerListView;
        lvDrawer.setAdapter(drawerAdapter);
        // Setup item listener
        lvDrawer.setOnItemClickListener(new FragmentDrawerItemListener());
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        drawerToggle = setupDrawerToggle();
        setDrawerListener(drawerToggle);
        // set a custom shadow that overlays the main content when the drawer
        setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // Setup action buttons
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    // addNavItem("First", "First Fragment", FirstFragment.class)
    public void addNavItem(String navTitle, String windowTitle, Class<? extends Fragment> fragmentClass) {
        drawerAdapter.add(navTitle);
        drawerNavItems.add(new FragmentNavItem(windowTitle, fragmentClass));
    }

    /**
     * Swaps fragments in the main content view
     */
    public void selectDrawerItem(int position) {
        // Create a new fragment and specify the planet to show based on
        // position
        try {
            FragmentNavItem navItem = drawerNavItems.get(position);
            Fragment newFragment = null;

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            if (navItem.getFragmentClass() == MapViewFragment.class) {
                newFragment = MapViewFragment.newInstance();

            } else if (navItem.getFragmentClass() == ListViewFragment.class) {
                newFragment = ListViewFragment.newInstance();
            }

            if(oldFragment == null || ! newFragment.isAdded()) {            // call setArguments only the first time the fragment is created
                Bundle args = navItem.getFragmentArgs();
                if (args != null) {
                    args.putSerializable("users", users);
                    newFragment.setArguments(args);
                }
            } else {
                newFragment.getArguments().putSerializable("users", users);
            }

            if (oldFragment == null) {
                ft.add(drawerContainerRes, newFragment, navItem.getFragmentClass().getName());
            } else {
                if(! newFragment.isAdded()) {
                    ft.add(drawerContainerRes, newFragment, navItem.getFragmentClass().getName());
                }
                ft.hide(oldFragment);
                ft.show(newFragment);
            }
            ft.commit();

            oldFragment = newFragment;



            // Highlight the selected item, update the title, and close the drawer
            lvDrawer.setItemChecked(position, true);
            setTitle(navItem.getTitle());
            closeDrawer(lvDrawer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }


    private FragmentActivity getActivity() {
        return (FragmentActivity) getContext();
    }

    private ActionBar getSupportActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    private void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    private class FragmentDrawerItemListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectDrawerItem(position);
        }
    }

    private class FragmentNavItem {
        private Class<? extends Fragment> fragmentClass;
        private String title;
        private Bundle fragmentArgs;

        public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass) {
            this(title, fragmentClass, new Bundle());
        }

        public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass, Bundle args) {
            this.fragmentClass = fragmentClass;
            this.fragmentArgs = args;
            this.title = title;
        }

        public Class<? extends Fragment> getFragmentClass() {
            return fragmentClass;
        }

        public String getTitle() {
            return title;
        }

        public Bundle getFragmentArgs() {
            return fragmentArgs;
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(getActivity(), /* host Activity */
                this, /* DrawerLayout object */
                R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description for accessibility */
                R.string.drawer_close /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                // setTitle(getCurrentTitle());
                // call onPrepareOptionsMenu()
                //getActivity().supportInvalidateOptionsMenu();
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                // setTitle("Navigate");
                // call onPrepareOptionsMenu()
                //getActivity().supportInvalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }
        };
    }

    public boolean isDrawerOpen() {
        return isDrawerOpen(lvDrawer);
    }

    public void setUsers(UserList<User> users) {
        this.users = users;
    }


}