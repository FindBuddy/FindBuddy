package com.findbuddy.findbuddy.activity;

import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.findbuddy.findbuddy.R;
import com.findbuddy.findbuddy.fragments.ListViewFragment;
import com.findbuddy.findbuddy.fragments.MapViewFragment;
import com.findbuddy.findbuddy.fragments.OnFragmentInteractionListener;
import com.findbuddy.findbuddy.models.UserList;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class MainActivity extends ActionBarActivity implements OnFragmentInteractionListener {

    private FragmentNavigationDrawer dlDrawer;
    private static String sUserId;

    private Handler handler = new Handler();
    private UserList<ParseUser> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sUserId = ParseUser.getCurrentUser().getUsername();


        dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawer_layout);
        // Setup drawer view
        dlDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.lvDrawer),
                R.layout.drawer_nav_item, R.id.flContent);
        // Add nav items
        dlDrawer.addNavItem("Friends Map", "Friends Map", MapViewFragment.class);
        dlDrawer.addNavItem("Friends List", "Friends List", ListViewFragment.class);
        // Select default
        if (savedInstanceState == null) {
            dlDrawer.selectDrawerItem(0);
        }

        usersList = new UserList<>();
        //setup parse
        //ParseObject.registerSubclass(User.class);

        receiveUsersList();
        //handler.postDelayed(runnable, 1000);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            receiveUsersList();
            //handler.postDelayed(this, 100);
        }
    };

/*
    private void startWithCurrentUser() {
        sUserId = User.getCurrentUser().getObjectId();
    }

    // Create an anonymous user using ParseAnonymousUtils and set sUserId
    private void login() {
        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d("DEBUG", "Anonymous login failed.");
                } else {
                    startWithCurrentUser();
                }
            }
        });
    }
*/
    private void sendCurrentUserInfo(Location myLocation) {
        ParseQuery query = ParseQuery.getQuery(ParseUser.class);
        //List<ParseUser> users = null;
        ParseUser user = ParseUser.getCurrentUser();

        double lat = myLocation.getLatitude();
        double lon = myLocation.getLongitude();
        float accuracy = myLocation.getAccuracy();


/*
        try {
            users = query.whereEqualTo("userId", sUserId).find();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(users == null || users.isEmpty()) {
            //user = new User(sUserId, sUserId, lat, lon, accuracy);
            user =


            //user = User.getCurrentUser();


        } else {
            user = users.get(0);
            user.setAccuracy(accuracy);
            user.setLon(lon);
            user.setLat(lat);
        }

*/
        user.put("accuracy", accuracy);
        user.put("lon", lon);
        user.put("lat", lat);

        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Toast.makeText(MainActivity.this, "Successfully created user on Parse", Toast.LENGTH_SHORT).show();
                //receiveUsersList();
            }
        });
    }

    private void receiveUsersList() {
        // Construct query to execute
        //ParseQuery<ParseUser> query = ParseQuery.getQuery(ParseUser.class);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.orderByAscending("createdAt");
        // Execute query for messages asynchronously
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> user, ParseException e) {
                if (e == null) {
                    usersList.clear();
                    usersList.addAll(user);
                    dlDrawer.setUsers(usersList);
                } else {
                    Log.d("message", "Error: " + e.getMessage());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content
        if (dlDrawer.isDrawerOpen()) {
            // Uncomment to hide menu items
            // menu.findItem(R.id.mi_test).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (dlDrawer.getDrawerToggle().onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        dlDrawer.getDrawerToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        dlDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
    }

    @Override
    public void sendCurrentLocationToParse(Location myLocation) {

        Toast.makeText(this, "Sending my location to parse", Toast.LENGTH_SHORT).show();
        sendCurrentUserInfo(myLocation);

    }


}
