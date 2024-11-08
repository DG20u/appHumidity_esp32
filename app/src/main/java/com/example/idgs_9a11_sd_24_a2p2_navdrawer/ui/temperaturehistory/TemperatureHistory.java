package com.example.idgs_9a11_sd_24_a2p2_navdrawer.ui.temperaturehistory;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.idgs_9a11_sd_24_a2p2_navdrawer.R;

public class TemperatureHistory extends Fragment {

    public static TemperatureHistory newInstance() {
        return new TemperatureHistory();
    }

    private TemperatureHistoryViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_temperature_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TemperatureHistoryViewModel.class);
        // TODO: Use the ViewModel
    }

}