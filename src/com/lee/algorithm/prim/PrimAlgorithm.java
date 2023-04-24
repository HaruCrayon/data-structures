package com.lee.algorithm.prim;

import java.util.Arrays;

/**
 * @author LiJing
 * @version 1.0
 * 普利姆算法
 */
public class PrimAlgorithm {
    public static void main(String[] args) {
        char[] data = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int vertexNum = data.length;
        //邻接矩阵的关系使用二维数组表示，10000这个大数，表示两个点不连通
        int[][] weight = new int[][]{
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000}};

        MGraph graph = new MGraph(vertexNum);
        MinTree minTree = new MinTree();
        //创建图
        minTree.createGraph(graph, vertexNum, data, weight);
        //显示图
        minTree.showGraph(graph);
        //Prim算法求最小生成树
        minTree.prim(graph, 0);
    }
}


class MinTree {
    //创建图
    public void createGraph(MGraph graph, int vertexNum, char[] data, int[][] weight) {
        int i, j;
        for (i = 0; i < vertexNum; i++) {
            graph.data[i] = data[i];
            for (j = 0; j < vertexNum; j++) {
                graph.weight[i][j] = weight[i][j];
            }
        }
    }

    //显示图
    public void showGraph(MGraph graph) {
        for (int[] row : graph.weight) {
            System.out.println(Arrays.toString(row));
        }
    }


    /**
     * Prim算法求最小生成树
     *
     * @param graph 图
     * @param v     出发顶点
     */
    public void prim(MGraph graph, int v) {
        //visited 记录顶点是否被访问过，默认0表示未被访问过
        int[] visited = new int[graph.vertexNum];
        //从顶点v出发，标记为已访问
        visited[v] = 1;
        //h1和h2 记录两个顶点的下标
        int h1 = -1;
        int h2 = -1;
        //minWeight 保存已经访问过的结点和未访问过的结点间的最小权值
        int minWeight = 10000;

        // vertexNum 个顶点，一定有(vertexNum-1)条边
        for (int k = 1; k < graph.vertexNum; k++) {
            //遍历邻接矩阵
            for (int i = 0; i < graph.vertexNum; i++) {
                for (int j = 0; j < graph.vertexNum; j++) {
                    if (visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWeight) {
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            //找到一条边 权值是最小
            System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + "> 权值为：" + minWeight);
            //将 h2 标记为已访问
            visited[h2] = 1;
            //minWeight 重置为大数
            minWeight = 10000;
        }
    }
}

class MGraph {
    int vertexNum;//图的节点个数
    char[] data;//存放节点数据
    int[][] weight;//邻接矩阵

    public MGraph(int vertexNum) {
        this.vertexNum = vertexNum;
        data = new char[vertexNum];
        weight = new int[vertexNum][vertexNum];
    }
}
