package com.chuangjiangx.unipay.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chuangjiangx.unipay.main.MainActivity;
import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.config.Config;
import com.chuangjiangx.unipay.login.LoginActivity;

public class LauncherActivity extends AppCompatActivity {

    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (!AccessibilityUtils.isAccessibilitySettingsOn(LauncherActivity.this) && mMaterialDialog == null) {
//            showAccessibilityDialog();
//        } else {
        if (Config.initConfig()) {
            startActivity(new Intent(LauncherActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
        }
        finish();
//        }
    }

    private void showAccessibilityDialog() {
        mMaterialDialog = new MaterialDialog.Builder(LauncherActivity.this)
                .cancelable(false)
                .content("请设置")
                .positiveText("马上设置")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        setupAccessibility();
                    }
                })
                .show();
    }


    private void setupAccessibility() {
        startActivity(new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }
}
