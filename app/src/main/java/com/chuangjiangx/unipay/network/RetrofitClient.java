package com.chuangjiangx.unipay.network;

import android.support.annotation.NonNull;

import com.chuangjiangx.unipay.model.network.ResponseBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
                        Request request = original.newBuilder()
//                                .header("token", Config.token == null ? "" : Config.token)
                                .build();
                        return chain.proceed(request);
                    }
                })
//                .addInterceptor(getlogging())//添加日志
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)//设置连接超时时间
                .build();

        mApiServer = new retrofit2.Retrofit.Builder()
                .baseUrl(InternetConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//GSON转化器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//Rxjava转化器
                .client(okHttpClient)
                .build().create(ApiServer.class);
    }

//    /**
//     * 打印请求
//     */
//    private HttpLoggingInterceptor getlogging() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                Log.i("okhttp", message);
//            }
//        });
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        return interceptor;
//    }

    /**
     * token拦截器，在token过期时会重新登录获取新token
     */
    private class TokenInterceptor implements Interceptor {

        private Gson gson = new Gson();

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (isTokenExpired(response)) {
//                String clientid = PushManager.getInstance().getClientid(MyApplication.getApp());
                FormBody formBody = new FormBody.Builder()
//                        .add("uid", Config.uid == null ? "" : Config.uid)
//                        .add("cid", clientid == null ? "" : clientid)
//                        .add("username", Config.username == null ? "" : Config.username)
//                        .add("password", Config.password == null ? "" : Config.password)
                        .build();
                Request tokenRequest = new Request.Builder()
                        .url(InternetConfig.BASE_URL + "/login")
                        .post(formBody)
                        .build();
                OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                        .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                        .build();
                ResponseBody tokenBody = okHttpClient.newCall(tokenRequest).execute().body();
                if (tokenBody != null) {//这里tokenBody不会为空，为空会抛出异常
                    String str = tokenBody.string();
//                    LoginBeanWrap loginBeanWrap = gson.fromJson(str, LoginBeanWrap.class);
//                    if (loginBeanWrap.isSuccess()) {
//                        Config.token = loginBeanWrap.getLoginBean().getToken();
//                        MyinfoPreferences.edit()
//                                .putString(MyinfoPreferences.KEY_TOKEN, Config.token)
//                                .apply();
//                        Request newRequest = chain.request()
//                                .newBuilder()
//                                .header("token", Config.token == null ? "" : Config.token)
//                                .build();
//                        //重新请求
//                        return chain.proceed(newRequest);
//                    }
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
                    ResponseBean responseBean = gson.fromJson(bodyString, ResponseBean.class);
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

//    /**
//     * 登录
//     *
//     * @param username 用户名
//     * @param password 密码
//     * @param cid      个推ID
//     * @param uid      UID
//     */
//    public Flowable<LoginBean> login(String username, String password, String cid, String uid) {
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("username", username);
//        hashMap.put("password", password);
//        hashMap.put("uid", uid);
//        hashMap.put("cid", cid);
//        return mApiServer.login(hashMap).subscribeOn(Schedulers.from(singleExecutorService))
//                .map(new ModelHandler<LoginBean>());
//    }
//
//
//    public Flowable<UserInfo> getUserInfo() {
//        return mApiServer.getUserInfo().subscribeOn(Schedulers.io())
//                .map(new ModelHandler<UserInfo>());
//    }
//
//    /**
//     * 获取用户权限信息
//     */
//    public Flowable<List<PermissionCode>> getUserAuthority() {
//        return mApiServer.getComponentList().subscribeOn(Schedulers.io())
//                .map(new ModelHandler<List<PermissionCode>>());
//    }
//
//    public Flowable<List<CountInfo>> getCountInfo() {
//        return mApiServer.getCountInfo().subscribeOn(Schedulers.io())
//                .map(new ModelHandler<List<CountInfo>>());
//    }
//
//    /**
//     * 获取扩展应用列表，首页我的使用
//     *
//     * @return 拓展应用列表
//     */
//    public Flowable<ExtendApplicationBean> getExtendApplication() {
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("page.pageNO", 1);
//        hashMap.put("page.everyPageCount", 4);
//        hashMap.put("page.totalCount", 4);
//        return mApiServer.getExtendApplication(hashMap).subscribeOn(Schedulers.io())
//                .map(new ModelHandler<ExtendApplicationBean>());
//    }
//
//    /**
//     * 获取扩展应用列表，扩展应用页使用
//     *
//     * @return 拓展应用列表
//     */
//    public Flowable<List<ExtendApplicationBean.OpenApplicationBean>> getExtendList() {
//        return mApiServer.getExtendList().subscribeOn(Schedulers.io())
//                .map(new ModelHandler<List<ExtendApplicationBean.OpenApplicationBean>>());
//    }
//
//    /**
//     * 获取扩展应用URL
//     *
//     * @param id 拓展应用列表中对应的id
//     * @return 拓展应用具体URL
//     */
//    public Flowable<ExtendCodeBean> getApplicationCode(long id) {
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("id", id);
//        return mApiServer.getApplicationCode(hashMap).subscribeOn(Schedulers.io())
//                .map(new ModelHandler<ExtendCodeBean>());
//    }
//
//    /**
//     * 退出登录
//     */
//    public Flowable<ResponseBean> logout(String token) {
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("token", token);
//        return mApiServer.logout(hashMap).subscribeOn(Schedulers.from(singleExecutorService));
//    }
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
//
//    /**
//     * 获取商户列表
//     *
//     * @param pageNum    当前页数
//     * @param status     状态，0：未启用，1：待审核，2：已签约，3：过期，4：注销
//     * @param searchName 搜索内容
//     */
//    public Flowable<MerchantList> getMerchantList(String roleCode, int pageNum, int status, String searchName) {
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("pageNumber", pageNum);
//        hashMap.put("pageSize", Page.EVERY_PAGE_COUNT);
//        hashMap.put("status", status);
//        hashMap.put("name", searchName);
//        return mApiServer.getMerchantList(Role.getPathFromRole(roleCode), hashMap)
//                .subscribeOn(Schedulers.io()).map(new ModelHandler<MerchantList>());
//    }
//
//
//    /**
//     * 获取运营商列表
//     *
//     * @param pageNum    当前页数
//     * @param status     状态，0：未启用，1：待审核，2：已签约，3：过期，4：注销
//     * @param searchName 搜索内容
//     */
//    public Flowable<OperatorBean> getOperatorList(String roleCode, int pageNum, int status, String searchName) {
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("pageNumber", pageNum);
//        hashMap.put("pageSize", Page.EVERY_PAGE_COUNT);
//        if (status != -1) {
//            hashMap.put("status", status);
//        }
//        hashMap.put("name", searchName);
//        return mApiServer.getOperatorList(Role.getPathFromRole(roleCode), hashMap)
//                .subscribeOn(Schedulers.io()).map(new ModelHandler<OperatorBean>());
//    }
//
//    /**
//     * 获取渠道商列表
//     *
//     * @param pageNum    当前页数
//     * @param status     状态，0：未启用，1：待审核，2：已签约，3：过期，4：注销
//     * @param searchName 搜索内容
//     */
//    public Flowable<ChannelBean> getChannelList(String roleCode, int pageNum, int status, String searchName) {
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("pageNumber", pageNum);
//        hashMap.put("pageSize", Page.EVERY_PAGE_COUNT);
//        if (status != -1) {
//            hashMap.put("status", status);
//        }
//        hashMap.put("name", searchName);
//        return mApiServer.getChannelList(Role.getPathFromRole(roleCode), hashMap)
//                .subscribeOn(Schedulers.io()).map(new ModelHandler<ChannelBean>());
//    }
//
//    /**
//     * 获取业务员列表
//     *
//     * @param pageNum    当前页数
//     * @param status     状态，0：未启用，1：待审核，2：已签约，3：过期，4：注销
//     * @param searchName 搜索内容
//     */
//    public Flowable<SalesListBean> getSalesmanList(String roleCode, int pageNum, int status, String searchName) {
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("pageNumber", pageNum);
//        hashMap.put("pageSize", Page.EVERY_PAGE_COUNT);
//        if (status != -1) {
//            hashMap.put("status", status);
//        }
//        hashMap.put("name", searchName);
//        return mApiServer.getSalesmanList(Role.getPathFromRole(roleCode), hashMap)
//                .subscribeOn(Schedulers.io()).map(new ModelHandler<SalesListBean>());
//    }
}