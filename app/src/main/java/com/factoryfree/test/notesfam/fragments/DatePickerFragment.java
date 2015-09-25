package com.factoryfree.test.notesfam.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by test on 24/09/2015.
 */
public class DatePickerFragment extends DialogFragment  {
    private DatePickerDialog.OnDateSetListener dateSetListener; // listener object to get calling fragment listener
    private DatePickerDialog myDatePicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        dateSetListener = (DatePickerDialog.OnDateSetListener)getTargetFragment(); // getting passed fragment
        // Create a new instance of DatePickerDialog and return it
        myDatePicker = new DatePickerDialog(getActivity(), dateSetListener , year, month, day);

        // Create a new instance of DatePickerDialog and return it
        return myDatePicker;
    }

}
