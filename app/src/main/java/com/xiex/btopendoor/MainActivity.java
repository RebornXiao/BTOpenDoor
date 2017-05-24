package com.xiex.btopendoor;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {
    private final String TAG = this.getClass().getSimpleName().toString();
    private BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 注册开始发现广播。
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        intentFilter.addAction(blueAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(blueAdapter.ACTION_DISCOVERY_FINISHED);

        this.registerReceiver(mReceiver, intentFilter);
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blueAdapter.isEnabled()) {
                    blueAdapter.enable();
                }
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFound = false;
                if(blueAdapter.isEnabled()){
                    blueAdapter.enable();
                }
                blueAdapter.startDiscovery();


            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (blueAdapter.enable()) {
//                    blueAdapter.disable();
//                }
            }
        });

    }

    private boolean isFound = true;
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                Log.v(TAG, "connected: " + device);
            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                Log.v(TAG, "disconnected: " + device);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                Log.e(TAG, "  设备：" + device.getAddress() + ";" + device.getName() + ";" + device.getUuids() + ";" +
                        device.getBondState());
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    //信号强度。
                    short rssi = intent.getExtras().getShort(
                            BluetoothDevice.EXTRA_RSSI);
                    Log.e(TAG, "信号：" + rssi);
                }
                String meilan = "1C:CD:E5:80:B5:AF".toLowerCase().replaceAll(":", "");
                String address = device.getAddress().toLowerCase().replaceAll(":", "");
                if (!TextUtils.isEmpty(address) && address.equals(meilan)) {
                    MediaPlayerHelp.play(MainActivity.this, R.raw.mp26);
                    isFound = true;
                }
            } else if (blueAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.v(TAG, "Discovery started");
            } else if (blueAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.v(TAG, "Discovery finished");
                if (!isFound) {
                    blueAdapter.startDiscovery();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
