package com.factoryfree.test.notesfam.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.factoryfree.test.notesfam.activities.HomeActivity;
import com.factoryfree.test.notesfam.R;
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
public class FirstFragment extends Fragment implements DatePickerDialog.OnDateSetListener  {

    private static final String TAG = HomeActivity.class.getSimpleName(); // for TAG Log class
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100; // onActivityResult call request code
    private static final int MEDIA_TYPE_IMAGE = 1;

    public Uri fileUri;
    public File mediaFile;
    private String fileName = null;
    TextView textview;

    public FirstFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ///////////////////////////////////////
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.first_fragment, container, false);

        ////////////////////
        // texview
        TextView textview = (TextView)RootView.findViewById(R.id.date_textview);

        ////////////////////
        // button take picture
        Button button_picture = (Button) RootView.findViewById(R.id.take_picture_button);
        button_picture.setOnClickListener(mButtonPicListener);

        ///////////////////
        // event Datepicker
        Button button_date = (Button) RootView.findViewById(R.id.date_picker_button);
        button_date.setOnClickListener(mButtonDateListener);

        return RootView;
    }


    /////////////////////////////////////////////////////////
    // DATEPICKER
    /////////////////////////////
    private View.OnClickListener mButtonDateListener = new View.OnClickListener() {  // setting listener for user click event
        @Override
        public void onClick(View v) {
            DialogFragment newFragment = new DatePickerFragment(); // creating DialogFragment which creates DatePickerDialog
            newFragment.setTargetFragment(FirstFragment.this, 0);  // Passing this fragment DatePickerFragment.
            newFragment.show(getActivity().getFragmentManager(), "datePicker"); // ededed in Homeactivity
            /* Log.d("getActivity", "= " + getActivity());*/
            /*Log.d("getActivityfragsupp", "= " + getActivity().getSupportFragmentManager());*/ // with import android.support.v4.app.Fragment;
           /* Log.d("getActivityfragman", "= " + getActivity().getFragmentManager());*/
        }
    };
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Log.d("onDateSetfragment","= " + year + month + day);
        textview.setText(year);
    }

    ////////////////////////////////////////////////////////
    // TAKE PICTURE
    ///////////////////////////
    private View.OnClickListener mButtonPicListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

             /** Compose Intent camera*/
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


           /** The Android Camera application saves a full-size photo if you give it a file to save into.
             You must provide a fully qualified file name where the camera app should save the photo.*/

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

            //////////////////////////////////////
            // small Bitmap in the extras, under the key "data".
            // Bundle extras = data.getExtras();
            // Bitmap imageBitmap = (Bitmap) extras.get("data");
            // mImageView.setImageBitmap(imageBitmap);
            // ///////////////

            /**Once Activity was navigated back,
             * case 1 ==== the result will be sent to Activity's onActivityResult with the modified requestCode
             * which will be decoded to original requestCode + Fragment's identity. After that,
             * Activity will send the Activity Result to that Fragment through onActivityResult.
             * And it's all done.
             * case 2 ==== The problem is:
             * Activity could send the result to only the Fragment that has been attached directly
             * to Activity but not the nested one. That's the reason why onActivityResult
             * of nested fragment would never been called no matter what.*/
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
            // internal IS ============== PRIVATE FOR OTHER APP (INTENT) ==================
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
            // import java.time.Instant;
            // Instant timestamp = Instant.now(); FUCK J8 no work on android
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            if (type == MEDIA_TYPE_IMAGE){
                mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
            } else {
                return null;
            }
            return mediaFile;
        }
        else if(isExternalStorageMountedOncomputer()){

            /** Error with Environment.getExternalStorageState()
                media is connected to computer.
                 i want block */
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


    /** Receive a camera Intent Result
    A result code specified by the second activity.
    This is either RESULT_OK if the operation was successful
    or RESULT_CANCELED if the user backed out or the operation failed for some reason.*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                /*Toast.makeText(this, "Image saved to:\n" + fileUri, Toast.LENGTH_LONG).show();*/
                Log.d("onActivityResult", "result " + fileUri);
                try{


                     /** image to byteArray
                     First, you'll need to have the data in byte[] form and then create a ParseFile with it.
                     In many cases when the "context is required", "getContentResolver":
                     we simply need to pass in the instance of the current activity :=)*/

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


                    /** As with ParseObject, there are many variants of the save method you can use depending
                    on what sort of callback and error handling suits you.*/

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


                    /** Finally, after the save completes, you can associate a
                    ParseFile onto a ParseObject just like any other piece of data*/

                    ParseObject Parseobject = new ParseObject("LifePic");
                    Parseobject.put("LifePicture", file);
                    Parseobject.put("blockedAt", new Date());
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



};
