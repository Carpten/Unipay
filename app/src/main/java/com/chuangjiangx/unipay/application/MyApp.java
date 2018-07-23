package com.chuangjiangx.unipay.application;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    private static MyApp sMyApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sMyApp = this;
    }

    public static Context getContext() {
        return sMyApp.getApplicationContext();
    }
}
