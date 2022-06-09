package com.example.beautyshopapp.ui.gallery;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import com.example.beautyshopapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {


    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseStorage storage;
    private String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView imageView = findViewById(R.id.imageViewDetails);
        TextView textView = findViewById(R.id.txtnombreDetails);
        Picasso.get().load(getIntent().getStringExtra("imgINTENT")).into(imageView);
        textView.setText(getIntent().getStringExtra("nameINTENT"));
        codigo = getIntent().getStringExtra("codINTENT");
    }


    private void deleteProd() {


    }
    public void updateData(ArrayList<ViewModel> viewModels) {
        //items.clear();
        //items.addAll(viewModels);
       // notifyDataSetChanged();
    }
}