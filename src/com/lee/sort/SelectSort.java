package com.lee.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author LiJing
 * @version 1.0
 * 选择排序
 */
public class SelectSort {
    public static void main(String[] args) {
        /*
        int[] arr = {101, 34, 119, 1};
        System.out.println("排序前的数组：");
        System.out.println(Arrays.toString(arr));
        selectSort(arr);
        System.out.println("排序后的数组：");
        System.out.println(Arrays.toString(arr));

         */

        //测试一下选择排序的速度 O(n^2)，给80000个数据，测试
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 8000000);//生成一个 [0, 8000000) 数
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        String date1Str = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是=" + date1Str);

        selectSort(arr);

        Date date2 = new Date();
        String date2Str = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是=" + date2Str);
    }

    public static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) {
                    minIndex = j;
                    min = arr[j];
                }
            }
            if (min != arr[i]) {
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
//            System.out.printf("第%d轮排序后的数组：%s\n", i + 1, Arrays.toString(arr));
        }
    }

}
