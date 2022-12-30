package com.salah.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.salah.util.TimeUtils;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    TextView date;
    TextInputLayout dateInput;

    public DatePickerFragment (TextView date){
        this.date = date;
    }

    public DatePickerFragment (TextInputLayout date){
        this.dateInput = date;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if(date!=null) {
            date.setText(TimeUtils.getFormatedDate(year, ++month, day));
        }
        if(dateInput!=null) {
            dateInput.getEditText().setText(TimeUtils.getFormatedDate(year, ++month, day));
        }

    }
}