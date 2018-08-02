package com.chuangjiangx.unipay.model.rxbus;

public class RxBean {

    private int id;
    private Object data;

    public RxBean(int id, Object data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
