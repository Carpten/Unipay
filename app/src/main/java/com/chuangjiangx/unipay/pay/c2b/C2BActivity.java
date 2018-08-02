package com.chuangjiangx.unipay.pay.c2b;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.databinding.ActivityC2bBinding;
import com.chuangjiangx.unipay.utils.ConvertUtils;
import com.chuangjiangx.unipay.view.activity.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class C2BActivity extends BaseActivity {

    private static final String EXTRA_AMOUNT = "EXTRA_AMOUNT";

    private ActivityC2bBinding mBind;

    private C2BViewModel mViewModel = new C2BViewModel(C2BActivity.this, mNetBuilder);

    public static void startC2BActivithy(Activity activity, double amount, int requestCode) {
        Intent intent = new Intent(activity, C2BActivity.class);
        intent.putExtra(EXTRA_AMOUNT, amount);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(C2BActivity.this, R.layout.activity_c2b);
        double amount = getIntent().getDoubleExtra(EXTRA_AMOUNT, 0d);
        mBind.tvAmount.setText(ConvertUtils.amount2String(amount));
        mViewModel.initSocket(amount);
        mViewModel.speak(ConvertUtils.amount2SpeakString(amount));
    }


    public void initQrView(Bitmap bitmap) {
        mBind.ivQrcode.setImageBitmap(bitmap);
        mBind.ivQrcode.animate().alpha(1).scaleX(1f).scaleY(1f).setDuration(200).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.closeWebSocket();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
