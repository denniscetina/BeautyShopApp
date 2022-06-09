package com.example.beautyshopapp.ui.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beautyshopapp.R;
import com.example.beautyshopapp.databinding.FragmentAboutBinding;
import com.example.beautyshopapp.databinding.FragmentSlideshowBinding;
import com.example.beautyshopapp.ui.slideshow.SlideshowViewModel;


public class AboutFragment extends Fragment {

    private AboutFragment aboutFragment;
    private FragmentAboutBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Inflate the layout for this fragment
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}