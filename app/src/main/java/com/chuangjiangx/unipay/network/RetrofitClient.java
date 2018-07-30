package com.chuangjiangx.unipay.network;

import android.support.annotation.NonNull;

import com.chuangjiangx.unipay.config.Config;
import com.chuangjiangx.unipay.model.c2b.UrlBean;
import com.chuangjiangx.unipay.model.login.MyinfoBean;
import com.chuangjiangx.unipay.model.network.CommonBean;
import com.chuangjiangx.unipay.model.network.ResponseBean;
import com.chuangjiangx.unipay.preference.Preferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: yangshuiqiang
 * Time:2017/11/20 16:42
 */

public class RetrofitClient {

    private volatile static RetrofitClient mRetrofitClient;
    private static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * 在分页列表中，可以使用这个线程池可以控制网络返回按照网络请求顺序返回
     * 这样可以避免极端条件下，列表拼装错误的问题，如先加载下一页，再下拉刷新数据，
     * 如果返回顺序不对，将导致数据错误
     **/
    private ExecutorService singleExecutorService = Executors.newSingleThreadExecutor();

    //十秒网络连接超时
    private static final long TIME_OUT = 1000 * 10;

    private ApiServer mApiServer;

    private RetrofitClient() {
        //用Chrome 调试
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new TokenInterceptor())//token处理拦截器
                .addInterceptor(new Interceptor() {//请求头添加token
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request original = chain.request();
                        String url = original.url().toString();
                        if (url != null && !url.contains("/main/app/login")) {
                            Request request = original.newBuilder()
                                    .header("token", Config.sToken == null ? "" : Config.sToken)
                                    .build();
                            return chain.proceed(request);
                        } else {
                            Request request = original.newBuilder().build();
                            return chain.proceed(request);
                        }

                    }
                })
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)//设置连接超时时间
                .build();

        mApiServer = new retrofit2.Retrofit.Builder()
                .baseUrl(InternetConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//GSON转化器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//Rxjava转化器
                .client(okHttpClient)
                .build().create(ApiServer.class);
    }

    /**
     * token拦截器，在token过期时会重新登录获取新token
     */
    private class TokenInterceptor implements Interceptor {

        private Gson mGson = new Gson();

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (isTokenExpired(response)) {
                FormBody formBody = new FormBody.Builder()
                        .add("username", Config.sUsername == null ? "" : Config.sUsername)
                        .add("password", Config.sPassword == null ? "" : Config.sPassword)
                        .add("macCode", "1")
                        .add("cid", "1")
                        .add("deviceType", "1")
                        .build();
                Request tokenRequest = new Request.Builder()
                        .url(InternetConfig.BASE_URL + "/main/app/login")
                        .post(formBody)
                        .build();
                OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                        .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                        .build();
                ResponseBody tokenBody = okHttpClient.newCall(tokenRequest).execute().body();
                if (tokenBody != null) {//这里tokenBody不会为空，为空会抛出异常
                    String str = tokenBody.string();
                    CommonBean<String> loginBeanWrap = mGson.fromJson(str
                            , new TypeToken<CommonBean<String>>() {
                            }.getType());
                    if (loginBeanWrap.isSuccess()) {
                        Config.sToken = loginBeanWrap.getData();
                        Preferences.edit()
                                .putString(Preferences.KEY_TOKEN, Config.sToken)
                                .apply();
                        Request newRequest = chain.request()
                                .newBuilder()
                                .header("token", Config.sToken == null ? "" : Config.sToken)
                                .build();
                        //重新请求
                        return chain.proceed(newRequest);
                    }
                }
            }
            return response;
        }

        /**
         * 用来判断token是否过期，000006为token过期代码
         */
        private boolean isTokenExpired(Response response) {
            ResponseBody responseBody = response.body();
            if (response.code() == 200 && responseBody != null) {
                try {
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE);
                    Buffer buffer = source.buffer();
                    String bodyString = buffer.clone().readString(UTF8);
                    ResponseBean responseBean = mGson.fromJson(bodyString, ResponseBean.class);
                    if ("000006".equals(responseBean.getErrCode())) {
                        return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }

    /**
     * 获取RetrofitClient对象
     *
     * @return RetrofitClient对象
     */
    public static RetrofitClient get() {
        if (mRetrofitClient == null) {
            synchronized (RetrofitClient.class) {
                if (mRetrofitClient == null) {
                    mRetrofitClient = new RetrofitClient();
                }
            }
        }
        return mRetrofitClient;
    }

    /**
     * Retrofit第一次初始化比较耗时，可以在Application中先初始化一次
     */
    public static void init() {
        RetrofitClient.get();
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    public Flowable<String> login(String username, String password) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("username", username);
        hashMap.put("password", password);
        hashMap.put("macCode", "9f3847c1-d84d-40ec-b3f9-38212647741a");
        hashMap.put("cid", "16b7ab463db997d15a62527e8708f49e");
        hashMap.put(" deviceType", "1");
        return mApiServer.login(hashMap).subscribeOn(Schedulers.io())
                .map(new ModelHandler<String>());
    }


    public Flowable<MyinfoBean> getUserInfo() {
        return mApiServer.getUserInfo().subscribeOn(Schedulers.io())
                .map(new ModelHandler<MyinfoBean>());
    }


    /**
     * 退出登录
     */
    public Flowable<ResponseBean> logout(String token) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("token", token);
        return mApiServer.logout(hashMap).subscribeOn(Schedulers.io());
    }
//
//    /**
//     * 检测更新
//     *
//     * @return 最新版本号
//     */
//    public Flowable<String> checkUpdate() {
//        return Flowable.create(new FlowableOnSubscribe<String>() {
//            @Override
//            public void subscribe(@io.reactivex.annotations.NonNull FlowableEmitter<String> e) throws Exception {
//                Request request = new Request.Builder().url(InternetConfig.VERSION_URL).build();
//                OkHttpClient client = new OkHttpClient().newBuilder().build();
//                ResponseBody body = client.newCall(request).execute().body();
//                if (body != null) {
//                    VersionBean versionBean = new Gson().fromJson(body.string(), VersionBean.class);
//                    e.onNext(versionBean.getVersion());
//                    e.onComplete();
//                }
//            }
//        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io());
//    }

    /**
     * c2b获取支付二维码地址
     */
    public Flowable<UrlBean> createPayCode(double totalFee, String id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("totalFee", totalFee);
        hashMap.put("channel", "2");
        hashMap.put("id", id);
        return mApiServer.createPayCode(hashMap).subscribeOn(Schedulers.io())
                .map(new ModelHandler<UrlBean>());
    }


}