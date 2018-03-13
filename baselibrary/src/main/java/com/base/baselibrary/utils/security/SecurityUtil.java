package com.base.baselibrary.utils.security;

import android.text.TextUtils;

/**
 * Created by yb on 2018/3/13.
 */

public class SecurityUtil {
    //必须16个 与getByte 方法一一对应
    private static final char[] KEYS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '!', '@', '#', '$', '%', '+'};

    public static String encrypt(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return "";
        }
        byte[] msgBytes = msg.getBytes();
        if (msgBytes == null || msgBytes.length <= 0) {
            return "";
        }
        char[] resultChar = new char[2 * msgBytes.length];
        for (int i = 0; i < msgBytes.length; i++) {
            int m = (msgBytes[i] & 0xFF);
            resultChar[2 * i] = KEYS[m >>> 4];
            resultChar[2 * i + 1] = KEYS[m & 0xF];
        }
        return new String(resultChar);
    }

    public static String decrypt(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        char[] contentChars = content.toCharArray();
        if (contentChars == null || contentChars.length <= 0 || (contentChars.length & 1) == 1) {
            return "";
        }
        byte[] resultByte = new byte[contentChars.length / 2];
        for (int i = 0; i < resultByte.length; i++) {
            resultByte[i] = (byte) (((getByte(contentChars[2 * i]) << 4) & 0xF0) |
                    (getByte(contentChars[2 * i + 1]) & 0x0F));
        }
        return new String(resultByte);
    }

    public static byte getByte(char a) {
        if (a == '!') {
            return 10;
        } else if (a == '@') {
            return 11;
        } else if (a == '#') {
            return 12;
        } else if (a == '$') {
            return 13;
        } else if (a == '%') {
            return 14;
        } else if (a == '+') {
            return 15;
        } else {
            return (byte) (a - '0');
        }
    }
}
