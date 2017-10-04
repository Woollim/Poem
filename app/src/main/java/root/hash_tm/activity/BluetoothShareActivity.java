package root.hash_tm.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import root.hash_tm.R;
import root.hash_tm.util.BaseActivity;

/**
 * Created by root1 on 2017. 10. 1..
 */

public class BluetoothShareActivity extends BaseActivity {

    BluetoothAdapter bluetoothManager;
    Button shareButton, sharedButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_share);

        setBluetooth();

        shareButton = (Button)findViewById(R.id.shareButton);
        sharedButton = (Button)findViewById(R.id.sharedButton);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDevicesDialog(bluetoothManager.getBondedDevices(), false);
            }
        });

        sharedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDevicesDialog(bluetoothManager.getBondedDevices(), true);
            }
        });
    }



    private void sendData(OutputStream outputStream, String data){
        try{
            outputStream.write(data.getBytes());
        }catch(Exception e){
            e.printStackTrace();
            showSnack("시를 전송 중에 오류가 발생했습니다.");
        }
    }

    private void getData(final InputStream inputStream){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()){
                    try{
                        int byteAvailable = inputStream.available();
                        byte readBuffer[] = new byte[byteAvailable];
                        inputStream.read(readBuffer);
                        String data = new String(readBuffer);
                        Log.d("get data", "" + data);
                    }catch (Exception e){
                        showSnack("시를 받는 중 오류가 발생했습니다.");
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void setBluetooth(){
        bluetoothManager = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothManager == null){
            showSnack("블루투스를 지원하지 않습니다.");
            finish();
        }else{
            if(!bluetoothManager.isEnabled()){
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 100);
            }else{
                selectDevice(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                selectDevice(true);
            }else{
                showSnack("블루투스를 켜야 시를 공유할 수 있습니다.");
                finish();
            }
        }else if(requestCode == 200){
            selectDevice(false);
        }
    }

    private void selectDevice(boolean goSetting){
        Set<BluetoothDevice> devices = bluetoothManager.getBondedDevices();

        if(goSetting){
            Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            startActivityForResult(intent, 200);
        }else{
            if(devices.size() == 0){
                showSnack("페어링 된 기기가 없습니다.");
                finish();
            }
        }
    }

    private void showDevicesDialog(final Set<BluetoothDevice> devices, final boolean isServer){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("시를 공유할 기기 선택");
        final List<String> deviceList = new ArrayList<>();

        for(BluetoothDevice device : devices){
            deviceList.add(device.getName());
        }

        CharSequence[] devicesName = deviceList.toArray(new CharSequence[deviceList.size()]);

        builder.setItems(devicesName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(isServer){
                    serverConnect(deviceList.get(i));
                }else{
                    connect(getDevice(deviceList.get(i), devices));
                }
            }
        });

        builder.create().show();
    }

    private void serverConnect(String deviceName){
        String uuidStr = "000011001-0000-1000-8000-00805F9B34FB";
        UUID uuid = java.util.UUID.fromString(uuidStr);

        try{
            BluetoothServerSocket blutoothServerSocket = bluetoothManager.listenUsingInsecureRfcommWithServiceRecord(deviceName,uuid);
            BluetoothSocket bluetoothSocket = blutoothServerSocket.accept();

            InputStream inputStream = bluetoothSocket.getInputStream();
            getData(inputStream);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void connect(BluetoothDevice device){

        String uuidStr = "000011001-0000-1000-8000-00805F9B34FB";

        UUID uuid = java.util.UUID.fromString(uuidStr);

        try{
            BluetoothSocket bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            bluetoothSocket.connect();

            OutputStream outputStream = bluetoothSocket.getOutputStream();

            sendData(outputStream, "hello world!");

        }catch(Exception e){
            showSnack("연결 중 오류가 발생했습니다.");
            e.printStackTrace();
            finish();
        }
    }

    private BluetoothDevice getDevice(String name, Set<BluetoothDevice> devices){
        for(BluetoothDevice device : devices){
            if(device.getName().equals(name)){
                return device;
            }
        }

        return null;
    }
}
