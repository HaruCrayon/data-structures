package com.lee.recursion;

/**
 * @author LiJing
 * @version 1.0
 * 递归调用机制
 */
public class RecursionTest {
    public static void main(String[] args) {
        //通过打印问题，回顾递归调用机制
//        test1(4);
//        test2(4);

        int res = factorial(3);
        System.out.println("res=" + res);
    }

    //打印问题
    public static void test1(int n) {
        if (n > 2) {
            test1(n - 1);
        }
        System.out.println("n=" + n);
    }

    //打印问题
    public static void test2(int n) {
        if (n > 2) {
            test2(n - 1);
        } else {
            System.out.println("n=" + n);
        }
    }

    //阶乘问题
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return factorial(n - 1) * n;
        }
    }
}
