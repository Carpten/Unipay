package com.chuangjiangx.unipay.wakeup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.chuangjiangx.unipay.launcher.LauncherActivity;
import com.chuangjiangx.unipay.main.MainActivity;

public class WakeupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("test", "sStarting:" + MainActivity.sStarting);
        if (!MainActivity.sStarting) {
            Intent intent = new Intent(WakeupActivity.this, LauncherActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        finish();
        overridePendingTransition(0, 0);
    }
}
