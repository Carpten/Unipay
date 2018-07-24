package com.chuangjiangx.unipay.main;

import android.content.Context;

import com.chuangjiangx.unipay.config.Config;
import com.chuangjiangx.unipay.login.LoginActivity;
import com.chuangjiangx.unipay.model.network.ResponseBean;
import com.chuangjiangx.unipay.network.NetBuilder;
import com.chuangjiangx.unipay.network.RetrofitClient;
import com.chuangjiangx.unipay.network.error.ErrorConsumer;
import com.chuangjiangx.unipay.view.activity.BaseViewModel;

import io.reactivex.functions.Consumer;

class MainViewModel extends BaseViewModel {

    MainViewModel(NetBuilder netBuilder) {
        super(netBuilder);
    }

    void logout(Context context) {
        mNetBuilder.getCompositeDisposable().add(
                RetrofitClient.get().logout(Config.sToken).subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                }));
        Config.clear();
        //跳转到LoginActivity并关闭所有Activity
        LoginActivity.startActivityAndClearTask(context);
    }
}
