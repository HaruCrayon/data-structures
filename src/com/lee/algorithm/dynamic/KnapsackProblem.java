package com.lee.algorithm.dynamic;

/**
 * @author LiJing
 * @version 1.0
 * 动态规划算法 - 背包问题
 */
public class KnapsackProblem {
    public static void main(String[] args) {
        //这里的问题属于01背包，即每个物品最多放一个
        int[] w = {1, 4, 3};//物品的重量
        int[] val = {1500, 3000, 2000};//物品的价值
        int m = 4;//背包的容量
        int n = w.length;//物品的数量

        //v[i][j] 表示在前i个物品中能够装入容量为j的背包中的最大价值
        int[][] v = new int[n + 1][m + 1];
        //path 记录放入物品的情况
        int[][] path = new int[n + 1][m + 1];

        //填表 第一行和第一列是0
        for (int i = 0; i < v.length; i++) {
            v[i][0] = 0;
        }
        for (int i = 0; i < v[0].length; i++) {
            v[0][i] = 0;
        }
        //每次遍历到的第i个物品，根据w[i]和v[i]来确定是否需要将该物品放入背包中
        for (int i = 1; i < v.length; i++) {
            for (int j = 1; j < v[0].length; j++) {
                //当准备加入的新增的商品的重量大于当前背包的容量时，就直接使用上一个单元格的装入策略
                if (w[i - 1] > j) {
                    v[i][j] = v[i - 1][j];
                } else {
                    //当准备加入的新增的商品的重量小于等于当前背包的容量时
                    //v[i][j] = Math.max(v[i - 1][j], val[i - 1] + v[i - 1][j - w[i - 1]]);
                    if (v[i - 1][j] < val[i - 1] + v[i - 1][j - w[i - 1]]) {
                        v[i][j] = val[i - 1] + v[i - 1][j - w[i - 1]];
                        //表示有新增物品放入背包
                        path[i][j] = 1;
                    } else {
                        v[i][j] = v[i - 1][j];
                    }
                }
            }
        }

        //输出表格
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[0].length; j++) {
                System.out.print(v[i][j] + " ");
            }
            System.out.println();
        }
        //输出最优解
        int i = path.length - 1;
        int j = path[0].length - 1;
        while (i > 0 && j > 0) {//从path的最后开始找
            if (path[i][j] == 1) {
                System.out.printf("第%d个商品放入到背包\n", i);
                j -= w[i - 1]; //w[i-1]
            }
            i--;
        }
    }
}
