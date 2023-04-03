package com.lee.search;

/**
 * @author LiJing
 * @version 1.0
 * 插值查找算法
 */
public class InsertValueSearch {
    public static void main(String[] args) {
//        int [] arr = new int[100];
//		for(int i = 0; i < 100; i++) {
//			arr[i] = i + 1;
//		}
        int arr[] = {1, 8, 10, 89, 1000, 1000, 1234};
        int resIndex = insertValueSearch(arr, 0, arr.length - 1, 1);
//        int resIndex = binarySearch(arr, 0, arr.length - 1, 1);
        System.out.println("resIndex= " + resIndex);

    }

    public static int insertValueSearch(int[] arr, int left, int right, int findVal) {
        System.out.println("插值查找被调用~~");
        //注意：findVal < arr[0]  和  findVal > arr[arr.length - 1] 必须需要
        //否则得到的 mid 可能越界
        if (left > right || findVal < arr[0] || findVal > arr[arr.length - 1]) {
            return -1;
        }
        // 求出自适应mid
        int mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - arr[left]);
        int midVal = arr[mid];
        if (findVal > midVal) {//向右递归
            return insertValueSearch(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) {//向左递归
            return insertValueSearch(arr, left, mid - 1, findVal);
        } else {
            return mid;
        }
    }

    public static int binarySearch(int[] arr, int left, int right, int findVal) {
        System.out.println("二分查找被调用~~");
        if (left > right || findVal < arr[0] || findVal > arr[arr.length - 1]) {
            return -1;
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        if (findVal > midVal) {//向右递归
            return binarySearch(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) {//向左递归
            return binarySearch(arr, left, mid - 1, findVal);
        } else {
            return mid;
        }
    }
}
