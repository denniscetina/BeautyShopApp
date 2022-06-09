package com.example.beautyshopapp.ui.fab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beautyshopapp.R;
import com.example.beautyshopapp.ui.objetc.Gasto;
import com.example.beautyshopapp.ui.objetc.Venta;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class spentActivity extends AppCompatActivity {
    private EditText categoriaSpent;
    private EditText conceptoSpent;
    private TextView fechaSpent;
    private EditText valorSpent;
    private TextView metodoPSpent;
    private Button btnSpent;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spent);
        fechaSpent = (TextView)findViewById(R.id.fechaSpent);
        categoriaSpent = (EditText)findViewById(R.id.categoriaSpent);
        valorSpent = (EditText) findViewById(R.id.valorSpent);
        conceptoSpent = (EditText) findViewById(R.id.conceptoSpent);
        metodoPSpent = (TextView) findViewById(R.id.metodoPSpent);
        btnSpent = (Button)findViewById(R.id.gastoBtn);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://beautyshopapp-4ac0a-default-rtdb.firebaseio.com");
        databaseReference = firebaseDatabase.getReference("Gastos");

        btnSpent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String conceptoo = conceptoSpent.getText().toString();
                String valorr = metodoPSpent.getText().toString();

                if(!TextUtils.isEmpty(conceptoo)||!TextUtils.isEmpty(valorr)){
                    String datos1 = fechaSpent.getText().toString();
                    String datos2 = categoriaSpent.getText().toString();
                    String datos3 = valorSpent.getText().toString();
                    String datos4 = conceptoSpent.getText().toString();
                    String datos5 = metodoPSpent.getText().toString();
                    String id = databaseReference.push().getKey();
                    Gasto gasto = new Gasto(id,datos1,datos2,datos3,datos4,datos5);
                    databaseReference.child(datos2+" gasto").setValue(gasto);
                    Toast.makeText(getApplicationContext(),"Éxito al subir datos",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void openCalendarSpent(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                int dia=i2;
                int mes = i1+1;
                int anio=i;
                String date = anio + "-" + mes + "-" + dia;

                fechaSpent.setText(date);
            }
        },year,month,day);
        datePickerDialog.show();

    }

}