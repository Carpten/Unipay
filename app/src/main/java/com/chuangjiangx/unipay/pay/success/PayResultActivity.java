package com.chuangjiangx.unipay.pay.success;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Html;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.databinding.ActivityPayResultBinding;
import com.chuangjiangx.unipay.view.activity.BaseActivity;

import io.reactivex.functions.Consumer;

public class PayResultActivity extends BaseActivity {

    private static final String EXTRA_AMOUNT = "EXTRA_AMOUNT";

    private static final String EXTRA_SUCCESS = "EXTRA_SUCCESS";

    private static final String EXTRA_REASON = "EXTRA_REASON";

    private PayResultViewModel mViewModel = new PayResultViewModel(mNetBuilder);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityPayResultBinding bind = DataBindingUtil.setContentView(
                PayResultActivity.this, R.layout.activity_pay_result);
        final boolean success = getIntent().getBooleanExtra(EXTRA_SUCCESS, false);
        double amount = getIntent().getDoubleExtra(EXTRA_AMOUNT, 0d);
        String reason = getIntent().getStringExtra(EXTRA_REASON);

        bind.ivResult.setImageResource(mViewModel.getBitmap(success));
        if (success) {
            bind.tvSecond.setText(Html.fromHtml(mViewModel.getSecondText(success, amount, reason)));
        } else {
            bind.tvSecond.setText(mViewModel.getSecondText(success, amount, reason));
        }
        mCompositeDisposable.add(
                mViewModel.getTimer()
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                long lastTime = 3 - aLong > 0 ? 3 - aLong : 0;
                                bind.tvFirst.setText(mViewModel.getFirstText(success, lastTime));
                                if (lastTime == 0) {
                                    finish();
                                }
                            }
                        })
        );
    }

    public static void startActivithy(Activity activity, double amount, boolean isSuccess, String reason) {
        Intent intent = new Intent(activity, PayResultActivity.class);
        intent.putExtra(EXTRA_SUCCESS, isSuccess);
        intent.putExtra(EXTRA_AMOUNT, amount);
        intent.putExtra(EXTRA_REASON, reason);
        activity.startActivity(intent);
    }

}
