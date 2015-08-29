package com.factoryfree.test.notesfam;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.ProfilePictureView;

public class HomeActivity extends Activity {


   /* private static final String PENDING_ACTION_BUNDLE_KEY = "com.factoryfree.test.notesfam";*/
    private CallbackManager callbackManager;
    private TextView greeting;
    private ProfilePictureView profilePictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //////////////////////////////////////////////
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.
        /////////////////////////////////////
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                updateUI();
            }

            @Override
            public void onCancel() {

                updateUI();
            }

            @Override
            public void onError(FacebookException exception) {
            if( exception instanceof FacebookAuthorizationException){
                showAlert();
                }
                updateUI();
            }
            private void showAlert() {
                new AlertDialog.Builder(
                        HomeActivity.this)
                        .setTitle(R.string.cancelled)
                        .setMessage(R.string.permission_not_granted)
                        .show();
            }
        });

        setContentView(R.layout.activity_home);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void updateUI(){
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {

        } else {

        }
        
    }


























}
