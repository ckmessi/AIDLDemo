package com.ckmessi.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {

    private static final String TAG = "AIDLService";

    private Binder mBinder = new IMyAidlInterface.Stub() {
        @Override
        public int add(int a, int b) throws RemoteException {
            Log.d(TAG, "add: a=" + a + ", b=" + b);
            return a + b;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
