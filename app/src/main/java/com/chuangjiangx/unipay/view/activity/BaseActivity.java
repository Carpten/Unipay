package com.chuangjiangx.unipay.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.chuangjiangx.unipay.config.Config;
import com.chuangjiangx.unipay.launcher.LauncherActivity;
import com.chuangjiangx.unipay.network.NetBuilder;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity extends AppCompatActivity {

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    protected NetBuilder mNetBuilder = new NetBuilder(this, mCompositeDisposable);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Config.sRightStart) {
            startActivity(new Intent(this, LauncherActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            return;
        }
    }

    @Override
    protected void onDestroy() {
        mCompositeDisposable.dispose();
        super.onDestroy();
    }
}
