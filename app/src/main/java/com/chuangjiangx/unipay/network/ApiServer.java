package com.chuangjiangx.unipay.network;


import com.chuangjiangx.unipay.model.c2b.UrlBean;
import com.chuangjiangx.unipay.model.login.MyinfoBean;
import com.chuangjiangx.unipay.model.network.CommonBean;
import com.chuangjiangx.unipay.model.network.ResponseBean;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author: yangshuiqiang
 * Time:2017/11/20 16:43
 */

interface ApiServer {

    @FormUrlEncoded
    @POST("/main/app/login")
    Flowable<CommonBean<String>> login(@FieldMap Map<String, Object> body);


    @FormUrlEncoded
    @POST("/main/logout")
    Flowable<ResponseBean> logout(@FieldMap Map<String, Object> body);

    //
    @POST("/main/app/search-my-details")
    Flowable<CommonBean<MyinfoBean>> getUserInfo();

    @FormUrlEncoded
    @POST("/pay/create-pay-qrcode")
    Flowable<CommonBean<UrlBean>> createPayCode(@FieldMap Map<String, Object> body);
//
//    @POST("/app/component-list")
//    Flowable<CommonBean<List<PermissionCode>>> getComponentList();
//
//
//    @POST("/countinfo")
//    Flowable<CommonBean<List<CountInfo>>> getCountInfo();
//
//    @FormUrlEncoded
//    @POST("/extend/bcrm/extend-application")
//    Flowable<CommonBean<ExtendApplicationBean>> getExtendApplication(@FieldMap Map<String, Object> body);
//
//    @POST("/extend/bcrm/extend-list")
//    Flowable<CommonBean<List<ExtendApplicationBean.OpenApplicationBean>>> getExtendList();
//
//    @FormUrlEncoded
//    @POST("/extend/bcrm/click-application")
//    Flowable<CommonBean<ExtendCodeBean>> getApplicationCode(@FieldMap Map<String, Object> body);
//
//    @FormUrlEncoded
//    @POST("/app/merchant/{rolecode}")
//    Flowable<CommonBean<MerchantList>> getMerchantList(
//            @Path("rolecode") String rolecode, @FieldMap Map<String, Object> body);
//
//    @FormUrlEncoded
//    @POST("/app/agent/{rolecode}")
//    Flowable<CommonBean<OperatorBean>> getOperatorList(
//            @Path("rolecode") String rolecode, @FieldMap Map<String, Object> body);
//
//    @FormUrlEncoded
//    @POST("/app/sub-agent/{rolecode}")
//    Flowable<CommonBean<ChannelBean>> getChannelList(
//            @Path("rolecode") String rolecode, @FieldMap Map<String, Object> body);
//
//    @FormUrlEncoded
//    @POST("/app/manager/search-{rolecode}")
//    Flowable<CommonBean<SalesListBean>> getSalesmanList(
//            @Path("rolecode") String rolecode, @FieldMap Map<String, Object> body);
}