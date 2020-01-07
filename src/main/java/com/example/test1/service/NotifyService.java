package com.example.test1.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.test1.Constant;
import com.example.test1.call.CallMgr;

public class NotifyService extends Service{
    Notify notify;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notify = new Notify(this);
        registerBroadcast();
    }

    public void registerBroadcast(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.TEST_NOTIFY);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(Constant.TEST_NOTIFY)){
                    notify.setNotification("难受","快给我打电话");
                }
            }
        },filter);
    }

}
