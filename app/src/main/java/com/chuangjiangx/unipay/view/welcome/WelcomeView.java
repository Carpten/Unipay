package com.chuangjiangx.unipay.view.welcome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.config.Config;
import com.chuangjiangx.unipay.login.LoginActivity;
import com.chuangjiangx.unipay.model.network.ResponseBean;
import com.chuangjiangx.unipay.network.RetrofitClient;
import com.chuangjiangx.unipay.view.dialog.DialogBuild;

import io.reactivex.functions.Consumer;

public class WelcomeView extends FrameLayout implements View.OnClickListener {

    public WelcomeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View contentView = LayoutInflater.from(getContext()).inflate(
                R.layout.layout_welcome, this, false);
        addView(contentView, new LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ImageView ivLogout = contentView.findViewById(R.id.iv_logout);
        ivLogout.setOnClickListener(this);
        TextView tvStorename = contentView.findViewById(R.id.tv_storename);
        tvStorename.setText(Config.sStoreName);
    }

    @Override
    public void onClick(View view) {
        new DialogBuild(getContext())
                .setTitle(Config.sStoreName)
                .setContentText(String.format(getContext().getString(R.string.main_logout)
                        , Config.sUsername, Config.sNickName))
                .setPositiveText(getContext().getString(R.string.login_logout))
                .onPositive(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logout(getContext());
                    }
                }).show();
    }

    @SuppressLint("CheckResult")
    void logout(Context context) {
        RetrofitClient.get().logout(Config.sToken).subscribe(new Consumer<ResponseBean>() {
            @Override
            public void accept(ResponseBean responseBean) throws Exception {
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
            }
        });
        Config.clear();
        //跳转到LoginActivity并关闭所有Activity
        LoginActivity.startActivityAndClearTask(context);
    }
}
