package com.chuangjiangx.unipay.main;

import android.app.ActivityManager;
import android.content.ComponentName;
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
import com.chuangjiangx.unipay.rxbus.RxBus;
import com.chuangjiangx.unipay.utils.ConvertUtils;
import com.chuangjiangx.unipay.view.activity.BaseActivity;
import com.chuangjiangx.unipay.view.dialog.DialogBuild;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    public static boolean sStarting;

    private MainViewModel mMainViewModel = new MainViewModel(mNetBuilder);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sStarting = true;
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mMainViewModel.speakInputAmount(MainActivity.this);

        mCompositeDisposable.add(RxBus.getSuccessClose().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                MainActivity.sStarting = false;
                finish();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
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
        sStarting = false;
        super.onDestroy();
    }

    private Disposable timerSubscribe;

    @Override
    protected void onPause() {
        if (timerSubscribe != null && !timerSubscribe.isDisposed()) {
            timerSubscribe.dispose();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        timerSubscribe = Flowable.timer(120, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        MainActivity.sStarting = false;
                        finish();
                    }
                });
        super.onResume();
    }
}
