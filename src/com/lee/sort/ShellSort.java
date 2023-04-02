package com.lee.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author LiJing
 * @version 1.0
 * 希尔排序
 */
public class ShellSort {
    public static void main(String[] args) {
//        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};

        //测试一下希尔排序的速度，给80000个数据，测试
        int[] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int) (Math.random() * 8000000);//生成一个 [0, 8000000) 数
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        String date1Str = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是=" + date1Str);

//        shellSort(arr);//交换法
        shellSort2(arr);//移动法

        Date date2 = new Date();
        String date2Str = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是=" + date2Str);
    }

    //希尔排序时，对有序序列在插入时采用 交换法
    public static void shellSort(int[] arr) {
        int temp = 0;
        int count = 0;
        //增量gap，并逐步的缩小增量
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //每一轮排序后，组内有序
            for (int i = gap; i < arr.length; i++) {
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
//            System.out.println("希尔排序第" + (++count) + "轮后：" + Arrays.toString(arr));
        }
    }

    //希尔排序时，对有序序列在插入时采用 移动法
    public static void shellSort2(int[] arr) {
        int count = 0;
        int insertValue = 0;
        int insertIndex = 0;
        //增量gap，并逐步的缩小增量
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //每一轮排序后，组内有序
            for (int i = gap; i < arr.length; i++) {
                insertValue = arr[i];
                insertIndex = i - gap;
                //给insertValue找到插入的位置
                while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
                    arr[insertIndex + gap] = arr[insertIndex];
                    insertIndex -= gap;
                }
                //当退出while循环时，说明插入的位置找到，该位置下标为 insertIndex + gap
                if (insertIndex + gap != i) {
                    arr[insertIndex + gap] = insertValue;
                }
            }
//            System.out.println("希尔排序第" + (++count) + "轮后：" + Arrays.toString(arr));
        }
    }

}
