package com.chuangjiangx.unipay.pay.success;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.constant.Constant;
import com.chuangjiangx.unipay.databinding.ActivityPaySuccessBinding;
import com.chuangjiangx.unipay.pay.c2b.C2BActivity;
import com.chuangjiangx.unipay.view.activity.BaseActivity;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class PaySuccessActivity extends BaseActivity {

    private PaySuccessViewModel mViewModel = new PaySuccessViewModel(mNetBuilder);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityPaySuccessBinding bind = DataBindingUtil.setContentView(
                PaySuccessActivity.this, R.layout.activity_pay_success);
        double amount = getIntent().getDoubleExtra(Constant.EXTRA_AMOUNT, 0d);
        String html = String.format("付款金额：<font color='#FF0000'>%s</font> 元", amount);
        bind.tvAmount.setText(Html.fromHtml(html));
        mCompositeDisposable.add(
                mViewModel.getTimer()
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                long lastTime = 3 - aLong > 0 ? 3 - aLong : 0;
                                String time = String.format(Locale.getDefault(), "  付款成功（%ds）", lastTime);
                                bind.tvTime.setText(time);
                                if (lastTime == 0) {
                                    finish();
                                }
                            }
                        })
        );
    }

    public static void startActivithy(Activity activity, double amount) {
        Intent intent = new Intent(activity, PaySuccessActivity.class);
        intent.putExtra(Constant.EXTRA_AMOUNT, amount);
        activity.startActivity(intent);
    }

}
