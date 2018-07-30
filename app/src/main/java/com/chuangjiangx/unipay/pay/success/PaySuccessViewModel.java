package com.chuangjiangx.unipay.pay.success;

import com.chuangjiangx.unipay.network.NetBuilder;
import com.chuangjiangx.unipay.view.activity.BaseViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

class PaySuccessViewModel extends BaseViewModel {

    PaySuccessViewModel(NetBuilder netBuilder) {
        super(netBuilder);
    }


    Flowable<Long> getTimer() {
        return Flowable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
