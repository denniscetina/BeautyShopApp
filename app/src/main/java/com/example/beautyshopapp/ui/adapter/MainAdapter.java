package com.example.beautyshopapp.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beautyshopapp.R;
import com.example.beautyshopapp.ui.gallery.DetailsActivity;
import com.example.beautyshopapp.ui.objetc.Productos;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class MainAdapter extends FirebaseRecyclerAdapter<Productos, MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    FirebaseRecyclerOptions<Productos> productosoptions;
    Context context;
    public MainAdapter(@NonNull FirebaseRecyclerOptions<Productos> options) {
        super(options);
        this.productosoptions =options;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, final int position, @NonNull Productos model) {
        //Productos pd = productosList.get(position);


        holder.txt_nombre.setText("Nombre: "+model.getNombre());
        holder.txt_cantidad.setText(model.getCantidad());
        holder.txt_precio.setText(model.getPrecio());
        Picasso.get().load(model.getImagen()).fit().centerCrop().into(holder.img_producto);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailsActivity = new Intent(context, DetailsActivity.class);
                detailsActivity.putExtra("imgINTENT", model.getImagen());
                detailsActivity.putExtra("codINTENT", model.getCodigo());
                detailsActivity.putExtra("nameINTENT", model.getNombre());
                context.startActivity(detailsActivity);

            }
        });
        holder.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img_producto.getContext()).setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setCancelable(true).setGravity(Gravity.TOP).
                setBackgroundColorResId(R.drawable.rounded).create();
                //dialogPlus.show();
                View view1 =dialogPlus.getHolderView();
                EditText nombre = view1.findViewById(R.id.txtNombre);
                EditText cantidad = view1.findViewById(R.id.txtCantidad);
                EditText precio = view1.findViewById(R.id.txtPrecio);
                Button btnSubir = view1.findViewById(R.id.btnUpdate);

                nombre.setText(model.getNombre());
                cantidad.setText(model.getCantidad());
                precio.setText(model.getPrecio());
                dialogPlus.show();

                btnSubir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("nombre",nombre.getText().toString());
                        map.put("cantidad",cantidad.getText().toString());
                        map.put("precio",precio.getText().toString());
                        FirebaseDatabase.getInstance().getReference().child("/ProductosE/Productos")
                                .child(getRef(position).getKey()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(holder.txt_nombre.getContext(), "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(holder.txt_nombre.getContext(), "Error en actualizar datos", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        });
                    }
                });




            }
        });

        holder.borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.txt_nombre.getContext());
                builder.setTitle("Eliminar");
                builder.setMessage("¿Está seguro de eliminar este produto?");
                builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.txt_nombre.getContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                        FirebaseDatabase.getInstance().getReference().child("/ProductosE/Productos").child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(holder.txt_nombre.getContext(), "Cancelado", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_productos,parent,false);
        context = parent.getContext();
        return new myViewHolder(view);

    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView txt_nombre, txt_cantidad, txt_precio;
        ImageView img_producto;
        ImageButton borrar;
        ImageButton editar;
        CardView cardView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nombre = (TextView) itemView.findViewById(R.id.txtnombre);
            txt_cantidad = (TextView)itemView.findViewById(R.id.txtcantidad);
            txt_precio = (TextView)itemView.findViewById(R.id.txtprecio);
            img_producto = (ImageView) itemView.findViewById(R.id.imagenProd);
            cardView = (CardView)itemView.findViewById(R.id.idCardView);
            borrar = (ImageButton)itemView.findViewById(R.id.id_borrar);
            editar =(ImageButton) itemView.findViewById(R.id.id_editar);


        }
    }

    @Override
    public int getItemCount() {
        productosoptions.getSnapshots();
        return super.getItemCount();
    }

}
