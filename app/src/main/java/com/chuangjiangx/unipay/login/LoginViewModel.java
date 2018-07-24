package com.chuangjiangx.unipay.login;

import com.chuangjiangx.unipay.network.NetBuilder;
import com.chuangjiangx.unipay.network.RetrofitClient;
import com.chuangjiangx.unipay.network.callback.NetCallback;
import com.chuangjiangx.unipay.preference.Preferences;
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


    //保存账号密码
    void saveAccount(String username, String password, String token, boolean ischecked) {
        Preferences.edit()
                .putString(Preferences.KEY_USERNAME, username)
                .putString(Preferences.KEY_PASSWORD, password)
                .putBoolean(Preferences.KEY_REMEMBER_PASSWORD, ischecked)
                .putString(Preferences.KEY_TOKEN, token)
                .apply();
    }
}
