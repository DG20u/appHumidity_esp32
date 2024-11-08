package com.example.idgs_9a11_sd_24_a2p2_navdrawer.ui.temp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TemperatureViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public TemperatureViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is temperature fragment fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}