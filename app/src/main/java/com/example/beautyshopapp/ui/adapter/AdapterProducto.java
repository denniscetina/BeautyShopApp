package com.example.beautyshopapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beautyshopapp.ui.gallery.DetailsActivity;
import com.example.beautyshopapp.ui.objetc.Productos;
import com.example.beautyshopapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterProducto  extends FirebaseRecyclerAdapter<AdapterProducto, AdapterProducto.viewHolderProductos> {

    List<Productos> productosList;
    Context context;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseStorage storage;

    public AdapterProducto(@NonNull FirebaseRecyclerOptions<AdapterProducto> options) {
        super(options);

    }

    @NonNull
    @Override
    public viewHolderProductos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://adalari-default-rtdb.firebaseio.com/");
       // databaseReference = firebaseDatabase.getReference("/Papeleria");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_productos,parent,false);
        viewHolderProductos holder = new viewHolderProductos(v);
        context =v.getContext();
        return holder;
    }




    @Override
    protected void onBindViewHolder(@NonNull viewHolderProductos holder, int position, @NonNull AdapterProducto model) {
        Productos pd = productosList.get(position);
        String pd1,pd2;



        holder.txt_nombre.setText("Nombre: "+pd.getNombre());
        holder.txt_cantidad.setText(pd.getCantidad());
        holder.txt_precio.setText(pd.getPrecio());
        Picasso.get().load(pd.getImagen()).into(holder.img_producto);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailsActivity = new Intent(context, DetailsActivity.class);
                detailsActivity.putExtra("imgINTENT", productosList.get(position).getImagen());
                detailsActivity.putExtra("codINTENT", productosList.get(position).getCodigo());
                detailsActivity.putExtra("nameINTENT", productosList.get(position).getNombre());
                context.startActivity(detailsActivity);
            }
        });

        holder.borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Productos").child(pd.getCodigo()).removeValue();
                // productosList.remove(position);
                notifyDataSetChanged();
                notifyItemRangeChanged(position, productosList.size());

                /*Query mQuery = databaseReference.child("Productos").child(pd.getCodigo());
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                           // productosList.remove(position);
                            //notifyItemRemoved(position);
                            //notifyItemRangeChanged(position, productosList.size());
                            //productosList.remove(position);
                            //notifyItemRemoved(position);
                            //notifyItemRangeChanged(position, productosList.size());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/









            }
        });
    }


    @Override
    public int getItemCount() {

        return productosList.size();

    }





    public class viewHolderProductos extends RecyclerView.ViewHolder {

        TextView txt_codigo, txt_nombre, txt_cantidad, txt_precio, txt_proveedor;
        ImageView img_producto;
        ImageButton borrar;
        ImageButton editar;
        CardView cardView;

        public viewHolderProductos(@NonNull View itemView) {
            super(itemView);
            txt_nombre = itemView.findViewById(R.id.txtnombre);
            txt_cantidad = itemView.findViewById(R.id.txtcantidad);
            txt_precio = itemView.findViewById(R.id.txtprecio);
            img_producto = itemView.findViewById(R.id.imagenProd);
            cardView = (CardView)itemView.findViewById(R.id.idCardView);
            borrar = itemView.findViewById(R.id.id_borrar);
            editar =itemView.findViewById(R.id.id_editar);


        }
    }
}
