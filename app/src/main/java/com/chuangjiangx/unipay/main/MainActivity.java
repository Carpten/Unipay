package com.chuangjiangx.unipay.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.config.Config;
import com.chuangjiangx.unipay.databinding.ActivityMainBinding;
import com.chuangjiangx.unipay.view.activity.BaseActivity;
import com.chuangjiangx.unipay.view.dialog.DialogBuild;

public class MainActivity extends BaseActivity {

    public static boolean sStarting;

    private MainViewModel mMainViewModel = new MainViewModel(mNetBuilder);

    private ActivityMainBinding mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sStarting = true;
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("test", "keyCode:" + keyCode);
        return mMainViewModel.handleKeyDown(MainActivity.this, keyCode)
                || super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMainViewModel.clearAmount();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sStarting = false;
    }
}
