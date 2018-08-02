package com.chuangjiangx.unipay.pay.result;

import android.content.Context;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.network.NetBuilder;
import com.chuangjiangx.unipay.services.tts.SpeakService;
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

    int getBitmap() {
        return R.drawable.bmp_pay_success;
    }

    String getFirstText(long lastTime) {
        return String.format(Locale.getDefault(), "  付款成功（%ds）", lastTime);
    }

    String getSecondText(String amount) {
        return String.format("付款金额：<font color='#FF0000'>%s</font> 元", amount);
    }

    String getReason(String errCode) {
        return String.format("失败原因/异常%s", errCode);
    }


    void speak(Context context, String type, String amount) {
        SpeakService.stopAndSpeak(context, String.format(
                context.getString(R.string.speak_success), type, amount));
    }
}
