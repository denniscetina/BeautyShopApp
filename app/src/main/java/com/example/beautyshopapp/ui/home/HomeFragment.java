package com.example.beautyshopapp.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.beautyshopapp.ui.objetc.Cita;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private TextView datestring;
    private TextView ventaTT;
    private TextView gastoTT;
    private TextView utilidadTT;
    private ImageView btnImageCalendar;
    private CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatMonth;
    private CardView cardView,btnVerCita;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReference1;
    private DatabaseReference databaseReference2;
    private TextView totalV;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
        datestring = (TextView) binding.dateCalendar;
        ventaTT = (TextView) binding.ingresoT;
        gastoTT = (TextView) binding.gastoT;
        utilidadTT = (TextView) binding.utilidadT;
        totalV = (TextView) binding.totalVentas;
        cardView=(CardView)binding.register;
        btnVerCita = (CardView) binding.seeQuotes;
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnVerCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),QuotesAppointment.class);
                startActivity(intent);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        //compactCalendarView.removeAllEvents();
        super.onStart();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date gmt = null;
        try {
            gmt = formatter.parse("2022-06-17");
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

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Citas");
        databaseReference1= FirebaseDatabase.getInstance().getReference().child("Ventas");
        databaseReference2= FirebaseDatabase.getInstance().getReference().child("Gastos");

        ArrayList<String> magazineName = new ArrayList<>();
        ArrayList<String> ventasArray = new ArrayList<>();
        ArrayList<String> gastosArray = new ArrayList<>();

        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                for(DataSnapshot snapshot1: dataSnapshot1.getChildren()){
                    String padre = snapshot1.getKey();
                    String ventasDia = String.valueOf(dataSnapshot1.child(padre).child("valorVenta").getValue());
                    long totalVenta = dataSnapshot1.getChildrenCount();

                    ventasArray.add(ventasDia);
                    totalV.setText(totalVenta+"");
                    //Toast.makeText(getActivity(),totalVenta+"",Toast.LENGTH_SHORT).show();
                    //magazineName.add(value);


                }
                int sum = 0;
                for (int i = 0; i < ventasArray.size(); i++) {
                    sum+= Integer.parseInt(ventasArray.get(i));
                }
                ventaTT.setText(""+sum);
               // Toast.makeText(getActivity(),sum+"d",Toast.LENGTH_SHORT).show();
                int totalutili= Integer.parseInt(ventaTT.getText().toString())-Integer.parseInt(gastoTT.getText().toString());
                utilidadTT.setText(totalutili+"");
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }

        });
        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                for(DataSnapshot snapshot1: dataSnapshot1.getChildren()){
                    String padre = snapshot1.getKey();
                    String gastosDia = String.valueOf(dataSnapshot1.child(padre).child("valorGasto").getValue());
                    long totalGasto = dataSnapshot1.getChildrenCount();

                    gastosArray.add(gastosDia);
                    ;
                    //Toast.makeText(getActivity(),totalVenta+"",Toast.LENGTH_SHORT).show();
                    //magazineName.add(value);


                }
                int sum = 0;
                for (int i = 0; i < gastosArray.size(); i++) {
                    sum+= Integer.parseInt(gastosArray.get(i));
                }
                //ventaTT.setText("$"+sum);
                gastoTT.setText(""+sum);


                // Toast.makeText(getActivity(),sum+"d",Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
            }
        });


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Recorremos todos los hijos debajo de data y obtenemos los ratings
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String padre = snapshot.getKey();
                    String value = String.valueOf(dataSnapshot.child(padre).child("fecha").getValue());
                    String va = String.valueOf(dataSnapshot.child("Ari Yam").child("fecha").getValue());

                    //--Toast.makeText(getActivity(),va,Toast.LENGTH_SHORT).show();
                    magazineName.add(value);


                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date []gmt = new Date[magazineName.size()];
                try {
                    for (int i = 0; i < magazineName.size(); i++) {
                        gmt[i] = formatter.parse(magazineName.get(i));
                        long millisecondsSinceEpoch = gmt[i].getTime();
                        Event fechas = new Event(Color.GREEN, millisecondsSinceEpoch, "Some extra data that I want to store.");
                        compactCalendarView.addEvent(fechas);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
               // Toast.makeText(getContext(), magazineName.toString(), Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                events.size();
                //Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
                Toast.makeText(getActivity(),"Tiene cita con "+events.size()+" personas",Toast.LENGTH_SHORT).show();
               // Toast.makeText(getContext(), "Day was clicked: " + asString + " with events " + events, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                //Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
                String str = dateFormatMonth.format(firstDayOfNewMonth);
                //Toast.makeText(getContext(), "Month was scrolled to: " + str, Toast.LENGTH_SHORT).show();
                datestring.setText(str);
            }
        });


    }

    @Override
    public void onPause() {

        super.onPause();
        compactCalendarView.removeAllEvents();

    }
}