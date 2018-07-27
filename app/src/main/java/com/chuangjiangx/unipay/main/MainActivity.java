package com.chuangjiangx.unipay.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.config.Config;
import com.chuangjiangx.unipay.databinding.ActivityMainBinding;
import com.chuangjiangx.unipay.view.activity.BaseActivity;
import com.chuangjiangx.unipay.view.dialog.DialogBuild;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private MainViewModel mMainViewModel = new MainViewModel(mNetBuilder);

    private ActivityMainBinding mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mBind.ivLogout.setOnClickListener(this);
        mBind.tvStorename.setText(Config.sStoreName);
        Log.i("test", "onCreate");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("test", "onNewIntent");
    }

    @Override
    public void onClick(View view) {
        new DialogBuild(MainActivity.this)
                .setTitle(Config.sStoreName)
                .setContentText(String.format(getString(R.string.main_logout), Config.sUsername, Config.sNickName))
                .setPositiveText(getString(R.string.login_logout))
                .onPositive(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mMainViewModel.logout(MainActivity.this);
                    }
                }).show();
    }
}
