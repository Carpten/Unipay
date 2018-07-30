package com.chuangjiangx.unipay.pay.success;

import android.text.Html;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.network.NetBuilder;
import com.chuangjiangx.unipay.view.activity.BaseViewModel;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

class PayResultViewModel extends BaseViewModel {

    PayResultViewModel(NetBuilder netBuilder) {
        super(netBuilder);
    }


    Flowable<Long> getTimer() {
        return Flowable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

    int getBitmap(boolean success) {
        return success ? R.drawable.bmp_pay_success : R.drawable.bmp_pay_fail;
    }

    String getFirstText(boolean success, long lastTime) {
        if (success) {
            return String.format(Locale.getDefault(), "  付款成功（%ds）", lastTime);
        } else {
            return String.format(Locale.getDefault(), "  付款失败（%ds）", lastTime);
        }
    }

    String getSecondText(boolean success, double amount, String reason) {
        if (success) {
            return String.format("付款金额：<font color='#FF0000'>%s</font> 元", amount);
        } else {
            return String.format("失败原因/异常%s", reason);
        }
    }

    String getReason(String errCode) {
        return String.format("失败原因/异常%s", errCode);
    }
}
