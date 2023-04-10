package com.lee.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author LiJing
 * @version 1.0
 * 堆排序
 */
public class HeapSort {
    public static void main(String[] args) {
        //要求将数组进行升序排序
//        int[] arr = {4, 6, 8, 5, 9, -1, -34, 0, 20, 13};
//        System.out.println("排序前，数组= " + Arrays.toString(arr));
//        heapSort(arr);
//        System.out.println("堆排序后，数组= " + Arrays.toString(arr));

        //测试一下堆排序的速度，给80000个数据，测试
        int[] arr = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int) (Math.random() * 8000000);//生成一个 [0, 8000000) 数
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        String date1Str = simpleDateFormat.format(date1);
        System.out.println("排序前的时间是=" + date1Str);

        heapSort(arr);

        Date date2 = new Date();
        String date2Str = simpleDateFormat.format(date2);
        System.out.println("堆排序后的时间是=" + date2Str);
    }

    //堆排序
    public static void heapSort(int[] arr) {
        int temp = 0;

        //1、将无序序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆
        //   从最后一个非叶子结点开始（叶结点自然不用调整，最后一个非叶子结点 arr.length/2-1），从左至右，从下至上进行调整
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }

        //2、将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端
        //3、重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序
        for (int j = arr.length - 1; j > 0; j--) {
            //交换
            temp = arr[0];
            arr[0] = arr[j];
            arr[j] = temp;
            //重新调整
            adjustHeap(arr, 0, j);
        }
    }

    /**
     * 功能：将以 i 为父结点的树调整成大顶堆
     *
     * @param arr    待调整的数组
     * @param i      非叶子结点在数组中索引
     * @param length 对多少个元素继续调整，length是在逐渐的减少
     */
    public static void adjustHeap(int arr[], int i, int length) {
        int temp = arr[i];
        //开始调整
        //说明：k = i * 2 + 1  k是i结点的左子结点
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            if (k + 1 < length && arr[k] < arr[k + 1]) {//说明左子结点的值小于右子结点的值
                k++;//将k指向右子结点
            }
            if (arr[k] > temp) {//如果左右子结点中较大的值大于父结点的值
                arr[i] = arr[k];//把较大的值赋给父结点
                i = k;//i指向k，继续循环比较
            } else {
                break;
            }
        }
        //当for循环结束后，就已经将以i为父结点的树的最大值，放在了最顶(局部)
        arr[i] = temp;//将temp值放到调整后的位置
    }
}
