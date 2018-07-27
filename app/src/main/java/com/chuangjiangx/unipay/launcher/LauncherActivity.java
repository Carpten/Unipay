package com.chuangjiangx.unipay.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.config.Config;
import com.chuangjiangx.unipay.login.LoginActivity;
import com.chuangjiangx.unipay.main.MainActivity;
import com.chuangjiangx.unipay.utils.AccessibilityUtils;
import com.chuangjiangx.unipay.view.dialog.DialogBuild;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!AccessibilityUtils.isAccessibilitySettingsOn(LauncherActivity.this)) {
            showAccessibilityDialog();
        } else {
            if (Config.initConfig()) {
                startActivity(new Intent(LauncherActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
            }
            finish();
        }
    }

    private void showAccessibilityDialog() {
        new DialogBuild(LauncherActivity.this)
                .setCloseOn(false)
                .setTitle(getString(R.string.accessibility_dialog_title))
                .setContentText(getString(R.string.accessibility_dialog_content))
                .setPositiveText(getString(R.string.accessibility_dialog_positive))
                .onPositive(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setupAccessibility();
                    }
                }).show();
    }


    private void setupAccessibility() {
        startActivity(new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }
}
