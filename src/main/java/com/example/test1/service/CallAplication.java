package com.example.test1.service;

import android.app.Application;

import com.example.test1.call.CallMgr;

public class CallAplication extends Application{

    private CallMgr mCallMgr;

    @Override
    public void onCreate() {
        super.onCreate();
        mCallMgr = new CallMgr(this);
    }
}
