package com.example.beautyshopapp.ui.fab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.beautyshopapp.R;
import com.example.beautyshopapp.ui.objetc.Cita;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class quoteActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;

    private EditText conceptoQuote;
    private EditText clienteQuote;
    private TextView fechaQuote;

    private TextView horaQuote;
    ProgressDialog progressDialog;
    UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://beautyshopapp-4ac0a-default-rtdb.firebaseio.com");
        databaseReference = firebaseDatabase.getReference("Citas");
        //storage = FirebaseStorage.getInstance();
        //storageReference = storage.getReference();

        conceptoQuote = findViewById(R.id.conceptoQuote);
        clienteQuote= findViewById(R.id.clienteQuote);
        fechaQuote = findViewById(R.id.fechaQuote);
        horaQuote = findViewById(R.id.horaQuote);


    }

    public void openCalendarQuote(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                int dia= Integer.parseInt(twoDigits(i2));
                int mes = i1+1;
                int anio=i;
                String date = anio + "-" + twoDigits(i1+1) + "-" + twoDigits(i2);

                fechaQuote.setText(date);
            }
        },year,month,day);
        datePickerDialog.show();
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    public void uploadQuote(View view) {
        String concepto = conceptoQuote.getText().toString();
        String cliente = clienteQuote.getText().toString();
        String fecha = fechaQuote.getText().toString();
        String hora= horaQuote.getText().toString();
        if(!TextUtils.isEmpty(concepto)||!TextUtils.isEmpty(cliente)||!TextUtils.isEmpty(fecha)
                ||!TextUtils.isEmpty(hora)){
            uploadData();
        }
    }

    private void uploadData() {
        String datos1 = conceptoQuote.getText().toString();
        String datos2 = clienteQuote.getText().toString();
        String datos3 = fechaQuote.getText().toString();
        String datos4 = horaQuote.getText().toString();
        String id = databaseReference.push().getKey();
        Cita cita = new Cita(id,datos2,datos1,datos3,datos4,true);
        databaseReference.child(datos3+" "+datos4).setValue(cita);
        Toast.makeText(getApplicationContext(),"Éxito al subir datos",Toast.LENGTH_SHORT).show();



//        imageRef = storageReference.child("productos/"+datos1+datos2+"."+GetExtension(uri));

    }

    public void openHour(View view) {
        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR);
        int minuto = calendar.get(Calendar.MINUTE);
        //int segundo = calendar.get(Calendar.SECOND);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String dateH = i + ":" + i1;
                horaQuote.setText(dateH);

            }
        },hora,minuto,true);
        timePickerDialog.show();

    }
}