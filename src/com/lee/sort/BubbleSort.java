package com.lee.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author LiJing
 * @version 1.0
 * 冒泡排序
 */
public class BubbleSort {
    public static void main(String[] args) {
        /*
        int[] arr = {3, 9, -1, 10, -2};
//        int[] arr = {3, 9, -1, 10, 20};

        System.out.println("排序前的数组：");
        System.out.println(Arrays.toString(arr));

        bubbleSort(arr);

        System.out.println("排序后的数组：");
        System.out.println(Arrays.toString(arr));

         */

        //测试一下冒泡排序的速度 O(n^2)，给80000个数据，测试
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 8000000);//生成一个 [0, 8000000) 数
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        String date1Str = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是=" + date1Str);

        bubbleSort(arr);

        Date date2 = new Date();
        String date2Str = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是=" + date2Str);
    }

    // 冒泡排序的时间复杂度 O(n^2)
    public static void bubbleSort(int[] arr) {
        int temp = 0;//临时变量
        boolean flag = false;//标识变量，表示是否进行过交换
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                //如果前面的数比后面的数大，则交换
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
//            System.out.printf("第%d趟排序后的数组：%s\n", i + 1, Arrays.toString(arr));

            if (!flag) {//在一趟排序中，一次交换都没有发生过
                break;
            } else {
                flag = false;//重置flag ，进行下次判断
            }
        }
    }
}
