package com.lee.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiJing
 * @version 1.0
 * 顺序(线性)查找算法
 */
public class SeqSearch {
    public static void main(String[] args) {
        int[] arr = {90, -2, 34, -1, 8, 0, 23, 11, -2};

        int resIndex = seqSearch(arr, -2);
        if (resIndex == -1) {
            System.out.println("没找到");
        } else {
            System.out.println("找到了第一个符合条件的值，下标是：" + resIndex);
        }

        List<Integer> resIndexList = seqSearch2(arr, -2);
        if (resIndexList.size() == 0) {
            System.out.println("没找到");
        } else {
            System.out.println("找到了全部符合条件的值，下标分别是：" + resIndexList);
        }
    }

    //查找到第一个符合条件的值
    public static int seqSearch(int[] arr, int findVal) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == findVal) {
                return i;
            }
        }
        return -1;
    }

    //查找到全部符合条件的值
    public static List<Integer> seqSearch2(int[] arr, int findVal) {
        List<Integer> resIndexList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == findVal) {
                resIndexList.add(i);
            }
        }
        return resIndexList;
    }
}
