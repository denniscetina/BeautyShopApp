package com.example.beautyshopapp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.beautyshopapp.R;
import com.example.beautyshopapp.databinding.FragmentGalleryBinding;
import com.example.beautyshopapp.ui.adapter.MainAdapter;
import com.example.beautyshopapp.ui.home.RegisterActivity;
import com.example.beautyshopapp.ui.objetc.Productos;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    public RecyclerView recyclerVieww;
    SearchView searchView;
    MainAdapter mainAdapter;
    Button subirPr;
    ArrayList<Productos> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        searchView = (SearchView)binding.searchView;

        subirPr = (Button)binding.crearProd;
        recyclerVieww = (RecyclerView)binding.recyclerViewProduct;
        recyclerVieww.setLayoutManager(new LinearLayoutManager(getContext()));

        subirPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EntryProductActivity.class);
                startActivity(intent);
            }
        });
        FirebaseRecyclerOptions<Productos> options =
                new FirebaseRecyclerOptions.Builder<Productos>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("/ProductosE/Productos"), Productos.class)
                        .build();


        mainAdapter = new MainAdapter(options);
        recyclerVieww.setAdapter(mainAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //buscar(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                buscar(newText);
                return false;
            }
        });
        return root;
    }
    public void buscar(String s) {
        FirebaseRecyclerOptions<Productos> optionsSearch =
                new FirebaseRecyclerOptions.Builder<Productos>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("/ProductosE/Productos")
                                .orderByChild("nombre").startAt(s).endAt(s+"\uf8ff"), Productos.class)
                        .build();
        mainAdapter = new MainAdapter(optionsSearch);
        mainAdapter.startListening();
        recyclerVieww.setAdapter(mainAdapter);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainAdapter.stopListening();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        mainAdapter.startListening();

    }
}