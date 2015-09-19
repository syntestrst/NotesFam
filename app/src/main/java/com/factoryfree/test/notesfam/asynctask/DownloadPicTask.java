package com.factoryfree.test.notesfam.asynctask;

import android.os.AsyncTask;

/**
 * Created by test on 17/09/2015.
 */
public class DownloadPicTask extends AsyncTask {

    // Runs on the UI thread before doInBackground
    // Good for toggling visibility of a progress indicator
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    // Some long-running task
    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }


    // Executes whenever publishProgress is called from doInBackground
    // Used to update the progress indicator
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(Object o) {
        super.onCancelled(o);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
