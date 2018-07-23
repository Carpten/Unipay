package com.chuangjiangx.unipay.login;

import com.chuangjiangx.unipay.network.NetBuilder;
import com.chuangjiangx.unipay.network.RetrofitClient;
import com.chuangjiangx.unipay.network.callback.NetCallback;
import com.chuangjiangx.unipay.view.activity.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

class LoginViewModel extends BaseViewModel {

    LoginViewModel(NetBuilder netBuilder) {
        super(netBuilder);
    }


    void login(Consumer<String> consumer, String username, String password
            , NetCallback... netCallbacks) {
        mNetBuilder.request(RetrofitClient.get().login(username, password)
                .observeOn(AndroidSchedulers.mainThread()), consumer, netCallbacks);
    }
}
