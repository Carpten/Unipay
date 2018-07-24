package com.chuangjiangx.unipay.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;

import com.chuangjiangx.unipay.MainActivity;
import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.databinding.ActivityLoginBinding;
import com.chuangjiangx.unipay.utils.MeasureUtils;
import com.chuangjiangx.unipay.view.activity.BaseActivity;
import com.chuangjiangx.unipay.view.click.ClickCallback;
import com.chuangjiangx.unipay.view.edittext.EditCallback;
import com.chuangjiangx.unipay.view.edittext.SimpleTextWatcher;
import com.chuangjiangx.unipay.view.toast.ToastCallback;

import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity implements View.OnClickListener
        , ViewTreeObserver.OnGlobalLayoutListener {

    private LoginViewModel mLoginViewModel = new LoginViewModel(mNetBuilder);

    private ActivityLoginBinding mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBind = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        mBind.ivUsernameDelete.setOnClickListener(this);
        mBind.ivPasswordDelete.setOnClickListener(this);
        mBind.btnLogin.setOnClickListener(this);
        mBind.etUsername.addTextChangedListener(mTextWatcher);
        mBind.etPassword.addTextChangedListener(mTextWatcher);
        //初始化登录按钮enable值
        mTextWatcher.afterTextChanged(null);
        mBind.scrollview.getViewTreeObserver().addOnGlobalLayoutListener(this);
        //使scrollview获取焦点，防止进来时edittext获取焦点导致键盘出现
        mBind.scrollview.requestFocus();


    }

    TextWatcher mTextWatcher = new SimpleTextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {
            //当账号和密码都有值时按钮才能点击
            if (!TextUtils.isEmpty(mBind.etUsername.getText()) &&
                    !TextUtils.isEmpty(mBind.etPassword.getText())) {
                mBind.btnLogin.setEnabled(true);
            } else {
                mBind.btnLogin.setEnabled(false);
            }
            //根据账号是否有值显示清除账号图标
            mBind.ivUsernameDelete.setVisibility(TextUtils.isEmpty(
                    mBind.etUsername.getText()) ? View.GONE : View.VISIBLE);
            //根据密码是否有值显示清除密码图标
            mBind.ivPasswordDelete.setVisibility(TextUtils.isEmpty(
                    mBind.etPassword.getText()) ? View.GONE : View.VISIBLE);
        }
    };


    //edittext获取焦点时，scrollview向上滑到底
    @Override
    public void onGlobalLayout() {
        mBind.scrollview.smoothScrollTo(0, MeasureUtils.dp2px(
                LoginActivity.this, 300));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login && !TextUtils.isEmpty(mBind.etUsername.getText())
                && !TextUtils.isEmpty(mBind.etPassword.getText())) {//点击登录按钮，登录
            final String username = mBind.etUsername.getText().toString();
            final String password = mBind.etPassword.getText().toString();
            mLoginViewModel.login(new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {
                    mLoginViewModel.saveAccount(username, password, s, mBind.cbRememberPassword.isChecked());
                    startMainActivity();
                }
            }, username, password, new EditCallback(getWindow().getDecorView()), new ToastCallback(), new ClickCallback(mBind.btnLogin, this));
        } else if (view.getId() == R.id.iv_username_delete) {//点击清除图标，清除账号
            mBind.etUsername.setText(null);
        } else if (view.getId() == R.id.iv_password_delete) {//点击清除图标，清除密码
            mBind.etPassword.setText(null);
        }
    }

    /**
     * 跳转至MainAcivity
     */
    private void startMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
        finish();
    }

    /**
     * 跳转到登录页并且关闭所有activity
     */
    public static void startActivityAndClearTask(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
