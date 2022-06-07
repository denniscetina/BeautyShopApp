package com.example.beautyshopapp.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beautyshopapp.R;
import com.example.beautyshopapp.databinding.FragmentHomeBinding;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private TextView datestring;
    private ImageView btnImageCalendar;
    private CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatMonth;
    private CardView cardView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
        datestring = (TextView) binding.dateCalendar;
        cardView=(CardView)binding.register;
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        //datestring = (TextView) binding.dateString;
        //  btnImageCalendar = (ImageView)binding.btnImageCalendar;

        // btnImageCalendar.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //   public void onClick(View view) {
        //      openCalendar();
        //  }
        // });
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZZZ");
        Date gmt = null;
        try {
            gmt = formatter.parse("2022-06-17T18:23:20+0000");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millisecondsSinceEpoch0 = gmt.getTime();
        String asString = formatter.format(gmt);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
        Date date = new Date();

        String fecha = dateFormat.format(date);
        datestring.setText(fecha);

        compactCalendarView = (CompactCalendarView) binding.compactcalendarView;
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        Event ev1 = new Event(Color.GREEN, millisecondsSinceEpoch0, "Some extra data that I want to store.");
        compactCalendarView.addEvent(ev1);
        Event ev2 = new Event(Color.YELLOW, millisecondsSinceEpoch0, "I have a car.");
        compactCalendarView.addEvent(ev2);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                //Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
                Toast.makeText(getContext(), "Day was clicked: " + dateClicked + " with events " + events, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                String str = dateFormatMonth.format(firstDayOfNewMonth);
                //Toast.makeText(getContext(), "Month was scrolled to: " + str, Toast.LENGTH_SHORT).show();
                datestring.setText(str);
            }
        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }

    public void openCalendar() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date = i2 + "/" + i1 + "/" + i;
                datestring.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}