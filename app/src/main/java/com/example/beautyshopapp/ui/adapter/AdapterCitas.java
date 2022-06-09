package com.example.beautyshopapp.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beautyshopapp.R;
import com.example.beautyshopapp.ui.objetc.Cita;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterCitas extends RecyclerView.Adapter<AdapterCitas.MyViewHolder> {

    Context context;
    ArrayList<Cita> list;
    private DatabaseReference databaseReference;

    public AdapterCitas(Context context, ArrayList<Cita> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cita cita = list.get(position);
        holder.nombreC.setText(cita.getCliente());
        holder.conceptoC.setText(cita.getConcepto());
        holder.fechaC.setText(cita.getFecha());
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://beautyshopapp-4ac0a-default-rtdb.firebaseio.com");
        databaseReference = firebaseDatabase.getReference("Ventas");
        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.nombreC.getContext()).setContentHolder(new ViewHolder(R.layout.venta_popup))
                        .setCancelable(true).setGravity(Gravity.TOP).
                                setBackgroundColorResId(R.drawable.rounded).create();
                //dialogPlus.show();
                View view1 =dialogPlus.getHolderView();
                EditText clienteV = view1.findViewById(R.id.txtNombreCliente);
                EditText conceptoV = view1.findViewById(R.id.txtConceptoVenta);
                EditText fechaV = view1.findViewById(R.id.txtFechaVenta);
                EditText metodoPV = view1.findViewById(R.id.txtMetodoPVenta);
                EditText valorV = view1.findViewById(R.id.txtValorVenta);
                Button btnVentaD = view1.findViewById(R.id.btnVentaD);

                clienteV.setText(cita.getCliente());
                conceptoV.setText(cita.getConcepto());
                fechaV.setText(cita.getFecha());

                dialogPlus.show();

                btnVentaD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = databaseReference.push().getKey();
                        Map<String, Object> map = new HashMap<>();
                        map.put("idVenta",id);
                        map.put("clienteVenta",clienteV.getText().toString());
                        map.put("fechaVenta",fechaV.getText().toString());
                        map.put("valorVenta",valorV.getText().toString());
                        map.put("conceptoVenta",conceptoV.getText().toString());
                        map.put("metodoPago",metodoPV.getText().toString());
                        databaseReference.child(clienteV.getText().toString()+" Venta").setValue(map);
                        dialogPlus.dismiss();

                    }
                });




            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView nombreC,fechaC,conceptoC;
        ImageButton borrar;
        ImageButton editar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreC = itemView.findViewById(R.id.nombreCliente);
            fechaC = itemView.findViewById(R.id.fechaCliente);
            conceptoC = itemView.findViewById(R.id.conceptoCliente);
            editar = itemView.findViewById(R.id.imageButtonVenta);
        }
    }
}
