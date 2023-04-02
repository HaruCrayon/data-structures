package com.lee.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author LiJing
 * @version 1.0
 * 基数排序
 */
public class RadixSort {
    public static void main(String[] args) {
//        int[] arr = {53, 3, 542, 748, 14, 214};

        //测试一下基数排序的速度，给80000个数据，测试
        int[] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int) (Math.random() * 8000000);//生成一个 [0, 8000000) 数
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        String date1Str = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是=" + date1Str);

        radixSort(arr);

        Date date2 = new Date();
        String date2Str = simpleDateFormat.format(date2);
        System.out.println("排序后的时间是=" + date2Str);

//        System.out.println("基数排序后 arr=" + Arrays.toString(arr));
    }

    public static void radixSort(int[] arr) {
        //得到数组中最大的数
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        //得到最大数是几位数
        int maxLength = (max + "").length();

        //定义一个二维数组，表示10个桶, 每个桶就是一个一维数组
        //说明
        //1. 二维数组包含10个一维数组
        //2. 为了防止在放入数的时候，数据溢出，则每个一维数组(桶)，大小定为arr.length
        //3. 基数排序是使用空间换时间的经典算法
        int[][] bucket = new int[10][arr.length];

        //为了记录每个桶中，实际存放了多少个数据，定义一个一维数组来记录各个桶的每次放入的数据个数
        //比如：bucketElementCounts[0]，记录的就是 bucket[0] 桶的放入数据个数
        int[] bucketElementCounts = new int[10];

        //需要进行 maxLength 轮排序处理，第一次是个位，第二次是十位，第三次是百位...
        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            //针对每个元素的对应位进行排序处理
            for (int j = 0; j < arr.length; j++) {
                //得到每个元素的对应位的值
                int digitOfElement = arr[j] / n % 10;
                //将元素放入到对应的桶中
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;
            }
            //遍历每一个桶，并将桶中的数据，放入到原数组
            int index = 0;
            for (int k = 0; k < bucket.length; k++) {
                //如果第k个桶中有数据，才遍历取数放入到原数组
                if (bucketElementCounts[k] != 0) {
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        arr[index++] = bucket[k][l];
                    }
                }
                //每轮处理后，需要将每个 bucketElementCounts[k] 置为0
                bucketElementCounts[k] = 0;
            }
//            System.out.println("第" + (i + 1) + "轮排序处理后 arr =" + Arrays.toString(arr));
        }
    }
}
