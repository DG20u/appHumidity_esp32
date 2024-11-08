package com.example.idgs_9a11_sd_24_a2p2_navdrawer.ui.humedad;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.idgs_9a11_sd_24_a2p2_navdrawer.databinding.FragmentHumidityBinding;
import com.example.idgs_9a11_sd_24_a2p2_navdrawer.ui.humedad.HumidityViewModel;


public class HumidityFragment extends Fragment {

    private FragmentHumidityBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HumidityViewModel HumidityViewModel =
                new ViewModelProvider(this).get(HumidityViewModel.class);

        binding = FragmentHumidityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHumidity;
        HumidityViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}