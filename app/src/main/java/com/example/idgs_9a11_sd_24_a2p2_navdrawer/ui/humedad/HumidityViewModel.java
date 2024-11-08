package com.example.idgs_9a11_sd_24_a2p2_navdrawer.ui.humedad;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HumidityViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public HumidityViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Humidity fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}