package com.example.idgs_9a11_sd_24_a2p2_navdrawer.ui.bondeddevices;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idgs_9a11_sd_24_a2p2_navdrawer.SharedViewModel;
import com.example.idgs_9a11_sd_24_a2p2_navdrawer.databinding.FragmentBondedDevicesBinding;

import com.example.idgs_9a11_sd_24_a2p2_navdrawer.R;

import java.util.Set;

public class BondedDevicesFragment extends Fragment {
    private SharedViewModel sharedViewModel;
    private final String TAG="Bonded Devices";
    private BluetoothAdapter mBTAdapter;
    private ArrayAdapter mPairedDevicesArrayAdapter;
    private FragmentBondedDevicesBinding binding;
    private BondedDevicesViewModel mViewModel;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBondedDevicesBinding.inflate(inflater, container, false);
        View root=binding.getRoot();

        return root;
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel=new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        VerificarEstadoBT();
        mPairedDevicesArrayAdapter=new ArrayAdapter(getContext(), R.layout.found_devices);
        binding.lvBonded.setAdapter(mPairedDevicesArrayAdapter);
        binding.lvBonded.setOnItemClickListener(mDeviceClickListener);
        mBTAdapter=BluetoothAdapter.getDefaultAdapter();
        @SuppressLint("MissingPermission") Set<BluetoothDevice> pairedDevices=mBTAdapter.getBondedDevices();
        if(pairedDevices.size()>0){
            for(BluetoothDevice device: pairedDevices){
                mPairedDevicesArrayAdapter.add(device.getName()+"\n"+device.getAddress());
            }
        }
    }
    private AdapterView.OnItemClickListener mDeviceClickListener=(av, v, arg2, arg3)->{
        String info=((TextView) v).getText().toString();
        sharedViewModel.setAddress(info.substring(info.length()-17));
        sharedViewModel.getAddress().observe(getViewLifecycleOwner(), binding.tvBonded::setText);
    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        sharedViewModel=null;
    }
    private void VerificarEstadoBT() {
        mBTAdapter=BluetoothAdapter.getDefaultAdapter();
        if(mBTAdapter==null){
            Toast.makeText(getContext(), "Bluetooth is not supported by this device", Toast.LENGTH_SHORT).show();
        }else{
            if(mBTAdapter.isEnabled()){
                Log.d(TAG, "..Bluetooth Activado");
            }else{
                Intent enableIntent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, 1);
            }
        }
    }

}