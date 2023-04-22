package com.lee.algorithm.kmp;

/**
 * @author LiJing
 * @version 1.0
 * KMP算法
 */
public class KMPAlgorithm {
    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";
        int[] next = buildNext(str2);
        int index = kmpSearch(str1, str2, next);
        System.out.println("index= " + index);
    }

    /**
     * @param text    源字符串
     * @param pattern 子串
     * @param next    子串对应的部分匹配表
     * @return 如果是-1就是没有匹配到，否则返回第一个匹配的位置
     */
    public static int kmpSearch(String text, String pattern, int[] next) {
        int i = 0;
        int j = 0;
        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                if (j > 0) {
                    j = next[j - 1];
                } else {
                    i++;
                }
            }
            if (j == pattern.length()) {
                return i - j;
            }
        }
        return -1;
    }

    //得到一个字符串(子串) 的部分匹配值表
    public static int[] buildNext(String str) {
        //创建一个next数组，保存部分匹配值
        int[] next = new int[str.length()];
        next[0] = 0;//如果字符串长度为1 部分匹配值就是0

        int len = 0;
        int i = 1;
        while (i < str.length()) {
            if (str.charAt(i) == str.charAt(len)) {
                len++;
                next[i] = len;
                i++;
            } else {
                if (len > 0) {
                    len = next[len - 1];
                } else {
                    next[i] = len;
                    i++;
                }
            }
        }
        return next;
    }
}
