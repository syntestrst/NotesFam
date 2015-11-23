package com.factoryfree.test.notesfam.activities;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.factoryfree.test.notesfam.R;

/**
 * Created by test on 29/08/2015.
 */

public class LoginActivity extends Activity {



    private CallbackManager mcallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         /** Initialize the SDK before executing any other operations,
         especially, if you're using Facebook UI elements.*/
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        // Creates an instance of CallbackManager
        mcallbackManager = CallbackManager.Factory.create();

        /**  This class manages login and permissions for Facebook.
        Getter for the login manager getInstance.
        Registers a login callback to the given callback manager with registerCallback
        mcallbackManager	The callback manager that will encapsulate the callback.
        callback	The login callback that will be called on login completion.*/
        LoginManager.getInstance().registerCallback(mcallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("onSuccess", "onSuccess");
                updateUI();
            }

            @Override
            public void onCancel() {
                Log.d("onCancel", "onCancel");
                updateUI();
            }

            @Override
            public void onError(FacebookException exception) {
                if (exception instanceof FacebookAuthorizationException) {
                    Log.d("onError", "onError");
                    showAlert();
                }
                updateUI();
            }

            private void showAlert() {
                new AlertDialog.Builder(
                        LoginActivity.this)
                        .setTitle(R.string.cancelled)
                        .setMessage(R.string.permission_not_granted)
                        .show();
            }
        });

        setContentView(R.layout.activity_login);

    }

    private void updateUI() {

        Boolean token = AccessToken.getCurrentAccessToken() != null;
        Profile profile = Profile.getCurrentProfile();

        if (token && profile != null) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {

        }
    }
    /** The CallbackManager manages the callbacks into the FacebookSdk
     from an Activity's or Fragment's onActivityResult() method.*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        mcallbackManager.onActivityResult(requestCode, resultCode, data);

    }

}













