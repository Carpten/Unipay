package com.chuangjiangx.unipay.login;

import android.text.TextUtils;

import com.chuangjiangx.unipay.config.Config;
import com.chuangjiangx.unipay.model.login.LoginBean;
import com.chuangjiangx.unipay.model.login.MyinfoBean;
import com.chuangjiangx.unipay.network.NetBuilder;
import com.chuangjiangx.unipay.network.RetrofitClient;
import com.chuangjiangx.unipay.network.callback.NetCallback;
import com.chuangjiangx.unipay.preference.Preferences;
import com.chuangjiangx.unipay.view.activity.BaseViewModel;

import org.reactivestreams.Publisher;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

class LoginViewModel extends BaseViewModel {

    LoginViewModel(NetBuilder netBuilder) {
        super(netBuilder);
    }


    String getUserName() {
        return getIsChecked() ? Config.sUsername : null;
    }

    /**
     * 获取保存的密码
     *
     * @return 保存的密码
     */
    String getPassword() {
        return getIsChecked() ? Config.sPassword : null;
    }

    /**
     * 获取记住密码状态
     *
     * @return 是否记住密码
     */
    boolean getIsChecked() {
        return Preferences.get().getBoolean(Preferences.KEY_REMEMBER_PASSWORD, false);
    }


    void login(Consumer<LoginBean> consumer, String username, String password
            , NetCallback... netCallbacks) {
        mNetBuilder.request(RetrofitClient.get().login(username, password)
                .flatMap(new Function<String, Publisher<LoginBean>>() {
                    @Override
                    public Publisher<LoginBean> apply(final String token) throws Exception {
                        Config.sToken = token;
                        return RetrofitClient.get().getUserInfo().map(new Function<MyinfoBean, LoginBean>() {
                            @Override
                            public LoginBean apply(MyinfoBean myinfoBean) throws Exception {
                                return new LoginBean(token, myinfoBean);
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()), consumer, netCallbacks);
    }


    //保存账号密码
    void saveAccount(String username, String password, boolean ischecked, LoginBean loginBean) {
        Config.sUsername = username;
        Config.sPassword = password;
        Config.sToken = loginBean.getToken();
        String storeName = loginBean.getMyinfoBean().getMainSearchMyDetailsDto().getStoreName();
        String merchantName = loginBean.getMyinfoBean().getMainSearchMyDetailsDto().getMerchantName();
        Config.sStoreName = !TextUtils.isEmpty(storeName) ? storeName : merchantName;
        Config.sNickName = loginBean.getMyinfoBean().getMainSearchMyDetailsDto().getRealname();
        Preferences.edit()
                .putString(Preferences.KEY_USERNAME, username)
                .putString(Preferences.KEY_PASSWORD, password)
                .putBoolean(Preferences.KEY_REMEMBER_PASSWORD, ischecked)
                .putString(Preferences.KEY_TOKEN, loginBean.getToken())
                .putString(Preferences.KEY_STORENAME, Config.sStoreName)
                .putString(Preferences.KEY_NICKNAME, Config.sNickName)

                .apply();
    }
}
