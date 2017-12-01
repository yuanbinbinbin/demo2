package com.yb.demo.utils;

/**
 * 安全转换
 * Created by yb on 2017/2/10.
 */
public class SafeConvertUtil {
    /**
     * 强转为int类型
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public final static int convertToInt(Object value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        String valueTemp = value.toString().trim();
        try {
            return Integer.valueOf(valueTemp);
        } catch (Exception e) {
            return (int) convertToDouble(value, defaultValue);
        }
    }

    /**
     * 强转为double类型
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public final static double convertToDouble(Object value, double defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        String valueTemp = value.toString().trim();
        try {
            return Double.valueOf(valueTemp);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 强转为String类型
     *
     * @param value
     * @param defaultValue
     * @return
     */
    public final static String convertToString(Object value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value.toString();
    }

    /**
     * 强转为指定的实体类
     *
     * @param value
     * @param classType
     * @param defaultValue
     * @param <T>
     * @return
     */
    public final static <T> T convertToEntity(Object value, Class<T> classType, T defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return (T) value;
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
