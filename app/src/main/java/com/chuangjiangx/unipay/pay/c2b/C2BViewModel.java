package com.chuangjiangx.unipay.pay.c2b;

import android.graphics.Bitmap;
import android.util.Log;

import com.chuangjiangx.unipay.R;
import com.chuangjiangx.unipay.application.MyApp;
import com.chuangjiangx.unipay.model.c2b.SuccessBean;
import com.chuangjiangx.unipay.model.c2b.UrlBean;
import com.chuangjiangx.unipay.model.network.CommonBean;
import com.chuangjiangx.unipay.network.InternetConfig;
import com.chuangjiangx.unipay.network.NetBuilder;
import com.chuangjiangx.unipay.network.RetrofitClient;
import com.chuangjiangx.unipay.pay.result.PayResultActivity;
import com.chuangjiangx.unipay.services.tts.SpeakService;
import com.chuangjiangx.unipay.utils.BarUtils;
import com.chuangjiangx.unipay.utils.MeasureUtils;
import com.chuangjiangx.unipay.view.activity.BaseViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

class C2BViewModel extends BaseViewModel {

    private WebSocketClient mWebSocketClient;

    private int i = 0;

    private C2BActivity mC2BActivity;

    C2BViewModel(C2BActivity activity, NetBuilder netBuilder) {
        super(netBuilder);
        mC2BActivity = activity;
    }


    void initSocket(final double amount) {
        try {
            String[] urlSplit = InternetConfig.BASE_URL.split("//");
            String protocolURL = "http:".equals(urlSplit[0]) ? "ws" : "wss";
            URI wsURI = new URI(protocolURL + "://" + urlSplit[1] + "/webSocketServer");
            mWebSocketClient = new WebSocketClient(wsURI) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                }

                @Override
                public void onMessage(String msg) {
                    handleWsMessage(msg, amount);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                }

                @Override
                public void onError(Exception e) {
                }
            };
            mWebSocketClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void closeWebSocket() {
        if (mWebSocketClient != null) {
            try {
                mWebSocketClient.close();
            } catch (Exception ignored) {
            }
        }
    }

    private void handleWsMessage(String message, double amount) {
        Log.i("test", "messag:" + message);
        if (i == 0) {
            mNetBuilder.request(RetrofitClient.get().createPayCode(amount, message)
                            .map(new Function<UrlBean, Bitmap>() {
                                @Override
                                public Bitmap apply(UrlBean urlBean) throws Exception {
                                    int size = MeasureUtils.dp2px(MyApp.getContext(), 240);
                                    return BarUtils.encodeAsBitmap(urlBean.getPayQrcodeUrl()
                                            , BarcodeFormat.QR_CODE, size, size);
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())

                    , new Consumer<Bitmap>() {
                        @Override
                        public void accept(Bitmap bitmap) throws Exception {
                            mC2BActivity.initQrView(bitmap);
                        }
                    });
        } else {
            try {
                CommonBean<SuccessBean> responseBean = new Gson().fromJson(message, new TypeToken<CommonBean<SuccessBean>>() {
                }.getType());

                if (responseBean.isSuccess()) {
                    PayResultActivity.startActivithy(mC2BActivity, amount, responseBean.getData().getOrderType());

                    mC2BActivity.finish();
                }
                i = 0;
            } catch (Exception e) {
                i = 0;
            }
        }
        i++;
    }


    void speak(String amout) {
        SpeakService.stopAndSpeak(mC2BActivity, mC2BActivity.getString(R.string.speak_amount, amout));
    }
}
