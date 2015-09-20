package com.factoryfree.test.notesfam.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.factoryfree.test.notesfam.R;
import com.factoryfree.test.notesfam.models.LivePic;

import java.util.List;

/**
 * Created by test on 19/09/2015.
 */
 /** Create the basic adapter extending from RecyclerView.Adapter
     Note that we specify the custom ViewHolder which gives us access to our view
     This adapter must extend a class
     called RecyclerView.Adapter passing our class,
     that implements the ViewHolder pattern*/

public class LivePicsAdapter extends RecyclerView.Adapter<ViewHolder> {

     /**
      We now have to override two methods so that we can implement our logic:
      onCreateViewHolder is called whenever a new instance of our ViewHolder
      class is created, and onBindViewHolder is called when the SO binds the
      view with the data -- or, in other words, the data is shown in the UI.*/

     // Store a member variable
     private List<LivePic> mLivePics;
     // Pass  array into the constructor
     public LivePicsAdapter(List<LivePic> livepics) {
         mLivePics = livepics;
     }

     // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View livepicView = inflater.inflate(R.layout.item_livepic, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(livepicView);
        return viewHolder;
    }

     // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // Get the data model based on position
        LivePic livepic = mLivePics.get(position);

        // Set item views based on the data model
        TextView textView = viewHolder.nameTextView;
        textView.setText(livepic.content);
        Log.d("onBind", "test" + livepic.content);

    }
     @Override
     public int getItemCount() {
         return mLivePics.size();
     }


     // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access

}
