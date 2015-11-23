package com.factoryfree.test.notesfam.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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


    // These variable are destroyed along with Activity
    // need restore with onSaveInstanceState and onRestoreInstanceState
    // i'm in fragment.
    private int someVarA;
    private String someVarB;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initiate the background task ASyncTask
        ParseTask asyncTask = new ParseTask();
        asyncTask.execute();
        Log.d("onCreaFrag", "start, asynctask");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View RootView = inflater.inflate(R.layout.second_fragment, container, false);

        // Lookup the recyclerview in activity layout
        RecyclerView rvLivePics = (RecyclerView) RootView.findViewById(R.id.rvLivePic);
        RecyclerView.ItemDecoration itemdecoration;

        rvLivePics.setHasFixedSize(true);

        // Set layout manager to position the items
        rvLivePics.setLayoutManager(new LinearLayoutManager(getActivity()));

        // add listenner touch event for recyclerview
        rvLivePics.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        // Create adapter
        adapter = new LivePicsAdapter(data);

        // Attach the adapter to the recyclerview to populate items
        rvLivePics.setAdapter(adapter);

        return RootView;
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
            ParseQuery<ParseObject> query = ParseQuery.getQuery("LifePic");
            try{
                List<ParseObject> queryResult = query.find();

                for(ParseObject po: queryResult){
                    LivePic livePic = new LivePic();
                    livePic.developpAt = po.getDate("developpAt");
                    /////////////////////////////////////
                    // TODO:
                    // if developpAt < current time;
                    // get file lifePicture on parse.com
                    // need logic in view if lifePicture == null don't show in cardlist
                    ///////////////////////////////////////////////////
                    // TODO:
                    // implements item click in card list
                    // bind with view holder in cardList
                    // if item is clicked start new view (Activity or fragment)
                    // show detail view with lifePicture with transition animation  cardList
                    // if Date < current time = show detail view lifePicture and options to save gallery android.
                    // if Date > current time = show detail view with,
                    // OPTIONS = comment picture
                    // OPTIONS = gift send to friend ,i don't know at the moment INTENT, or redirect play store
                    //
                    ///////////////////////////////////////////////////////////////////////////////////
                    // TODO:
                    // design cardList
                    // design detail detail view
                    // desing LOGIN with parse user
                    //////////////////////////////////////////////////////////////////////////////////////////////
                    // TODO:
                    // need User parse model for linked with my classes
                    // implements logic with login parse
                    // forget password, login,
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

   /* @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("someVarA", someVarA);
        outState.putString("someVarB", someVarB);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        someVarA = savedInstanceState.getInt("someVarA");
        someVarB = savedInstanceState.getString("someVarB");
    }*/
}
