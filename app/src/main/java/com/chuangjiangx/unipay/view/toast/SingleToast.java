package com.chuangjiangx.unipay.view.toast;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.chuangjiangx.unipay.application.MyApp;

/**
 * @author: yangshuiqiang
 * Time:2018/1/17
 */
public class SingleToast {
    private volatile static SingleToast mSingleToast;
    private Toast mToast;

    @SuppressLint("ShowToast")
    private SingleToast() {
        mToast = Toast.makeText(MyApp.getContext(), "", Toast.LENGTH_LONG);
    }

    public static SingleToast get() {
        if (mSingleToast == null) {
            synchronized (SingleToast.class) {
                if (mSingleToast == null) {
                    mSingleToast = new SingleToast();
                }
            }
        }
        return mSingleToast;
    }

    public void shortShow(String text) {
        if (text == null) return;
        mToast.setText(text);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void longShow(String text) {
        if (text == null) return;
        mToast.setText(text);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }
}
