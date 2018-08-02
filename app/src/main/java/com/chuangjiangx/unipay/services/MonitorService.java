package com.chuangjiangx.unipay.services;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

import com.chuangjiangx.unipay.wakeup.WakeupActivity;

public class MonitorService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {

    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        Log.i("test", "event:" + event);
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_NUMPAD_DIVIDE) {
            startActivity(new Intent(getApplicationContext(), WakeupActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            return true;
        } else {
            return super.onKeyEvent(event);
        }
    }
}
