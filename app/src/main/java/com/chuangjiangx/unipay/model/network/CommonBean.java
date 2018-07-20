package com.chuangjiangx.unipay.model.network;

/**
 * @author: yangshuiqiang
 * Time:2017/11/22 13:32
 */

public class CommonBean<T> extends ResponseBean {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
