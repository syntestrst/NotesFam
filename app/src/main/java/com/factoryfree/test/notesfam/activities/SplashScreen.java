package com.factoryfree.test.notesfam.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.factoryfree.test.notesfam.R;

/**
 * Created by test on 29/08/2015.
 */
public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 1000;
    private AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // The activity is being created.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };
        updateWithToken(AccessToken.getCurrentAccessToken());

    }

    @Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }
    ///////////////////////////////////////////////////
    // Activity RUNNING
    //////////////////////////////////////////////////
    @Override
    protected void onPause() {
        super.onPause();
            // Another activity is taking focus (this activity is about to be "paused").
        /**
         * Activity Paused is completely ALIVE
         * he Activity object is retained in memory, it maintains all state
         * and member information, and remains attached to the window manager),
         * but can be killed by the system in extremely low memory situations.
         * "is called when the device goes to sleep or when a dialog appears"
         * */
    }

    @Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        /** The activity is completely obscured by another activity (the activity is now in the "background").
            A stopped activity is also still alive (the Activity object is retained in memory,
            it maintains all state and member information, but is not attached to the window manager)*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // The activity is about to be destroyed.
    }


    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(intent);
                    /*overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); //test*/
                    finish();
                }
            }, SPLASH_TIME_OUT);

        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }
}


