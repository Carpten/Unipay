package com.chuangjiangx.unipay.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.config.Config;
import com.chuangjiangx.unipay.databinding.ActivityMainBinding;
import com.chuangjiangx.unipay.login.LoginActivity;
import com.chuangjiangx.unipay.model.network.ResponseBean;
import com.chuangjiangx.unipay.network.RetrofitClient;
import com.chuangjiangx.unipay.network.error.ErrorConsumer;
import com.chuangjiangx.unipay.view.activity.BaseActivity;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private MainViewModel mMainViewModel = new MainViewModel(mNetBuilder);

    private ActivityMainBinding mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mBind.ivLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        mMainViewModel.logout(MainActivity.this);
    }
}
