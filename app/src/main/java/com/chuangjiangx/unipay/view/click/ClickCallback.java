package com.chuangjiangx.unipay.view.click;

import android.view.View;

import com.chuangjiangx.unipay.network.callback.NetCallback;
import com.chuangjiangx.unipay.network.error.HttpException;

/**
 * @author: yangshuiqiang
 * Time:2018/1/12
 */
public class ClickCallback implements NetCallback {

    private View mView;
    private View.OnClickListener mOnClickListener;

    public ClickCallback(View view, View.OnClickListener onClickListener) {
        mView = view;
        mOnClickListener = onClickListener;
    }

    @Override
    public void onRequestFail(HttpException e) {

    }

    @Override
    public void onStart() {
        mView.setOnClickListener(null);
    }

    @Override
    public void onRequestSuccess() {

    }

    @Override
    public void onComplete() {
        mView.setOnClickListener(mOnClickListener);
    }
}
