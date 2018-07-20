package com.chuangjiangx.unipay.network.callback;


import com.chuangjiangx.unipay.network.error.HttpException;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * @author: yangshuiqiang
 * Time:2018/1/17
 */
public class SimpleNetCallback implements NetCallback {
    @Override
    public void onRequestFail(HttpException e) {

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

    @Override
    public <T> void onRegister(CompositeDisposable compositeDisposable, Flowable<T> flowable, Consumer<T> consumer, NetCallback... netCallbacks) {

    }
}
