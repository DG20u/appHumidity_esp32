package com.example.idgs_9a11_sd_24_a2p2_navdrawer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.idgs_9a11_sd_24_a2p2_navdrawer.SharedViewModel;
import com.example.idgs_9a11_sd_24_a2p2_navdrawer.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private SharedViewModel sharedViewModel;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textHome;
        sharedViewModel=new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getDataIn().observe(getViewLifecycleOwner(), textView::setText);
        sharedViewModel.getAddress().observe(getViewLifecycleOwner(), binding.tvAddress ::setText);


        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}