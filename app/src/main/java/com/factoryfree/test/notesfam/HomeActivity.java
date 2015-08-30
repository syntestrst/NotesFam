package com.factoryfree.test.notesfam;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by test on 30/08/2015.
 */

public class HomeActivity extends Activity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        CharSequence text = "Hello Homeactivity!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        setContentView(R.layout.activity_home);
    }
}
