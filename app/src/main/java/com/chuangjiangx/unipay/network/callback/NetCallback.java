package com.chuangjiangx.unipay.network.callback;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * @author: yangshuiqiang
 * Time:2017/11/27 15:59
 */

public interface NetCallback extends ErrorNetCallback {

    void onStart();

    void onRequestSuccess();

    void onComplete();
}
