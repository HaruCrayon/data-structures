package com.lee.algorithm.kruskal;

import java.util.Arrays;

/**
 * @author LiJing
 * @version 1.0
 * 克鲁斯卡尔算法
 */
public class KruskalAlgorithm {
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        //顶点
        char[] vertexArr = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        //邻接矩阵
        int[][] matrix = {
                     /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ {0, 12, INF, INF, INF, 16, 14},
                /*B*/ {12, 0, 10, INF, INF, 7, INF},
                /*C*/ {INF, 10, 0, 3, 5, 6, INF},
                /*D*/ {INF, INF, 3, 0, 4, INF, INF},
                /*E*/ {INF, INF, 5, 4, 0, 2, 8},
                /*F*/ {16, 7, 6, INF, 2, 0, 9},
                /*G*/ {14, INF, INF, INF, 8, 9, 0}};

        Graph graph = new Graph(vertexArr, matrix);
        graph.showMatrix();

        graph.kruskal();
    }
}


//图
class Graph {
    private char[] vertexArr;//顶点
    private int[][] matrix;//邻接矩阵
    private int edgeNum;//边的数量
    private static final int INF = Integer.MAX_VALUE;//使用INF 表示两个顶点不能连通

    //构造器
    public Graph(char[] vertexArr, int[][] matrix) {
//        this.vertexArr = vertexArr;
//        this.matrix = matrix;
        int vlen = vertexArr.length;
        //采用复制的方式初始化顶点和邻接矩阵
        this.vertexArr = new char[vlen];
        this.matrix = new int[vlen][vlen];
        for (int i = 0; i < vlen; i++) {
            this.vertexArr[i] = vertexArr[i];
            for (int j = 0; j < vlen; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
        //统计边的数量
        for (int i = 0; i < vlen; i++) {
            for (int j = i + 1; j < vlen; j++) {
                if (this.matrix[i][j] != INF) {
                    this.edgeNum++;
                }
            }
        }
    }

    //显示邻接矩阵
    public void showMatrix() {
        System.out.println("图的邻接矩阵是：");
        for (int i = 0; i < vertexArr.length; i++) {
            for (int j = 0; j < vertexArr.length; j++) {
                System.out.printf("%12d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * @param ch 顶点的值，比如'A','B'
     * @return 返回ch顶点对应的下标，如果找不到，返回-1
     */
    public int getPosition(char ch) {
        for (int i = 0; i < vertexArr.length; i++) {
            if (vertexArr[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    //获取图的所有边
    public EData[] getEdges() {
        int index = 0;
        EData[] edges = new EData[edgeNum];
        for (int i = 0; i < vertexArr.length; i++) {
            for (int j = i + 1; j < vertexArr.length; j++) {
                if (matrix[i][j] != INF) {
                    edges[index++] = new EData(vertexArr[i], vertexArr[j], matrix[i][j]);
                }
            }
        }
        return edges;
    }

    //对图的所有边按照权值从小到大进行排序（冒泡排序）
    public void sortEdges(EData[] edges) {
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - 1 - i; j++) {
                if (edges[j].weight > edges[j + 1].weight) {
                    EData temp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 功能: 获取下标为i的顶点的终点，用于后面判断两个顶点的终点是否相同
     *
     * @param ends 记录了各个顶点对应的终点是哪个，ends数组是在遍历过程中逐步形成
     * @param i    下标为i的顶点
     * @return 返回下标为i的这个顶点对应的终点的下标
     */
    private int getEnd(int[] ends, int i) {
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i;
    }

    //Kruskal算法求最小生成树
    public void kruskal() {
        //记录顶点在"已有最小生成树"中的终点
        int[] ends = new int[vertexArr.length];
        //创建结果数组，保存最后的最小生成树
        EData[] res = new EData[vertexArr.length - 1];
        //结果数组的索引
        int index = 0;
        //获取图的所有边
        EData[] edges = getEdges();
        //对图的所有边按照权值从小到大进行排序
        sortEdges(edges);
        System.out.println("图的所有边：" + Arrays.toString(edges) + " 共" + edges.length); //12
        //遍历edges数组，将边添加到最小生成树中时，判断准备加入的边是否形成了回路，如果没有形成回路，就加入res，否则不能加入
        for (int i = 0; i < edges.length; i++) {
            //获取到第i条边的第一个顶点
            int p1 = getPosition(edges[i].start);
            //获取到第i条边的第二个顶点
            int p2 = getPosition(edges[i].end);
            //获取p1这个顶点在"已有最小生成树"中的终点
            int m = getEnd(ends, p1);
            //获取p2这个顶点在"已有最小生成树"中的终点
            int n = getEnd(ends, p2);
            //判断是否构成回路
            if (m != n) {//没有构成回路
                ends[m] = n;
                res[index++] = edges[i];
            }
        }
        System.out.println("得到的最小生成树：" + Arrays.toString(res));//<E,F> <C,D> <D,E> <B,F> <E,G> <A,B>
    }
}


//边
class EData {
    char start;//边的一个顶点
    char end;//边的另一个顶点
    int weight;//边的权值

    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "EData{ <" + start + "," + end + "> = " + weight + " }";
    }
}