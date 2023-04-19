package com.lee.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author LiJing
 * @version 1.0
 * 图
 */
public class GraphDemo {
    public static void main(String[] args) {
        int n = 8;//顶点的个数
//        String[] vertexArr = {"A", "B", "C", "D", "E"};
        String[] vertexArr = {"1", "2", "3", "4", "5", "6", "7", "8"};
        //创建图对象
        Graph graph = new Graph(n);
        //添加顶点
        for (String vertex : vertexArr) {
            graph.insertVertex(vertex);
        }
        //添加边
        //A-B A-C B-C B-D B-E
//        graph.insertEdge(0, 1, 1);//A-B
//        graph.insertEdge(0, 2, 1);//A-C
//        graph.insertEdge(1, 2, 1);//B-C
//        graph.insertEdge(1, 3, 1);//B-D
//        graph.insertEdge(1, 4, 1);//B-E

        //更新边的关系
        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);
        graph.insertEdge(3, 7, 1);
        graph.insertEdge(4, 7, 1);
        graph.insertEdge(2, 5, 1);
        graph.insertEdge(2, 6, 1);
        graph.insertEdge(5, 6, 1);

        //显示图对应的邻接矩阵
        graph.showGraph();
        //图的深度优先遍历
        System.out.println("深度优先遍历");//1->2->4->8->5->3->6->7
        graph.dfs();
        System.out.println("广度优先遍历");//1->2->3->4->5->6->7->8
        graph.bfs();

    }
}


class Graph {
    private ArrayList<String> vertexList;//存放顶点(结点)
    private int[][] edges;//图对应的邻接矩阵
    private int numOfEdges;//边的数量
    private boolean[] isVisited;//记录某个顶点是否被访问

    //构造器
    public Graph(int n) {//n表示顶点的数量
        //初始化
        vertexList = new ArrayList<String>(n);
        edges = new int[n][n];
        numOfEdges = 0;
    }

    //添加顶点
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    /**
     * 添加边
     *
     * @param v1     第一个顶点对应的下标
     * @param v2     第二个顶点对应的下标
     * @param weight 权值，1 表示能够直接连接，0 表示不能直接连接
     */
    public void insertEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    //显示图对应的邻接矩阵
    public void showGraph() {
        for (int[] row : edges) {
            System.out.println(Arrays.toString(row));
        }
    }

    //返回顶点i(下标)对应的数据 0->"A" 1->"B" 2->"C"
    public String getVertexByIndex(int i) {
        return vertexList.get(i);
    }

    //返回v1和v2的权值
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    //返回顶点的个数
    public int getNumOfVertex() {
        return vertexList.size();
    }

    //返回边的数量
    public int getNumOfEdges() {
        return numOfEdges;
    }

    /**
     * 得到第一个邻接结点的下标
     *
     * @param v 当前结点的下标
     * @return 返回当前结点的第一个邻接结点的下标，不存在则返回-1
     */
    public int getFirstNeighbor(int v) {
        for (int j = 0; j < vertexList.size(); j++) {
            if (edges[v][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 得到下一个邻接结点的下标
     *
     * @param v1 当前结点的下标
     * @param v2 前一个邻接结点的下标
     * @return 返回下一个邻接结点的下标
     */
    public int getNextNeighbor(int v1, int v2) {
        for (int j = v2 + 1; j < vertexList.size(); j++) {
            if (edges[v1][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    //深度优先遍历算法
    private void dfs(boolean[] isVisited, int i) {
        //访问结点i，并标记结点i为已访问
        System.out.print(getVertexByIndex(i) + "->");
        isVisited[i] = true;
        //查找结点i的第一个邻接结点w
        int w = getFirstNeighbor(i);

        while (w != -1) {//w存在
            //若w未被访问，对w进行深度优先遍历递归
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }
            //查找结点i的下一个邻接结点
            w = getNextNeighbor(i, w);
        }
    }

    //对dfs 进行一个重载，遍历vertexList 所有的结点，并进行dfs
    public void dfs() {
        isVisited = new boolean[vertexList.size()];
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                dfs(isVisited, i);
            }
        }
        System.out.println();
    }

    //广度优先遍历算法
    private void bfs(boolean[] isVisited, int i) {
        LinkedList queue = new LinkedList();//队列，记录结点访问的顺序
        int u; // 队列的头结点对应下标u
        int w; // 结点u的邻接结点对应下标w

        //访问初始结点i并标记结点i为已访问
        System.out.print(getVertexByIndex(i) + "->");
        isVisited[i] = true;
        //结点i入队列
        queue.addLast(i);
        //当队列非空时
        while (!queue.isEmpty()) {
            //出队列，取得队头结点u
            u = (Integer) queue.removeFirst();
            //查找结点u的第一个邻接结点w。
            w = getFirstNeighbor(u);

            while (w != -1) {//w存在
                //若结点w尚未被访问，则访问结点w并标记为已访问
                if (!isVisited[w]) {
                    System.out.print(getVertexByIndex(w) + "->");
                    isVisited[w] = true;
                    //结点w入队列
                    queue.addLast(w);
                }
                //查找结点u的继w邻接结点后的下一个邻接结点w
                w = getNextNeighbor(u, w);
            }
        }
    }

    //对bfs 进行一个重载，遍历vertexList 所有的结点，并进行bfs
    public void bfs() {
        isVisited = new boolean[vertexList.size()];
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
        System.out.println();
    }
}