package com.lee.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiJing
 * @version 1.0
 * 二分查找算法
 */
public class BinarySearch {
    public static void main(String[] args) {
        //注意：使用二分查找的前提是 该数组是有序的
        int[] arr = {1, 8, 10, 89, 1000, 1000, 1000, 1234};
//        int resIndex = binarySearch(arr, 0, arr.length - 1, 4);
//        System.out.println("resIndex = " + resIndex);

        List<Integer> resIndexList = binarySearch2(arr, 0, arr.length - 1, 1000);
        System.out.println("resIndexList = " + resIndexList);
    }

    /**
     *
     * @param arr 有序数组
     * @param left 左边索引
     * @param right 右边索引
     * @param findVal 要查找的值
     * @return 如果找到，就返回对应的下标，如果没有找到，返回-1
     */
    public static int binarySearch(int[] arr, int left, int right, int findVal) {
        //当 left > right 时，说明递归完整个数组，但是没有找到
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

    /*
     * {1,8, 10, 89, 1000, 1000, 1234} 当一个有序数组中，
     * 有多个相同的数值时，如何将所有的数值都查找到，比如这里的 1000
     *
     * 思路分析
     * 1. 在找到mid 索引值时，不要马上返回
     * 2. 向mid 索引值的左边扫描，将所有满足 1000 的元素的下标，加入到集合ArrayList
     * 3. 向mid 索引值的右边扫描，将所有满足 1000 的元素的下标，加入到集合ArrayList
     * 4. 将Arraylist返回
     */
    public static List<Integer> binarySearch2(int[] arr, int left, int right, int findVal) {
        if (left > right || findVal < arr[0] || findVal > arr[arr.length - 1]) {
            return new ArrayList<Integer>();
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        if (findVal > midVal) {//向右递归
            return binarySearch2(arr, mid + 1, right, findVal);
        } else if (findVal < midVal) {//向左递归
            return binarySearch2(arr, left, mid - 1, findVal);
        } else {
            List<Integer> resIndexList = new ArrayList<Integer>();
            int temp = mid - 1;
            while (true) {
                if (temp < 0 || arr[temp] != findVal) {
                    break;
                }
                resIndexList.add(temp);
                temp--;
            }
            resIndexList.add(mid);
            temp = mid + 1;
            while (true) {
                if (temp > arr.length - 1 || arr[temp] != findVal) {
                    break;
                }
                resIndexList.add(temp);
                temp++;
            }
            return resIndexList;
        }
    }
}
