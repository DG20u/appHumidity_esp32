package com.example.idgs_9a11_sd_24_a2p2_navdrawer;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


import com.example.idgs_9a11_sd_24_a2p2_navdrawer.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="Main Activity";
    private BluetoothAdapter mBtAdapter;
    private static Handler bluetoothIn;

    final int handlerState = 0;

    private BluetoothDevice device = null;

    private StringBuilder dataStringIn = new StringBuilder();

    private ConnectedThread MyConnectionBT;

    private BluetoothAdapter btAdapter = null;

    private BluetoothSocket btSocket = null;

    private SharedViewModel sharedViewModel;


    private StringBuilder DataStringIN = new StringBuilder();

    private static final UUID BTMODULEUUID= UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel=new ViewModelProvider(this).get(SharedViewModel.class);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_temperature, R.id.nav_humidity, R.id.nav_bonded, R.id.nav_th,R.id.nav_hh)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    dataStringIn.append((String) msg.obj);
                    int endOfLineIndex = dataStringIn.indexOf("#");

                    if (endOfLineIndex > 0) {
                        String dataInPrint = dataStringIn.substring(0, endOfLineIndex + 1);

                        // String[] dataSub = dataInPrint.split("$");
                        // Log.i(TAG, dataSub[0]);
                        // Log.i(TAG, dataSub[1]);
                        // Log.i(TAG, dataSub[2]);
                        // Log.i(TAG, dataSub[3]);
                        // sharedViewModel.setTemp(Integer.valueOf(dataSub[1]));
                        // sharedViewModel.setHum(Integer.valueOf(dataSub[2]));

                        sharedViewModel.setDataIn(dataInPrint);
                        dataStringIn.delete(0, dataStringIn.length());
                    }
                }
            }
        };
        sharedViewModel.getAddress().observe(this, new Observer<String>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onChanged(String s) {
                String address=sharedViewModel.getAddress().getValue().toString();
                if(!address.equals("no address")){
                    device=mBtAdapter.getRemoteDevice(address);
                    try {
                        btSocket = createBluetoothSocket(device);
                        Toast.makeText(getBaseContext(), "\"onResume\" creación del socket exitosa", Toast.LENGTH_SHORT).show();
                    }catch (IOException e){
                        Toast.makeText(getBaseContext(), "La creación del socket falló", Toast.LENGTH_SHORT).show();
                    }
                    try{
                        btSocket.connect();
                    }catch (IOException e){
                        //Toast.makeText(getBaseContext(), "La conexion al socket falló", Toast.LENGTH_SHORT).show();
                        try{
                            btSocket.close();
                        }catch (IOException e1){
                            Toast.makeText(getBaseContext(), "El cierre del socket falló", Toast.LENGTH_SHORT).show();
                        }
                    }
                    MyConnectionBT=new ConnectedThread(btSocket);
                    MyConnectionBT.start();
                }else{
                    Toast.makeText(getBaseContext(), "No address", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();
        VerificarEstadoBT();
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            btSocket.close();
            Toast.makeText(this, "\"onPause\", se cerró BT", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "El cierre del socket falló", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @SuppressLint("MissingPermission")
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    @SuppressLint("MissingPermission")
    private void VerificarEstadoBT() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            if (mBtAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth Activado....");
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "No se pudo obtener información", Toast.LENGTH_SHORT).show();
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        public void write(String input) {
            try {
                mmOutStream.write(input.getBytes());
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "El envío de datos falló", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

}