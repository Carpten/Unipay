package com.chuangjiangx.unipay.view.activity;

import com.chuangjiangx.unipay.network.NetBuilder;

public class BaseViewModel {

    protected NetBuilder mNetBuilder;

    public BaseViewModel(NetBuilder netBuilder) {
        mNetBuilder = netBuilder;
    }
}
