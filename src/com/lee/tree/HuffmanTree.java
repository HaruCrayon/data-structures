package com.lee.tree;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author LiJing
 * @version 1.0
 * 赫夫曼树
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        Node root = createHuffmanTree(arr);
        preOrder(root);
    }

    //前序遍历
    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("是空树，不能遍历~~");
        }
    }

    /**
     * 功能：创建赫夫曼树
     *
     * @param arr 需要创建成赫夫曼树的数组
     * @return 创建好的赫夫曼树的root节点
     */
    public static Node createHuffmanTree(int[] arr) {
        //为了操作方便：遍历arr数组，将arr的每个元素构成一个Node，将Node放入到ArrayList中
        ArrayList<Node> nodes = new ArrayList<>();
        for (int value : arr) {
            nodes.add(new Node(value));
        }

        while (nodes.size() > 1) {
            //从小到大排序，每个节点可以看成是一颗最简单的二叉树
            Collections.sort(nodes);
            //取出根节点权值最小的两颗二叉树
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            //组成一颗新的二叉树，该新的二叉树的根节点的权值是前面两颗二叉树根节点权值的和
            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;
            //从ArrayList删除处理过的二叉树
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //将parent加入到ArrayList
            nodes.add(parent);
        }
        //返回赫夫曼树的root节点
        return nodes.get(0);
    }
}


//为了让Node对象支持排序，让Node实现Comparable接口
class Node implements Comparable<Node> {
    public int value;//结点权值
    public Node left;
    public Node right;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(Node o) {
        //表示从小到大排序
        return this.value - o.value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    //前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }
}