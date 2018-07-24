package com.chuangjiangx.unipay.config;

import com.chuangjiangx.unipay.preference.Preferences;

/**
 * @author: yangshuiqiang
 * Time:2018/1/5
 */
public class Config {
    //用来判断程序是否正常启动，在SplashActivity中置为true
    public static boolean sRightStart;

    public static String sToken;
    public static String sUsername;
    public static String sPassword;
    public static String sStoreName;


    public static boolean initConfig() {
        sRightStart = true;
        sToken = Preferences.get().getString(Preferences.KEY_TOKEN, null);
        sUsername = Preferences.get().getString(Preferences.KEY_USERNAME, null);
        sPassword = Preferences.get().getString(Preferences.KEY_PASSWORD, null);
        sStoreName = Preferences.get().getString(Preferences.KEY_STORENAME, null);
        return sToken != null && sUsername != null && sPassword != null && sStoreName != null;
    }

    public static void clear() {
        Preferences.edit().remove(Preferences.KEY_TOKEN).apply();
        sToken = null;
    }
}