package com.chuangjiangx.unipay.pay.result;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.databinding.ActivityPayResultBinding;
import com.chuangjiangx.unipay.rxbus.RxBus;
import com.chuangjiangx.unipay.utils.ConvertUtils;
import com.chuangjiangx.unipay.view.activity.BaseActivity;

import io.reactivex.functions.Consumer;

public class PayResultActivity extends BaseActivity {

    private static final String EXTRA_AMOUNT = "EXTRA_AMOUNT";

    private static final String EXTRA_SUCCESS = "EXTRA_SUCCESS";

    private static final String EXTRA_TYPE = "EXTRA_TYPE";

    private PayResultViewModel mViewModel = new PayResultViewModel(mNetBuilder);

    private ActivityPayResultBinding mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(
                PayResultActivity.this, R.layout.activity_pay_result);
        final boolean success = getIntent().getBooleanExtra(EXTRA_SUCCESS, false);
        double amount = getIntent().getDoubleExtra(EXTRA_AMOUNT, 0d);
        String type = getIntent().getStringExtra(EXTRA_TYPE);
        mBind.ivResult.setImageResource(mViewModel.getBitmap());
        mBind.tvSecond.setText(Html.fromHtml(mViewModel.getSecondText(ConvertUtils.amount2String(amount))));
        setTimer();
        mViewModel.speak(PayResultActivity.this, type, ConvertUtils.amount2SpeakString(amount));
    }

    private void setTimer() {
        mCompositeDisposable.add(
                mViewModel.getTimer()
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                long lastTime = 3 - aLong > 0 ? 3 - aLong : 0;
                                mBind.tvFirst.setText(mViewModel.getFirstText(lastTime));
                                if (lastTime == 0) {
                                    RxBus.send(RxBus.EVENT_SUCCESS_CLOSE, null);
                                    finish();
                                }
                            }
                        })
        );
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            mCompositeDisposable.dispose();
            RxBus.send(RxBus.EVENT_SUCCESS_CLOSE, null);
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public static void startActivithy(Activity activity, double amount, String type) {
        Intent intent = new Intent(activity, PayResultActivity.class);
        intent.putExtra(EXTRA_AMOUNT, amount);
        intent.putExtra(EXTRA_TYPE, type);
        activity.startActivity(intent);
    }

}
