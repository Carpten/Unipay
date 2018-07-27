package com.chuangjiangx.unipay.preference;import android.content.Context;import android.content.SharedPreferences;import com.chuangjiangx.unipay.application.MyApp;//这个SharedPreferences用来操作跟用户无关的数据public class Preferences {    public static final String KEY_USERNAME = "user";    public static final String KEY_PASSWORD = "pwd";    public static final String KEY_REMEMBER_PASSWORD = "ischeck";    public static final String KEY_TOKEN = "token";    public static final String KEY_STORENAME = "storename";    public static final String KEY_NICKNAME = "nickname";    private volatile static Preferences mInstance;    private static final String NAME = "unipay";    private SharedPreferences mPref;    public static SharedPreferences get() {        if (mInstance == null) {            synchronized (Preferences.class) {                if (mInstance == null) {                    mInstance = new Preferences();                }            }        }        return mInstance.mPref;    }    public static SharedPreferences.Editor edit() {        if (mInstance == null) {            synchronized (Preferences.class) {                if (mInstance == null) {                    mInstance = new Preferences();                }            }        }        return mInstance.mPref.edit();    }    private Preferences() {        mPref = MyApp.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);    }}