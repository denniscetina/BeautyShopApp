package com.example.beautyshopapp.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.beautyshopapp.R;
import com.example.beautyshopapp.ui.adapter.AdapterCitas;
import com.example.beautyshopapp.ui.objetc.Cita;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuotesAppointment extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    AdapterCitas adapterCitas;
    ArrayList<Cita> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_appointment);

        recyclerView = findViewById(R.id.citaList);
        databaseReference = FirebaseDatabase.getInstance().getReference("Citas");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( this));
        list = new ArrayList();
        adapterCitas = new AdapterCitas(this,list);
        recyclerView.setAdapter(adapterCitas);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Cita cita = dataSnapshot.getValue(Cita.class);
                    list.add(cita);

                }
                adapterCitas.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}