package com.lee.recursion;

/**
 * @author LiJing
 * @version 1.0
 * 递归 - 八皇后问题 (回溯算法)
 */
public class Queen8Test {
    public static void main(String[] args) {
        Queen8 queen8 = new Queen8();
        queen8.check(0);
        System.out.printf("一共有 %d 种解法\n", queen8.getCount());//92种
        System.out.printf("判断冲突的次数：%d 次\n", queen8.getJudgeCount()); // 15720次
    }
}


class Queen8 {
    //定义max, 表示共有多少个皇后
    private int max = 8;
    //定义数组arr, 保存皇后放置位置的结果, 比如 arr = {0 , 4, 7, 5, 2, 6, 1, 3}
    private int[] arr = new int[max];
    private int count = 0;
    private int judgeCount = 0;

    public int getCount() {
        return count;
    }

    public int getJudgeCount() {
        return judgeCount;
    }

    //方法：可以将皇后摆放的位置输出
    private void print() {
        count++;
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    //方法：当放置第n个皇后, 判断该皇后是否和前面已经摆放的皇后冲突
    private boolean judge(int n) {
        judgeCount++;
        for (int i = 0; i < n; i++) {
            if (arr[i] == arr[n] || Math.abs(n - i) == Math.abs(arr[n] - arr[i])) {
                return false;
            }
        }
        return true;
    }

    //方法：放置第n个皇后
    //特别注意：每一次递归时，进入到check中都有 for(int i = 0; i < max; i++)，因此会有回溯
    public void check(int n) {
        if (n == max) {//当n = 8 , 其实8个皇后已经放好
            print();
            return;
        }
        //放入皇后，并判断是否冲突
        for (int i = 0; i < max; i++) {
            arr[n] = i;
            if (judge(n)) {//不冲突
                //接着放第n+1个皇后
                check(n + 1);
            }
            //如果冲突，就继续执行 arr[n] = i; 即：将第n个皇后，放置在本行的后移的一个位置
        }
    }
}