package com.factoryfree.test.notesfam.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.factoryfree.test.notesfam.R;
import com.factoryfree.test.notesfam.adapters.LivePicsAdapter;
import com.factoryfree.test.notesfam.models.LivePic;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 07/09/2015.
 */
public class SecondFragment extends Fragment {

    final List<LivePic> data = new ArrayList<>();
    private LivePicsAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.second_fragment, container, false);

        // Lookup the recyclerview in activity layout
        RecyclerView rvLivePics = (RecyclerView) myView.findViewById(R.id.rvLivePic);

        // Set layout manager to position the items
        rvLivePics.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create adapter
        adapter = new LivePicsAdapter(data);
        
        // Attach the adapter to the recyclerview to populate items
        rvLivePics.setAdapter(adapter);

        // Initiate the background task ASyncTask
        ParseTask asyncTask = new ParseTask();
        asyncTask.execute();

        return myView;
    }

    public class ParseTask extends AsyncTask<Void, Void, List<LivePic>> {

        // Runs on the UI thread before doInBackground
        // Good for toggling visibility of a progress indicator
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // Some long-running task
        @Override
        protected List<LivePic> doInBackground(Void... voids) {
            //////////////////////////////////////////////////////////////////
            // TODO: implements logic with date when everything work (need design checkbox or select date in UI)
            /////////////////////////////////////////
            ParseQuery<ParseObject> query = ParseQuery.getQuery("comment");
            try{
                List<ParseObject> queryResult = query.find();

                for(ParseObject po: queryResult){
                    LivePic livePic = new LivePic();
                    livePic.content = po.getString("content");
                    data.add(livePic);
                }

            }catch (ParseException e)
            {
                Log.d("ParseExep", "Error: " + e.getMessage());
            }
            return data;
        }

        /**
         * invoked on the UI thread after the background computation finishes.
         * The result of the background computation is passed to this step as a parameter.
         *
         */
        @Override
        protected void onPostExecute(List<LivePic> data) {
            super.onPostExecute(data);
            Log.d("onPostExecute", "data = " + data);
            adapter.notifyDataSetChanged();
            
        }


        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(List<LivePic> livePics) {
            super.onCancelled(livePics);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

}
