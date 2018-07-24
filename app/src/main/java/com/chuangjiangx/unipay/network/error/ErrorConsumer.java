package com.chuangjiangx.unipay.network.error;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.login.LoginActivity;
import com.chuangjiangx.unipay.network.callback.ErrorNetCallback;
import com.google.gson.stream.MalformedJsonException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;

/**
 * @author yangshuiqiang
 * Time:2017/11/22 13:49
 */

public class ErrorConsumer implements Consumer<Throwable> {
    private Context mContext;
    private ErrorNetCallback[] mErrorNetCallbacks;

    public ErrorConsumer(Context context, ErrorNetCallback... errorNetCallbacks) {
        this.mContext = context;
        this.mErrorNetCallbacks = errorNetCallbacks;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        if (throwable != null) {
            if (!TextUtils.isEmpty(throwable.getMessage()))
                Log.e(ErrorConsumer.class.getSimpleName(), throwable.getMessage());
            if (throwable instanceof UnknownHostException || throwable instanceof ConnectException
                    || throwable instanceof SocketTimeoutException) {
                HttpException httpException = new HttpException(HttpException.NET_ERROR_CODE
                        , mContext.getString(R.string.network_fail));
                for (ErrorNetCallback errorNetCallback : mErrorNetCallbacks) {
                    errorNetCallback.onRequestFail(httpException);
                }
            } else if (throwable instanceof HttpException) {
                //000006错误，表示未登录，跳转至登录页
                if ("000006".equals(((HttpException) throwable).getErrCode())) {
                    LoginActivity.startActivityAndClearTask(mContext);
                }
                for (ErrorNetCallback errorNetCallback : mErrorNetCallbacks) {
                    errorNetCallback.onRequestFail((HttpException) throwable);
                }
                // TODO: 2017/11/26 根据不同类型处理不同情况
            } else if (throwable instanceof MalformedJsonException) {
                // TODO: 2017/11/27 Gson解析，格式错误
            }
        }
    }
}