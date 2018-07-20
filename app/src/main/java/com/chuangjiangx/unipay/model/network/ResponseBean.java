package com.chuangjiangx.unipay.model.network;

/**
 * @author: yangshuiqiang
 * Time:2018/1/12
 */
public class ResponseBean {

    private boolean success;
    private String errCode;
    private String errMsg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
