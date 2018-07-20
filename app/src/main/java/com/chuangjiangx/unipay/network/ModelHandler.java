package com.chuangjiangx.unipay.network;


import com.chuangjiangx.unipay.model.network.CommonBean;
import com.chuangjiangx.unipay.network.error.HttpException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 网络请求结果数据处理类
 *
 * @author: yangshuiqiang
 * Time:2017/11/22 14:42
 */

class ModelHandler<T> implements Function<CommonBean, T> {
    @Override
    public T apply(@NonNull CommonBean commonBean) throws Exception {
        if (!commonBean.isSuccess()) {//isSuccess未false时，抛出HttpException异常
            throw new HttpException(commonBean.getErrCode(), commonBean.getErrMsg());
        } else {
            //noinspection unchecked
            return (T) commonBean.getData();//isSuccess未true时，返回实体类
        }
    }
}
