package com.example.beautyshopapp.ui.fab;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.beautyshopapp.R;
import com.example.beautyshopapp.databinding.ActivityMainBinding;

import java.util.Calendar;

public class saleActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TextView datePick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        datePick = (TextView)findViewById(R.id.datepick);
    }

    public void openCalendarr(View view) {   Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date = i2 + "/" + i1 + "/" + i;
                datePick.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();



    }
}