package com.chuangjiangx.unipay.view.toast;


import com.chuangjiangx.unipay.network.callback.NetCallback;
import com.chuangjiangx.unipay.network.error.HttpException;

/**
 * @author: yangshuiqiang
 * Time:2018/1/12
 */
public class ToastCallback implements NetCallback {

    @Override
    public void onRequestFail(HttpException e) {
        SingleToast.get().shortShow(e.getErrMsg());
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onRequestSuccess() {

    }

    @Override
    public void onComplete() {
    }
}
