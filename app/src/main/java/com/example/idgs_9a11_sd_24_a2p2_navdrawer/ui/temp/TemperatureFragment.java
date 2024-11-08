package com.example.idgs_9a11_sd_24_a2p2_navdrawer.ui.temp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.idgs_9a11_sd_24_a2p2_navdrawer.databinding.FragmentTemperatureBinding;


public class TemperatureFragment extends Fragment {

    private FragmentTemperatureBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
          ViewGroup container, Bundle savedInstanceState) {
        TemperatureViewModel temperatureViewModel =
                new ViewModelProvider(this).get(TemperatureViewModel.class);

        binding = FragmentTemperatureBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTemperature;
        temperatureViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}