package com.chuangjiangx.unipay.model.c2b;

public class UrlBean {


    /**
     * payQrcodeUrl : http://testsh.chuangjiangx.com/api/redirect/pay-qrcode-redirect?userId=162&totalFee=0.01&channel=2&endTime=1532917097329&id=1
     * endTime : 2018-07-30 10:18:17
     */

    private String payQrcodeUrl;
    private String endTime;

    public String getPayQrcodeUrl() {
        return payQrcodeUrl;
    }

    public void setPayQrcodeUrl(String payQrcodeUrl) {
        this.payQrcodeUrl = payQrcodeUrl;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
