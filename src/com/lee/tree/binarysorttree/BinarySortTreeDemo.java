package com.lee.tree.binarysorttree;

/**
 * @author LiJing
 * @version 1.0
 * 二叉排序树
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree binarySortTree = new BinarySortTree();

        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }

        System.out.println("中序遍历二叉排序树：");
        binarySortTree.infixOrder();

//        binarySortTree.delNode(1);
//        binarySortTree.delNode(9);
//        binarySortTree.delNode(3);
        binarySortTree.delNode(7);
        System.out.println("删除后：");
        binarySortTree.infixOrder();
        System.out.println("root= " + binarySortTree.getRoot());
    }
}


class BinarySortTree {
    private Node root;

    public Node getRoot() {
        return root;
    }

    //添加节点
    public void add(Node node) {
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    //中序遍历
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("二叉排序树为空，不能遍历~~");
        }
    }

    //查找要删除的结点
    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    //查找要删除的结点的父结点
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }
    }

    /**
     * 功能：
     * 1. 返回 以 node 为根结点的二叉排序树的最小结点的值
     * 2. 删除 以 node 为根结点的二叉排序树的最小结点
     *
     * @param node 二叉排序树的根结点
     * @return 以 node 为根结点的二叉排序树的最小结点的值
     */
    public int delTreeMin(Node node) {
        Node target = node;
        //循环的查找左子节点，就会找到最小值
        while (target.left != null) {
            target = target.left;
        }
        //这时target就指向了最小结点
        //删除最小结点
        delNode(target.value);
        return target.value;
    }

    //删除结点
    public void delNode(int value) {
        if (root == null) {
            return;
        }
        //查找要删除的结点 targetNode
        Node targetNode = search(value);
        //没有找到要删除的结点
        if (targetNode == null) {
            return;
        }
        //如果发现该树只有一个结点，则要删除的结点就是根结点
        if (root.left == null && root.right == null) {
            root = null;
            return;
        }

        //查找要删除的结点的父结点 parent
        Node parent = searchParent(value);

        if (targetNode.left == null && targetNode.right == null) {//targetNode是叶子节点
            //判断 targetNode 是 parent的左子结点 还是右子结点
            if (parent.left != null && parent.left.value == value) {//是左子结点
                parent.left = null;
            } else if (parent.right != null && parent.right.value == value) {//是右子结点
                parent.right = null;
            }
        } else if (targetNode.left != null && targetNode.right != null) {//targetNode有两颗子树
            //从targetNode的右子树找到最小的结点。也可以从左子树找到最大的节点
            //用一个临时变量，将最小结点的值保存
            //删除该最小结点
            int minVal = delTreeMin(targetNode.right);
            targetNode.value = minVal;

        } else {//targetNode只有一颗子树
            if (targetNode.left != null) {//如果targetNode 有左子结点
                if (parent != null) {
                    if (parent.left != null && parent.left.value == value) {//如果 targetNode 是 parent 的左子结点
                        parent.left = targetNode.left;
                    } else if (parent.right != null && parent.right.value == value) {
                        parent.right = targetNode.left;
                    }
                } else {
                    root = targetNode.left;
                }
            } else {//如果targetNode 有右子结点
                if (parent != null) {
                    if (parent.left != null && parent.left.value == value) {
                        parent.left = targetNode.right;
                    } else if (parent.right != null && parent.right.value == value) {
                        parent.right = targetNode.right;
                    }
                } else {
                    root = targetNode.right;
                }
            }
        }
    }
}


class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    //添加结点
    public void add(Node node) {
        if (node == null) {
            return;
        }
        if (node.value < this.value) {
            if (this.left == null) {
                this.left = node;
            } else {
                this.left.add(node);
            }
        } else {
            if (this.right == null) {
                this.right = node;
            } else {
                this.right.add(node);
            }
        }
    }

    //中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    /**
     * 查找要删除的结点
     *
     * @param value 要删除的结点的值
     * @return 如果找到返回该结点，否则返回null
     */
    public Node search(int value) {
        if (value == this.value) {
            return this;
        } else if (value < this.value) {
            if (this.left == null) {
                return null;
            }
            return this.left.search(value);
        } else {
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }
    }

    /**
     * 查找要删除的结点的父结点
     *
     * @param value 要删除的结点的值
     * @return 返回要删除的结点的父结点，如果没有就返回null
     */
    public Node searchParent(int value) {
        if ((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)) {
            return this;
        } else {
            if (value < this.value && this.left != null) {
                return this.left.searchParent(value);
            } else if (value >= this.value && this.right != null) {
                return this.right.searchParent(value);
            } else {
                return null;
            }
        }

    }
}