package com.ggstudios.android_aidl_example;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class GetDeviceInfoService extends Service {

    private static final String TAG = "GetDeviceInfoService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate()");
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new GetDeviceInfoInterface.Stub() {

            @Override
            public String getSerialNumber() throws RemoteException {
                return Build.SERIAL;
            }

            @Override
            public int getVersionCodes() throws RemoteException {
                return BuildConfig.VERSION_CODE;
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }
}
