package com.factoryfree.test.notesfam.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.factoryfree.test.notesfam.R;
import com.factoryfree.test.notesfam.adapters.ContactsAdapter;
import com.factoryfree.test.notesfam.models.Contact;

/**
 * Created by test on 07/09/2015.
 */
public class SecondFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View myView = (View) inflater.inflate(R.layout.second_fragment, container, false);
        // ...
        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) myView.findViewById(R.id.rvLivePic);
        // Create adapter passing in the sample user data
        ContactsAdapter adapter = new ContactsAdapter(Contact.createContactsList(20));
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        // That's all!
        return myView;
    }
}
