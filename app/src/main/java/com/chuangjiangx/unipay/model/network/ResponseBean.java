package com.chuangjiangx.unipay.model.network;

/**
 * @author: yangshuiqiang
 * Time:2018/1/12
 */
public class ResponseBean {

    private boolean success;
    private String err_code;
    private String errCode;
    private String err_msg;
    private String errMsg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrCode() {
        return errCode == null ? err_code : errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg == null ? err_msg : errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
