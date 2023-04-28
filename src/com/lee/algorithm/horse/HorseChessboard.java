package com.lee.algorithm.horse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author LiJing
 * @version 1.0
 * 马踏棋盘问题（骑士周游问题）
 */
public class HorseChessboard {
    public static void main(String[] args) {
        System.out.println("骑士周游算法，开始运行~~");

        Chessboard chessboard = new Chessboard(8, 8);
        int row = 0;//马儿初始位置的行，从0开始编号
        int column = 0;//马儿初始位置的列，从0开始编号

        long start = System.currentTimeMillis();
        chessboard.traversalChessboard(row, column, 1);
        long end = System.currentTimeMillis();
        System.out.println("共耗时: " + (end - start) + " 毫秒");

        chessboard.showChessboard();
    }
}


class Chessboard {
    private int X;//棋盘的列数
    private int Y;//棋盘的行数
    private int[][] chessboard;//棋盘，记录棋盘的各个位置是第几步
    private boolean[] visited;//标记棋盘的各个位置是否被访问过
    private boolean finished;//标记是否棋盘的所有位置都被访问，如果为true，表示成功

    //构造器
    public Chessboard(int x, int y) {
        X = x;
        Y = y;
        chessboard = new int[y][x];
        visited = new boolean[x * y];
    }

    //显示棋盘
    public void showChessboard() {
        for (int[] row : chessboard) {
            for (int step : row) {
                System.out.print(step + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 根据当前位置(Point对象)，计算马儿下一步可以走哪些位置(Point)，并放入到一个集合中(ArrayList), 最多有8个位置
     *
     * @param curPoint 当前位置
     * @return 下一步可以走哪些位置
     */
    public ArrayList<Point> next(Point curPoint) {
        ArrayList<Point> ps = new ArrayList<>();
        Point p = new Point();
        //表示马儿可以走5这个位置
        if ((p.x = curPoint.x - 2) >= 0 && (p.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p));
        }
        //表示马儿可以走6这个位置
        if ((p.x = curPoint.x - 1) >= 0 && (p.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p));
        }
        //表示马儿可以走7这个位置
        if ((p.x = curPoint.x + 1) < X && (p.y = curPoint.y - 2) >= 0) {
            ps.add(new Point(p));
        }
        //表示马儿可以走0这个位置
        if ((p.x = curPoint.x + 2) < X && (p.y = curPoint.y - 1) >= 0) {
            ps.add(new Point(p));
        }
        //表示马儿可以走1这个位置
        if ((p.x = curPoint.x + 2) < X && (p.y = curPoint.y + 1) < Y) {
            ps.add(new Point(p));
        }
        //表示马儿可以走2这个位置
        if ((p.x = curPoint.x + 1) < X && (p.y = curPoint.y + 2) < Y) {
            ps.add(new Point(p));
        }
        //表示马儿可以走3这个位置
        if ((p.x = curPoint.x - 1) >= 0 && (p.y = curPoint.y + 2) < Y) {
            ps.add(new Point(p));
        }
        //表示马儿可以走4这个位置
        if ((p.x = curPoint.x - 2) >= 0 && (p.y = curPoint.y + 1) < Y) {
            ps.add(new Point(p));
        }

        return ps;
    }

    /**
     * 马踏棋盘问题的算法实现
     *
     * @param row    马儿当前位置的行 从0开始
     * @param column 马儿当前位置的列 从0开始
     * @param step   是第几步，初始位置就是第1步
     */
    public void traversalChessboard(int row, int column, int step) {
        //记录当前位置是第step步
        chessboard[row][column] = step;
        //标记当前位置已经访问
        visited[row * X + column] = true;
        //获取当前位置下一步可以走的位置的集合
        ArrayList<Point> ps = next(new Point(column, row));
        //根据ps中所有的Point的下一步可以走的位置的数量，进行非递减排序。目的是减少回溯的次数
        sort(ps);

        while (!ps.isEmpty()) {
            Point p = ps.remove(0);
            //判断该位置是否已经访问过
            if (!visited[p.y * X + p.x]) {
                traversalChessboard(p.y, p.x, step + 1);
            }
        }

        //判断马儿是否完成了任务，使用 step 和应该走的步数比较
        //如果没有达到数量，则表示没有完成任务
        //说明: step < X * Y 成立的情况有两种
        //1. 棋盘到当前位置，仍然没有走完
        //2. 棋盘处于一个回溯过程
        if (step < X * Y && !finished) {
            chessboard[row][column] = 0;
            visited[row * X + column] = false;
        } else {
            finished = true;
        }
    }

    //根据 ps 中所有的Point 的下一步可以走的位置的数量，进行非递减排序
    public void sort(ArrayList<Point> ps) {
        ps.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                //o1 的下一步可以走的位置的数量
                int count1 = next(o1).size();
                //o2 的下一步可以走的位置的数量
                int count2 = next(o2).size();
                if (count1 < count2) {
                    return -1;
                } else if (count1 == count2) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }
}