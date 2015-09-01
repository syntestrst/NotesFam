package com.factoryfree.test.notesfam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;

/**
 * Created by test on 29/08/2015.
 */
public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 1000;
    private AccessTokenTracker accessTokenTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    private void updateWithToken(AccessToken currentAccessToken) {
        if (currentAccessToken != null) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Explicit intents specify the component to start by name (the fully-qualified class name).
                    You'll typically use an explicit intent to start a component in your own app,
                    because you know the class name of the activity or service you want to start.
                    For example, start a new activity in response to a user action or start a service to download a file
                    in the background.*/
                    Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); //test
                    finish();
                }
            }, SPLASH_TIME_OUT);

        } else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }
}


