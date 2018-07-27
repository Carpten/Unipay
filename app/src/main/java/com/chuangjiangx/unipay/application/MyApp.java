package com.chuangjiangx.unipay.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.chuangjiangx.unipay.services.MonitorService;

public class MyApp extends Application {

    private static MyApp sMyApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sMyApp = this;
        startService(new Intent(getApplicationContext(), MonitorService.class));
    }

    public static Context getContext() {
        return sMyApp.getApplicationContext();
    }
}
