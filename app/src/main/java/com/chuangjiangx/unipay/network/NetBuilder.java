package com.chuangjiangx.unipay.network;

import android.content.Context;

import com.chuangjiangx.unipay.network.callback.NetCallback;
import com.chuangjiangx.unipay.network.error.ErrorConsumer;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author: yangshuiqiang
 * Time:2017/11/27 16:24
 */

public class NetBuilder<T> {
    private CompositeDisposable mCompositeDisposable;
    private Context mContext;

    public NetBuilder(Context context, CompositeDisposable compositeDisposable) {
        this.mContext = context;
        this.mCompositeDisposable = compositeDisposable;
    }


    public <T> Disposable request(Flowable<T> flowable, final Consumer<T> consumer, final NetCallback... netCallbacks) {
        for (NetCallback netCallback : netCallbacks) {
            netCallback.onStart();
        }
        Disposable disposable = flowable
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        for (NetCallback netCallback : netCallbacks) {
                            netCallback.onComplete();
                        }
                    }
                })
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        consumer.accept(t);
                        for (NetCallback netCallback : netCallbacks) {
                            netCallback.onRequestSuccess();
                        }
                    }
                }, new ErrorConsumer(mContext, netCallbacks));

        if (mCompositeDisposable != null) {
            mCompositeDisposable.add(disposable);
        }
        return disposable;
    }
}