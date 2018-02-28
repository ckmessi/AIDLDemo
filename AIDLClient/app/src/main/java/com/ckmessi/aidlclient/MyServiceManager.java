package com.ckmessi.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.ckmessi.aidlserver.IMyAidlInterface;

public class MyServiceManager {
    private final String TAG = "MyServiceManager";
    private Context context;
    private final String PACKAGE_NAME = "com.ckmessi.aidlserver";
    private final String ACTION_NAME = "com.ckmessi.aidlserver.myservice";
    private IMyAidlInterface myService;
    private ServiceConnection myConnection;
    public boolean mConnecting;

    public MyServiceManager(Context context){
        this.context = context;
        this.bindMyService();
    }

    private void bindMyService(){
        myConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "connected to service");
                myService = IMyAidlInterface.Stub.asInterface(service);
                mConnecting = true;
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "disconnected");
                myService = null;
            }
        };
        Intent intent = new Intent();
        intent.setAction(ACTION_NAME);
        intent.setPackage(PACKAGE_NAME);
        context.bindService(intent, myConnection, context.BIND_AUTO_CREATE);
    }

    public int callServiceAdd(int a, int b){
        try {
            return this.myService.add(a, b);
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
