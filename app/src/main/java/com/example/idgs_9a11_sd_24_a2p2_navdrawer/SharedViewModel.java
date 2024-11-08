package com.example.idgs_9a11_sd_24_a2p2_navdrawer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Integer> temp=new MutableLiveData<>();
    private MutableLiveData<Integer> hum=new MutableLiveData<>();
    private MutableLiveData<String> dataIn=new MutableLiveData<>();
    private MutableLiveData<String> address= new MutableLiveData<>();
    public SharedViewModel(){
        temp.setValue(10);
        hum.setValue(15);
        dataIn.setValue("No data received");
        address.setValue("no address");
        //address.setValue("7C:87:CE:27:EE:CE");
    }
    public MutableLiveData<String> getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address.setValue(address);
    }
    public MutableLiveData<Integer> getTemp() {
        return temp;
    }
    public void setTemp(int temp) {
        this.temp.setValue(temp);
    }
    public LiveData<String> getDataIn() {
        return dataIn;
    }
    public void setDataIn(String dataIn) {
        this.dataIn.setValue(dataIn);
    }
    public MutableLiveData<Integer> getHum() {
        return hum;
    }
    public void setHum(int hum) {
        this.hum.setValue(hum);
    }
}