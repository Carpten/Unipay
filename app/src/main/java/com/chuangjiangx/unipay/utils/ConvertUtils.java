package com.chuangjiangx.unipay.utils;

import java.text.DecimalFormat;

public class ConvertUtils {

    public static String amount2String(double amount) {

        int amount0 = (int) amount;
        if (amount - amount0 == 0) {
            return String.valueOf(amount0);
        } else {
            int amount1 = (int) (amount * 10);
            if (amount * 10 - amount1 == 0) {
                DecimalFormat df = new DecimalFormat("######0.0");
                return df.format(amount);
            } else {
                DecimalFormat df = new DecimalFormat("######0.00");
                return df.format(amount);
            }
        }
    }

    public static String amount2SpeakString(double amount) {
        int amount0 = (int) amount;
        if (amount - amount0 == 0) {
            DecimalFormat df = new DecimalFormat("######0.00");
            return df.format(amount);
        } else {
            int amount1 = (int) (amount * 10);
            if (amount * 10 - amount1 == 0) {
                DecimalFormat df = new DecimalFormat("######0.0");
                return df.format(amount);
            } else {
                DecimalFormat df = new DecimalFormat("######0.00");
                return df.format(amount);
            }
        }
    }
}
