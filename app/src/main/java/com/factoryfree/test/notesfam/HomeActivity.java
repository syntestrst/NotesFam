package com.factoryfree.test.notesfam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by test on 30/08/2015.
 */

public class HomeActivity extends Activity{


    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100; // onActivityResult call request code
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(mListenner);

    }


    private View.OnClickListener mListenner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ////////////////////////////////////
            // Compose Intent camera
            ////////////////////////////

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ///////////////////////////////////////
            //The Android Camera application saves a full-size photo if you give it a file to save into.
            // You must provide a fully qualified file name where the camera app should save the photo.
            ///////////////////
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image

            //////////////////////////////////////
            // small Bitmap in the extras, under the key "data".
            // Bundle extras = data.getExtras();
            // Bitmap imageBitmap = (Bitmap) extras.get("data");
            // mImageView.setImageBitmap(imageBitmap);
            // ///////////////
            /*intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name*/


            ////////////////////////////////////
            // Start camera Intent (Implicit MediaStore.ACTION_IMAGE_CAPTURE)
            ////////////////////////////
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        }
    };
            ////////////////////////////////////
            // Receive a camera Intent Result
            // A result code specified by the second activity.
            // This is either RESULT_OK if the operation was successful
            // or RESULT_CANCELED if the user backed out or the operation failed for some reason.
            //
            ////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Image saved to:\n" + fileUri, Toast.LENGTH_LONG).show();
                Log.d("FileUri","result " + fileUri);

                if(fileuri not null){
                    // Encode crypt and upload at class on parse.com
                    if(upload call back ok){
                        // delete on Environment.getExternalStoragePublicDirectory
                    }
                    else{
                        // Error upload on parse.com class
                    }
                }
                else{
                    // Error fileuri
                }
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
                Log.d("cas2", "here");
            }
        }
    }


    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        // checl later: TODO
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }
}
