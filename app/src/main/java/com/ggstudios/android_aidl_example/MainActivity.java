package com.ggstudios.android_aidl_example;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "android-aidl-example";
    GetDeviceInfoInterface service;
    Button buttonSerial, buttonVersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSerial = (Button)findViewById(R.id.button_serial);
        buttonVersionCode = (Button)findViewById(R.id.button_version_code);

        buttonSerial.setClickable(false);
        buttonVersionCode.setClickable(false);

        buttonSerial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(MainActivity.this, "Serial: " + service.getSerialNumber(), Toast.LENGTH_LONG).show();
                } catch (RemoteException e) {
                    Log.i(TAG, "service.getSerialNumber() failed with: " + e);
                    e.printStackTrace();
                }
            }
        });

        buttonVersionCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(MainActivity.this, "VersionCode: " + service.getVersionCodes(), Toast.LENGTH_LONG).show();
                } catch (RemoteException e) {
                    Log.i(TAG, "service.getVersionCodes() failed with: " + e);
                    e.printStackTrace();
                }
            }
        });

        initService();
    }

    /**
     * Class for interacting with the main interface of the service.
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = GetDeviceInfoInterface.Stub.asInterface(boundService);
            Toast.makeText(MainActivity.this, "AIDL service connected", Toast.LENGTH_LONG).show();
        }

        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Toast.makeText(MainActivity.this, "AIDL service disconnected", Toast.LENGTH_LONG).show();
        }
    };

    /** Function to establish connections with the service, binding by interface names. */
    private void initService() {
        Intent i = new Intent();
        i.setClassName(this.getPackageName(), GetDeviceInfoService.class.getName());
        boolean bindResult = bindService(i, mConnection, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "initService() bindResult: " + bindResult);

        if(bindResult) {
            buttonSerial.setClickable(true);
            buttonVersionCode.setClickable(true);
        }
    }

    /** Function to release connections. */
    private void releaseService() {
        unbindService(mConnection);
        mConnection = null;
        Log.d(TAG, "releaseService() trigger");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }
}
