package com.lee.algorithm.dijkstra;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author LiJing
 * @version 1.0
 * 迪杰斯特拉算法
 */
public class DijkstraAlgorithm {
    public static void main(String[] args) {
        //顶点
        char[] vertexArr = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        final int N = 65535;//表示两个顶点不能连通
        //邻接矩阵
        int[][] matrix = {
                {N, 5, 7, N, N, N, 2},
                {5, N, N, 9, N, N, 3},
                {7, N, N, N, 8, N, N},
                {N, 9, N, N, N, 4, N},
                {N, N, 8, N, N, 5, 4},
                {N, N, N, 4, 5, N, 6},
                {2, 3, N, N, 4, 6, N}};

        Graph graph = new Graph(vertexArr, matrix);
        graph.showGraph();

//        graph.dijkstra(6);//G
//        graph.showResult();//A(2)B(3)C(9)D(10)E(4)F(6)G(0)
        graph.dijkstra(2);//C
        graph.showResult();//A(7) B(12) C(0) D(17) E(8) F(13) G(9)
    }
}


//图
class Graph {
    private char[] vertexArr;//顶点
    private int[][] matrix;//邻接矩阵
    private VisitedVertex vv;//已访问顶点集合

    //构造器
    public Graph(char[] vertexArr, int[][] matrix) {
        this.vertexArr = vertexArr;
        this.matrix = matrix;
    }

    //显示图
    public void showGraph() {
        System.out.println("图的邻接矩阵是：");
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    //得到v顶点第一个邻接顶点的下标
    public int getFirstNeighbor(int v) {
        for (int j = 0; j < vertexArr.length; j++) {
            if (matrix[v][j] < 65535) {
                return j;
            }
        }
        return -1;
    }

    //得到v1顶点继v2邻接顶点之后下一个邻接顶点的下标
    public int getNextNeighbor(int v1, int v2) {
        for (int j = v2 + 1; j < vertexArr.length; j++) {
            if (matrix[v1][j] < 65535) {
                return j;
            }
        }
        return -1;
    }

    //访问顶点index，更新index顶点的邻接顶点的dis和pre
    public void update(int index) {
        int len;
        for (int j = 0; j < matrix[index].length; j++) {
            //len = 出发顶点到index顶点的距离 + index顶点到j顶点的距离
            len = vv.getDis(index) + matrix[index][j];
            if (len < vv.getDis(j)) {
                //更新j顶点的前驱顶点为index顶点
                vv.updatePre(j, index);
                //更新出发顶点到j顶点的距离为len
                vv.updateDis(j, len);
            }
        }
    }

    /**
     * Dijkstra算法实现
     *
     * @param start 出发顶点对应的下标
     */
    public void dijkstra(int start) {
        vv = new VisitedVertex(vertexArr.length, start);
        //广度优先遍历思想
        LinkedList queue = new LinkedList();//队列，记录结点访问的顺序
        int u; // 队列的头结点对应下标u
        int w; // 结点u的邻接结点对应下标w

        //访问初始结点start并标记结点start为已访问
        update(start);
        vv.already_arr[start] = 1;
        //结点start入队列
        queue.addLast(start);
        //当队列非空时
        while (!queue.isEmpty()) {
            //出队列，取得队头结点u
            u = (Integer) queue.removeFirst();
            //查找结点u的第一个邻接结点w。
            w = getFirstNeighbor(u);

            while (w != -1) {//w存在
                //若结点w尚未被访问，则访问结点w并标记为已访问
                if (vv.already_arr[w] == 0) {
                    update(w);
                    vv.already_arr[w] = 1;
                    //结点w入队列
                    queue.addLast(w);
                }
                //查找结点u的继w邻接结点后的下一个邻接结点w
                w = getNextNeighbor(u, w);
            }
        }
    }

    //显示最后的结果：出发顶点到各个顶点的最短距离
    public void showResult() {
        vv.showArr();
    }
}


//已访问顶点集合
class VisitedVertex {
    //记录各个顶点是否访问过 1表示访问过，0未访问，会动态更新
    public int[] already_arr;
    //记录各个顶点的前驱顶点下标，会动态更新
    public int[] pre_visited;
    //记录出发顶点到各个顶点的距离，比如G为出发顶点，就会记录G到其它顶点的距离，会动态更新，求的最短距离就会存放到dis
    public int[] dis;

    /**
     * 构造器
     *
     * @param vertexNum 顶点个数
     * @param start     出发顶点对应的下标
     */
    public VisitedVertex(int vertexNum, int start) {
        this.already_arr = new int[vertexNum];
        this.pre_visited = new int[vertexNum];
        this.dis = new int[vertexNum];
        //初始化dis数组
        Arrays.fill(dis, 65535);
        this.dis[start] = 0;
    }

    //更新出发顶点到index顶点的距离
    public void updateDis(int index, int len) {
        dis[index] = len;
    }

    //更新index顶点的前驱顶点为pre顶点
    public void updatePre(int index, int pre) {
        pre_visited[index] = pre;
    }

    //返回出发顶点到index顶点的距离
    public int getDis(int index) {
        return dis[index];
    }

    //显示最后的结果，即 将三个数组的情况输出
    public void showArr() {
        System.out.println("=========== 结果 =============");
        System.out.println("already_arr = " + Arrays.toString(already_arr));
        System.out.println("pre_visited = " + Arrays.toString(pre_visited));
        //出发顶点到各个顶点的最短距离
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int count = 0;
        for (int i : dis) {
            if (i != 65535) {
                System.out.print(vertex[count] + "(" + i + ") ");
            } else {
                System.out.println("N ");
            }
            count++;
        }
        System.out.println();
    }
}