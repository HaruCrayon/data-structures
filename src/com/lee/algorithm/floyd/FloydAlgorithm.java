package com.lee.algorithm.floyd;

import java.util.Arrays;

/**
 * @author LiJing
 * @version 1.0
 * 弗洛伊德算法
 */
public class FloydAlgorithm {
    public static void main(String[] args) {
        final int N = 65535;
        //顶点
        char[] vertexArr = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        //邻接矩阵
        int[][] matrix = {
                {0, 5, 7, N, N, N, 2},
                {5, 0, N, 9, N, N, 3},
                {7, N, 0, N, 8, N, N},
                {N, 9, N, 0, N, 4, N},
                {N, N, 8, N, 0, 5, 4},
                {N, N, N, 4, 5, 0, 6},
                {2, 3, N, N, 4, 6, 0}
        };

        Graph graph = new Graph(vertexArr, matrix);
        graph.floyd();
        graph.show();
    }
}


//图
class Graph {
    private char[] vertexArr;//顶点数组
    private int[][] dis;//每一个顶点到其他顶点的最短距离，会动态更新。最后的结果，也是保存在该数组
    private int[][] mid;//保存中间顶点

    //构造器
    public Graph(char[] vertexArr, int[][] matrix) {
        this.vertexArr = vertexArr;
        this.dis = matrix;
        this.mid = new int[vertexArr.length][vertexArr.length];
        for (int i = 0; i < vertexArr.length; i++) {
            Arrays.fill(mid[i], i);
        }
    }

    //显示mid数组和dis数组
    public void show() {
        for (int i = 0; i < vertexArr.length; i++) {
            for (int j = 0; j < vertexArr.length; j++) {
                System.out.print(vertexArr[mid[i][j]] + " ");
            }
            System.out.println();
            for (int j = 0; j < vertexArr.length; j++) {
                System.out.print("<" + vertexArr[i] + "," + vertexArr[j] + "> = " + dis[i][j] + "  ");
            }
            System.out.println();
            System.out.println();
        }
    }

    //Floyd算法实现
    public void floyd() {
        int len = 0;
        //k 中间顶点
        for (int k = 0; k < vertexArr.length; k++) {
            //从 i 顶点出发
            for (int i = 0; i < vertexArr.length; i++) {
                //到达 j 顶点
                for (int j = 0; j < vertexArr.length; j++) {
                    len = dis[i][k] + dis[k][j];
                    if (len < dis[i][j]) {
                        dis[i][j] = len;//更新最短距离
                        mid[i][j] = k;//更新中间顶点
                    }
                }
            }
        }
    }
}