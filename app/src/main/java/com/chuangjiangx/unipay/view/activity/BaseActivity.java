package com.chuangjiangx.unipay.view.activity;

import android.support.v7.app.AppCompatActivity;

import com.chuangjiangx.unipay.network.NetBuilder;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity extends AppCompatActivity {

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected NetBuilder mNetBuilder = new NetBuilder(this, mCompositeDisposable);

    @Override
    protected void onDestroy() {
        mCompositeDisposable.dispose();
        super.onDestroy();
    }
}
