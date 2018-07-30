package com.chuangjiangx.unipay.main;

import android.app.Activity;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.chuangjiangx.unipay.network.NetBuilder;
import com.chuangjiangx.unipay.pay.c2b.C2BActivity;
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
                mMoney.append((keyCode - KeyEvent.KEYCODE_NUMPAD_0));
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


    private void confirmMoney(Activity activity) {
        if (!TextUtils.isEmpty(mMoney.toString()) && !".".equals(mMoney.toString())) {
            Double amount = Double.valueOf(mMoney.toString());
            if (0 != amount) {
                C2BActivity.startC2BActivithy(activity, amount, 1);
            }
        }
    }

    private void finish(Activity activity) {
        activity.finish();
    }
}
