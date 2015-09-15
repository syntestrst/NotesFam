package com.factoryfree.test.notesfam.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.factoryfree.test.notesfam.HomeActivity;
import com.factoryfree.test.notesfam.R;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by test on 07/09/2015.
 */
public class FirstFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = HomeActivity.class.getSimpleName(); // for TAG Log class
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100; // onActivityResult call request code
    private static final int MEDIA_TYPE_IMAGE = 1;

    public Uri fileUri;
    public File mediaFile;
    private String fileName = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Parse.initialize(this.getActivity()); // init SDK Parse*/


        LinearLayout mLinearLayout = (LinearLayout) inflater.inflate(R.layout.first_fragment,
                container, false);
        Button button = (Button) mLinearLayout.findViewById(R.id.button);
        button.setOnClickListener(mListenner);
        /*return inflater.inflate(R.layout.first_fragment, container, false);*/
        return mLinearLayout;
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
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

            //////////////////////////////////////
            // small Bitmap in the extras, under the key "data".
            // Bundle extras = data.getExtras();
            // Bitmap imageBitmap = (Bitmap) extras.get("data");
            // mImageView.setImageBitmap(imageBitmap);
            // ///////////////

            ////////////////////////////////////
            // Start camera Intent (Implicit MediaStore.ACTION_IMAGE_CAPTURE)
            ////////////////////////////
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    };


    /** Create a file Uri for saving an image or video */
    private Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private File getOutputMediaFile(int type){

        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        if(isExternalStorageMounted()){

            // This location works best if you want the created images to be shared
            // between applications and persist after your app has been uninstalled.
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");

            // getFilesDir() Returns a File representing an internal directory for your app.
            // To create a new file in one of these directories, you can use the File() constructor.
            // internal IS ============== PRIVATE FOR OTHER APP ==================
            /*Context context = this;
            File mediaStorageDir = new File(context.getFilesDir(), "LivePic");
            Log.d(TAG, "result_fileuri=" + fileUri);*/

            // Create the storage directory if it does not exist
            if (! mediaStorageDir.exists()){
                if (! mediaStorageDir.mkdirs()){
                    Log.d("MyCameraApp", "failed to create directory");
                    return null;
                }
            }
            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            if (type == MEDIA_TYPE_IMAGE){
                mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
            } else {
                return null;
            }
            return mediaFile;
        }
        else if(isExternalStorageMountedOncomputer()){
            ////////////////////////////////////
            // Error with Environment.getExternalStorageState()
            // media is connected to computer.
            // i want block.
            //////////////////////////
            Log.e("MountedAtcomputer", "external storage is connected at computer");
            return null;
        }
        else{
            // Error with Environment.getExternalStorageState()
            // media is not available.
            Log.e("StorageMounted", "external storage not available");
            return null;
        }

    }

    /** Checks if external storage is available for read and write */
    private boolean isExternalStorageMounted(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

    /** Checks if external storage the media is being shared (connected to a computer)
     *
     * */
    private boolean isExternalStorageMountedOncomputer(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_SHARED.equals(state)){
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    ////////////////////////////////////
    // Receive a camera Intent Result
    // A result code specified by the second activity.
    // This is either RESULT_OK if the operation was successful
    // or RESULT_CANCELED if the user backed out or the operation failed for some reason.
    ////////////////////////////
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                /*Toast.makeText(this, "Image saved to:\n" + fileUri, Toast.LENGTH_LONG).show();*/
                Log.d("onActivityResult", "result " + fileUri);
                try{

                    ///////////////////////////////////////
                    // image to byteArray
                    // First, you'll need to have the data in byte[] form and then create a ParseFile with it.
                    // In many cases when the "context is required", "getContentResolver":
                    // we simply need to pass in the instance of the current activity :=)
                    ////////////////
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream(); //Creates a new byte array output stream. The buffer capacity is initially 32 bytes, though its size increases if necessary.
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray(); // Creates a newly allocated byte array.
                    stream.close();

                    // URI to String for name file in server
                    String scheme = fileUri.getScheme();
                    if (scheme.equals("file")) {
                        fileName = fileUri.getLastPathSegment();
                    }
                    ParseFile file = new ParseFile(fileName, byteArray);

                    // ///////////////////////////////////////
                    // As with ParseObject, there are many variants of the save method you can use depending
                    // on what sort of callback and error handling suits you.
                    ///////////////
                    file.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {
                            // Handle success or failure here ...
                            mediaFile.delete();
                        }
                    }, new ProgressCallback() {

                        public void done(Integer percentDone) {
                            // Update your progress spinner here. percentDone will be between 0 and 100.
                        }
                    });

                    ///////////////////////////////////////
                    // Finally, after the save completes, you can associate a
                    // ParseFile onto a ParseObject just like any other piece of data
                    ///////////////
                    ParseObject Parseobject = new ParseObject("LivePic");
                    Parseobject.put("livepic", file);
                    /*Parseobject.pin("updateAt", new Date());*/
                    Parseobject.saveInBackground();
                }
                catch(FileNotFoundException e){
                    e.printStackTrace();
                }
                catch(IOException e){
                    e.printStackTrace();
                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
                Log.d(TAG, "here");
            }
        }
    }
}
