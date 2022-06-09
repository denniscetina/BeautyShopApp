package com.example.beautyshopapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beautyshopapp.R;
import com.example.beautyshopapp.ui.objetc.Cita;

import java.util.ArrayList;

public class AdapterCitas extends RecyclerView.Adapter<AdapterCitas.MyViewHolder> {

    Context context;
    ArrayList<Cita> list;

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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView nombreC,fechaC,conceptoC;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreC = itemView.findViewById(R.id.nombreCliente);
            fechaC = itemView.findViewById(R.id.fechaCliente);
            conceptoC = itemView.findViewById(R.id.conceptoCliente);
        }
    }
}
