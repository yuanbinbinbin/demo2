package com.yb.demo.other.java.algorithm.sort;

/**
 * 快速排序
 * 算法思想:在数组中选择一个基准点,升序时:将小于基准点的值放在数组前面、大于基准点时放在后面，接着分别排前面和后面的数组<br>
 * 快速排序在序列中元素很少时，效率将比较低，因此一般在序列中元素很少时使用插入排序，这样可以提高整体效率。<br>
 *  时间:O(logn)~O(n^2) 空间：O(1)
 * Created by yb on 2017/12/20.
 */
public class QuickSort {

    public static int[] sortUp(int[] array, int l, int h) {
        if (l >= h) {
            return array;
        }
        int middleValue = array[l];
        int low = l;
        int high = h;
        while (low < high) {
            while (high > low && array[high] > middleValue) {
                high--;
            }
            array[low] = array[high];
            while (low < high && array[low] < middleValue) {
                low++;
            }
            array[high] = array[low];
        }
        array[low] = middleValue;
        sortUp(array, l, low - 1);
        sortUp(array, low + 1, h);
        return array;
    }

    public static int[] sortDown(int[] array, int l, int h) {
        if (l >= h) {
            return array;
        }
        int low = l;
        int high = h;
        int middleValue = array[l];
        while (low < high) {
            while (low < high && array[high] < middleValue) {
                high--;
            }
            array[low] = array[high];
            while (low < high && array[low] > middleValue) {
                low++;
            }
            array[high] = array[low];
        }
        array[low] = middleValue;
        sortDown(array, l, low - 1);
        sortDown(array, low + 1, h);
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
        array = sortUp(array, 0, array.length - 1);
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
