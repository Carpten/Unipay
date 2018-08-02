package com.chuangjiangx.unipay.model.c2b;

public class SuccessBean {


    /**
     * note :
     * orderType : 支付宝
     * orderNumber : 201808021414538647356370
     * payTime : 1533190499000
     * channel : 2
     * discountAmount : 0.0
     * payWay : 2
     * type : 1
     * merchantName : 我是支付宝
     * orderAmount : 0.01
     * merchantId : 142
     * realPayAmount : 0.01
     * storeName : 我是支付宝
     * attach :
     * id : 4608
     * storeUserId : 0
     * refundAmount : 0.0
     * merchantNum : 1425133480
     * updateTime : 1533190499745
     * storeId : 0
     * realname : 李超555
     * orderBody : 我是支付宝
     * paidInAmount : 0.01
     * statusText : 支付成功
     * payChannel : 4
     * merchantUserId : 162
     * status : 1
     */

    private String note;
    private String orderType;
    private String orderNumber;
    private long payTime;
    private int channel;
    private double discountAmount;
    private int payWay;
    private int type;
    private String merchantName;
    private double orderAmount;
    private int merchantId;
    private double realPayAmount;
    private String storeName;
    private String attach;
    private int id;
    private int storeUserId;
    private double refundAmount;
    private String merchantNum;
    private long updateTime;
    private int storeId;
    private String realname;
    private String orderBody;
    private double paidInAmount;
    private String statusText;
    private int payChannel;
    private int merchantUserId;
    private int status;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getPayWay() {
        return payWay;
    }

    public void setPayWay(int payWay) {
        this.payWay = payWay;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public double getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(double realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreUserId() {
        return storeUserId;
    }

    public void setStoreUserId(int storeUserId) {
        this.storeUserId = storeUserId;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getMerchantNum() {
        return merchantNum;
    }

    public void setMerchantNum(String merchantNum) {
        this.merchantNum = merchantNum;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getOrderBody() {
        return orderBody;
    }

    public void setOrderBody(String orderBody) {
        this.orderBody = orderBody;
    }

    public double getPaidInAmount() {
        return paidInAmount;
    }

    public void setPaidInAmount(double paidInAmount) {
        this.paidInAmount = paidInAmount;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public int getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(int merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
