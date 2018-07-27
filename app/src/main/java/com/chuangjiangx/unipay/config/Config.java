package com.chuangjiangx.unipay.config;

import com.chuangjiangx.unipay.preference.Preferences;

/**
 * @author: yangshuiqiang
 * Time:2018/1/5
 */
public class Config {

    /**
     * 用来判断程序是否正常启动，在SplashActivity中置为true
     */
    public static boolean sRightStart;

    /**
     * 接口所需token值
     */
    public static String sToken;

    /**
     * 登录用户名
     */
    public static String sUsername;

    /**
     * 登录密码
     */
    public static String sPassword;
    /**
     * 门店名
     */
    public static String sStoreName;

    /**
     * 用户昵称
     */
    public static String sNickName;


    public static boolean initConfig() {
        sRightStart = true;
        sToken = Preferences.get().getString(Preferences.KEY_TOKEN, null);
        sUsername = Preferences.get().getString(Preferences.KEY_USERNAME, null);
        sPassword = Preferences.get().getString(Preferences.KEY_PASSWORD, null);
        sStoreName = Preferences.get().getString(Preferences.KEY_STORENAME, null);
        sNickName = Preferences.get().getString(Preferences.KEY_NICKNAME, null);
        return sToken != null && sUsername != null && sPassword != null
                && sStoreName != null && sNickName != null;
    }

    public static void clear() {
        Preferences.edit().remove(Preferences.KEY_TOKEN).apply();
        sToken = null;
    }
}