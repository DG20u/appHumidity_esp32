package com.example.idgs_9a11_sd_24_a2p2_navdrawer.ui.humidityhistory;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.idgs_9a11_sd_24_a2p2_navdrawer.R;

public class HumidityHistory extends Fragment {

    private HumidityHistoryViewModel mViewModel;

    public static HumidityHistory newInstance() {
        return new HumidityHistory();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_humidity_history, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HumidityHistoryViewModel.class);
        // TODO: Use the ViewModel
    }

}