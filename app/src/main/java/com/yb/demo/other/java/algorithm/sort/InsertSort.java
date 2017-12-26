package com.yb.demo.other.java.algorithm.sort;

/**
 * 插入排序
 * Created by yb on 2017/12/20.
 */
public class InsertSort {
    public static int[] sortUp(int[] array) {
        int i, j, insertValue;
        for (i = 1; i < array.length; i++) {
            insertValue = array[i];
            j = i - 1;
            while (j >= 0 && array[j] > insertValue) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = insertValue;
        }
        return array;
    }

    public static int[] sortDown(int[] array, int l, int h) {
        int i, j, insertValue;
        for (i = 1; i < array.length; i++) {
            insertValue = array[i];
            j = i - 1;
            while (j >= 0 && array[j] < insertValue) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = insertValue;
        }
        return array;
    }

    public static void main(String[] args) {
        int[] array = Util.randomArray(10, 0, 100);
        System.out.println("源数据:");
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("升序:");
        array = sortUp(array);
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("降序:");
        array = sortDown(array, 0, array.length - 1);
        for (int i : array) {
            System.out.print(i + " ");
        }
    }
}
