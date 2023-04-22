package com.lee.algorithm.kmp;

/**
 * @author LiJing
 * @version 1.0
 * 字符串匹配问题 - 暴力匹配算法
 */
public class BruteForce {
    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        int index = bruteForce(str1, str2);
        System.out.println("index=" + index);

    }

    public static int bruteForce(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int s1Len = s1.length;
        int s2Len = s2.length;
        int i = 0;// i索引指向s1
        int j = 0;// j索引指向s2

        while (i < s1Len && j < s2Len) {
            if (s1[i] == s2[j]) {
                i++;
                j++;
            } else {
                i = i - (j - 1);
                j = 0;
            }
        }
        //判断是否匹配成功
        if (j == s2Len) {
            return i - j;
        } else {
            return -1;
        }
    }
}
