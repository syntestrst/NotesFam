package com.factoryfree.test.notesfam.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 19/09/2015.
 */
public class Contact {

    private String mName;
    private boolean mOnline;

    private Contact(String name, boolean online){

    }

    public String getName(){
        return mName;
    }

    public boolean isOnline(){
        return mOnline;
    }

    private static int lastContactId = 0;

    public static List<Contact> createContactsList(int numContacts){
        List<Contact> contacts = new ArrayList<>();

        for(int i =0;i  <=numContacts; i++){
            contacts.add(new Contact("Person " + ++lastContactId, i <= numContacts / 2));
        }
        return contacts;
    }
}
