package com.chuangjiangx.unipay.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.network.RetrofitClient;
import com.chuangjiangx.unipay.network.error.ErrorConsumer;
import com.chuangjiangx.unipay.utils.MeasureUtils;
import com.chuangjiangx.unipay.view.activity.BaseActivity;
import com.chuangjiangx.unipay.view.toast.ToastCallback;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity {

    private LoginViewModel mLoginViewModel = new LoginViewModel(mNetBuilder);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



//        mLoginViewModel.login(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//
//            }
//        }, "aaaa", "1", new ToastCallback());
    }


    /**
     * 跳转到登录页并且关闭所有activity
     */
    public static void startActivityAndClearTask(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
