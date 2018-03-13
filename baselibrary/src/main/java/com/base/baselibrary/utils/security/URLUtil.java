package com.base.baselibrary.utils.security;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * url encode/decode
 * Created by yb on 2018/3/13.
 */

public class URLUtil {
    public static String encode(String content) {
        if (content == null) {
            return content;
        }
        try {
            content = URLEncoder.encode(content, "UTF-8");
        } catch (Exception e) {
            content = null;
        }
        return content;
    }

    public static String decode(String content) {
        if (content == null) {
            return content;
        }
        try {
            content = URLDecoder.decode(content, "UTF-8");
        } catch (Exception e) {
            content = null;
        }
        return content;
    }
}
