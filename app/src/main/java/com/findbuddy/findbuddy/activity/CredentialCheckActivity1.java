package com.findbuddy.findbuddy.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.findbuddy.findbuddy.R;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

public class CredentialCheckActivity1 extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ParseUser.logOut();
        // Determine whether the current user is an anonymous user
        if (ParseUser.getCurrentUser() != null && ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            Intent intent = new Intent(this,
                    LoginSignupActivity2.class);
            startActivity(intent);
            finish();
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this,
                        LoginSignupActivity2.class);
                startActivity(intent);
                finish();
            }
        }

    }
}

