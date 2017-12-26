package com.yb.demo.other.java.algorithm.sort;

import java.util.Random;

/**
 * 生成无须的数组
 * Created by yb on 2017/12/20.
 */
public class Util {
    public static int[] randomArray(int length, int min, int max) {
        if (length <= 0) {
            return null;
        }
        int[] result = new int[length];
        int dx = max - min;
        for (int i = 0; i < length; i++) {
            result[i] = (int) (min + Math.random() * dx);
        }
        return result;
    }
}
