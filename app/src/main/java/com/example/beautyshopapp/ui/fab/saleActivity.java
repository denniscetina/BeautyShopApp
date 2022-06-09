package com.example.beautyshopapp.ui.fab;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.beautyshopapp.R;
import com.example.beautyshopapp.databinding.ActivityMainBinding;
import com.example.beautyshopapp.ui.objetc.Cita;
import com.example.beautyshopapp.ui.objetc.Venta;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class saleActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private TextView clienteSale;
    private EditText conceptoSale;
    private TextView fechaSale;
    private EditText valorSale;
    private TextView metodoPSale;
    private DatabaseReference databaseReference, databaseReference1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        fechaSale = (TextView)findViewById(R.id.fechaSale);
        clienteSale = (EditText)findViewById(R.id.clienteSale);
        valorSale = (EditText) findViewById(R.id.valorSale);
        conceptoSale = (EditText) findViewById(R.id.conceptoSale);
        metodoPSale = (TextView) findViewById(R.id.metodoPSale);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://beautyshopapp-4ac0a-default-rtdb.firebaseio.com");
        databaseReference = firebaseDatabase.getReference("Ventas");
        databaseReference1 = firebaseDatabase.getReference("Citas");



    }

    public void openCalendarr(View view) {
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

                fechaSale.setText(date);
            }
        },year,month,day);
        datePickerDialog.show();

    }

    public void subirVenta(View view) {
        String concepto = conceptoSale.getText().toString();
        String valor = clienteSale.getText().toString();

        if(!TextUtils.isEmpty(concepto)||!TextUtils.isEmpty(valor)){
            uploadDataVenta();
        }

    }

    private void uploadDataVenta() {
        /*this.idVenta = idVenta;
        this.clienteventa = clienteventa;
        this.fechaVenta = fechaVenta;
        this.valorVenta = valorVenta;
        this.conceptoVenta = conceptoVenta;
        this.metodoPago = metodoPago;*/
        String datos1 = clienteSale.getText().toString();
        String datos2 = fechaSale.getText().toString();
        String datos3 = valorSale.getText().toString();
        String datos4 = conceptoSale.getText().toString();
        String datos5 = metodoPSale.getText().toString();
        String id = databaseReference.push().getKey();
        Venta venta = new Venta(id,datos1,datos2,datos3,datos4,datos5);
        databaseReference.child(datos1+" Venta").setValue(venta);
        Toast.makeText(getApplicationContext(),"Éxito al subir datos",Toast.LENGTH_SHORT).show();
        //String key = databaseReference1.push().getKey();



    }
}