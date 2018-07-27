package com.chuangjiangx.unipay.services;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import com.chuangjiangx.unipay.launcher.LauncherActivity;

public class MonitorService extends AccessibilityService {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("test", "onCreate");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(getApplicationContext(), LauncherActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        super.onKeyEvent(event);
        return true;
    }
}
