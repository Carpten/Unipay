package com.chuangjiangx.unipay.view.edittext;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chuangjiangx.unipay.network.callback.NetCallback;
import com.chuangjiangx.unipay.network.error.HttpException;
import com.chuangjiangx.unipay.utils.SoftInputUtils;

/**
 * @author: yangshuiqiang
 * Time:2018/1/17
 */
public class EditCallback implements NetCallback {

    private View mView;

    public EditCallback(View view) {
        mView = view;
    }

    @Override
    public void onRequestFail(HttpException e) {

    }

    @Override
    public void onStart() {
        SoftInputUtils.hideInput(mView);
        setEditable(mView, false);
    }

    @Override
    public void onRequestSuccess() {

    }

    @Override
    public void onComplete() {
        setEditable(mView, true);
    }


    private void setEditable(View view, boolean editable) {
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int childCount = vg.getChildCount();
            for (int i = 0; i < childCount; i++) {
                setEditable(vg.getChildAt(i), editable);
            }
        } else if (view instanceof EditText) {
            view.setFocusable(editable);
            view.setFocusableInTouchMode(editable);
        }
    }
}
