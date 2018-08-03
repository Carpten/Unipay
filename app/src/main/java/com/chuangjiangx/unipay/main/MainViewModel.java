package com.chuangjiangx.unipay.main;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.network.NetBuilder;
import com.chuangjiangx.unipay.pay.c2b.C2BActivity;
import com.chuangjiangx.unipay.services.tts.SpeakService;
import com.chuangjiangx.unipay.view.activity.BaseViewModel;

class MainViewModel extends BaseViewModel {

    private StringBuilder mMoney = new StringBuilder();

    MainViewModel(NetBuilder netBuilder) {
        super(netBuilder);
    }


    boolean handleKeyDown(Activity activity, int keyCode) {
        if (keyCode >= KeyEvent.KEYCODE_NUMPAD_0
                && keyCode <= KeyEvent.KEYCODE_NUMPAD_9) {
            int index = mMoney.lastIndexOf(".");
            if (index == -1 || index >= mMoney.length() - 2) {
                if (!(keyCode == KeyEvent.KEYCODE_NUMPAD_0
                        && index == mMoney.length() - 2
                        && Double.valueOf(mMoney.toString()) == 0)) {
                    String overNum = mMoney.toString() + (keyCode - KeyEvent.KEYCODE_NUMPAD_0);
                    if (Double.valueOf(overNum) <= 99999.99) {
                        mMoney.append((keyCode - KeyEvent.KEYCODE_NUMPAD_0));
                    }
                }
            }
        } else if (keyCode == KeyEvent.KEYCODE_NUMPAD_DOT) {
            if (mMoney.lastIndexOf(".") == -1) {
                mMoney.append(".");
            }
        } else if (keyCode == KeyEvent.KEYCODE_ENTER) {
            confirmMoney(activity);
        } else if (keyCode == KeyEvent.KEYCODE_DEL) {
            finish(activity);
        } else if (keyCode == KeyEvent.KEYCODE_SHIFT_LEFT) {
            if (mMoney.length() > 0) {
                mMoney.deleteCharAt(mMoney.length() - 1);
            }
        } else {
            return false;
        }
        return true;
    }

    void clearAmount() {
        mMoney = new StringBuilder();
    }

    void speakInputAmount(Context context) {
        speak(context, context.getString(R.string.speak_input_amount));
    }


    private void confirmMoney(Activity activity) {
        if (!TextUtils.isEmpty(mMoney.toString()) && !".".equals(mMoney.toString())) {
            Double amount = Double.valueOf(mMoney.toString());
            if (0 != amount) {
                C2BActivity.startC2BActivithy(activity, amount, 1);
            }
        }
    }

    private void speak(Context context, String text) {
        SpeakService.stopAndSpeak(context, text);
    }

    private void finish(Activity activity) {
        MainActivity.sStarting = false;
        activity.finish();
    }
}
