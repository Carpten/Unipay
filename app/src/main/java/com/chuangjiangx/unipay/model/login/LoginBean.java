package com.chuangjiangx.unipay.model.login;

public class LoginBean {
    private String token;
    private MyinfoBean myinfoBean;

    public LoginBean(String token, MyinfoBean myinfoBean) {
        this.token = token;
        this.myinfoBean = myinfoBean;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public MyinfoBean getMyinfoBean() {
        return myinfoBean;
    }

    public void setMyinfoBean(MyinfoBean myinfoBean) {
        this.myinfoBean = myinfoBean;
    }
}
