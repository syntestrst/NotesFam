package com.factoryfree.test.notesfam.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.factoryfree.test.notesfam.R;

/**
 * Created by test on 19/09/2015.
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    // Your holder should contain a member variable
    // for any view that will be set as you render a row
    public TextView nameTextView;
    public Button messageButton;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ViewHolder(View itemView) {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.
        super(itemView);

        nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
        messageButton = (Button) itemView.findViewById(R.id.message_button);
    }
}


