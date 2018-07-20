package com.chuangjiangx.unipay.network.error;

/**
 * @author: yangshuiqiang
 * Time:2017/11/22 15:17
 */

public class HttpException extends Exception {

    static final String NET_ERROR_CODE = "NET_BREAK";
    private String errCode;
    private String errMsg;


    public HttpException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }


    /**
     * @return 是否为网络异常
     */
    public boolean isNetError() {
        return NET_ERROR_CODE.equals(errCode);
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
