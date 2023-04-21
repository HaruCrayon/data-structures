package com.lee.algorithm.divideandconquer;

/**
 * @author LiJing
 * @version 1.0
 * 分治算法 - 汉诺塔问题
 */
public class HanoiTower {
    public static void main(String[] args) {
        hanoiTower(5, "A", "B", "C");
    }

    /**
     * 有 a、b、c 三个塔，把所有盘从 a 移动到 c
     *
     * @param num 盘子总数
     * @param a
     * @param b
     * @param c
     */
    public static void hanoiTower(int num, String a, String b, String c) {
        if (num == 1) {
            System.out.println("第 1 个盘从 " + a + " -> " + c);
        } else {
            //如果 num >= 2，可以看作是两个盘：1.最下边的一个盘  2.上面的所有盘
            //1. 先把上面的所有盘 a->b，移动过程会使用到 c
            hanoiTower(num - 1, a, c, b);
            //2. 把最下边的一个盘 a->c
            System.out.println("第 " + num + " 个盘从 " + a + " -> " + c);
            //3. 把b塔的所有盘 b->c，移动过程会使用到 a
            hanoiTower(num - 1, b, a, c);
        }
    }
}
