package com.lee.search;

import java.util.Arrays;

/**
 * @author LiJing
 * @version 1.0
 * 斐波那契(黄金分割法)查找算法
 */
public class FibonacciSearch {
    private static int maxsize = 20;

    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        int resIndex = fibonacciSearch(arr, 8);
        System.out.println("resIndex= " + resIndex);
    }

    public static int[] getFibonacci() {
        int[] fib = new int[maxsize];
        fib[0] = 1;
        fib[1] = 1;
        for (int i = 2; i < fib.length; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib;
    }

    public static int fibonacciSearch(int[] arr, int findVal) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        int[] fib = getFibonacci();//获取到斐波那契数列
        int k = 0;//表示斐波那契数值的下标
        while (arr.length > fib[k] - 1) {
            k++;
        }
        int[] temp = Arrays.copyOf(arr, fib[k] - 1);
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = arr[high];
        }
        while (low <= high) {
            mid = low + fib[k - 1] - 1;
            if (findVal < temp[mid]) {
                high = mid - 1;
                k -= 1;
            } else if (findVal > temp[mid]) {
                low = mid + 1;
                k -= 2;
            } else {
                if (mid <= high) {
                    return mid;
                } else {
                    return high;
                }
            }
        }
        return -1;
    }
}
