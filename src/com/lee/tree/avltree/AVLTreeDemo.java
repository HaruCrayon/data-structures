package com.lee.tree.avltree;

/**
 * @author LiJing
 * @version 1.0
 * 平衡二叉树
 */
public class AVLTreeDemo {
    public static void main(String[] args) {
//        int[] arr = {4, 3, 6, 5, 7, 8};
//        int[] arr = {10, 12, 8, 9, 7, 6};
        int[] arr = {10, 11, 7, 6, 8, 9};
        //创建树
        AVLTree avlTree = new AVLTree();
        //添加结点
        for (int i = 0; i < arr.length; i++) {
            avlTree.add(new Node(arr[i]));
        }
        //中序遍历树
        System.out.println("中序遍历:");
        avlTree.infixOrder();
        //树的高度
        System.out.println("树的高度= " + avlTree.getRoot().height());
        System.out.println("左子树的高度= " + avlTree.getRoot().leftHeight());
        System.out.println("右子树的高度= " + avlTree.getRoot().rightHeight());
        System.out.println("当前根结点= " + avlTree.getRoot());
        System.out.println("当前根结点的左结点= " + avlTree.getRoot().left);
        System.out.println("当前根结点的右结点= " + avlTree.getRoot().right);

    }
}


class AVLTree {
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

        //添加完一个结点后，如果: (右子树的高度-左子树的高度) > 1，左旋转
        //当它(当前根结点)符合左旋转的条件时
        if (rightHeight() - leftHeight() > 1) {
            //如果它的右子树的左子树高度大于它的右子树的右子树高度
            if (right != null && right.leftHeight() > right.rightHeight()) {
                //先对它的右子树进行右旋转
                right.rightRotate();
                //再对它进行左旋转
                leftRotate();
            } else {
                //否则，直接对它进行左旋转
                leftRotate();
            }
            return;
        }

        //添加完一个结点后，如果：(左子树的高度 - 右子树的高度) > 1，右旋转
        //当它(当前根结点)符合右旋转的条件时
        if (leftHeight() - rightHeight() > 1) {
            //如果它的左子树的右子树高度大于它的左子树的左子树高度
            if (left != null && left.rightHeight() > left.leftHeight()) {
                //先对它的左子树进行左旋转
                left.leftRotate();
                //再对它进行右旋转
                rightRotate();
            } else {
                //否则，直接对它进行右旋转
                rightRotate();
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

    //返回 以该结点为根结点的树的高度
    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    //返回 左子树的高度
    public int leftHeight() {
        if (left == null) {
            return 0;
        }
        return left.height();
    }

    //返回 右子树的高度
    public int rightHeight() {
        if (right == null) {
            return 0;
        }
        return right.height();
    }

    //左旋转
    public void leftRotate() {
        //1. 创建一个新的节点，值等于当前根节点的值
        Node newNode = new Node(value);
        //2. 把新节点的左子树设置为当前节点的左子树
        newNode.left = left;
        //3. 把新节点的右子树设置为当前节点的右子树的左子树
        newNode.right = right.left;
        //4. 把当前节点的值换为右子节点的值
        value = right.value;
        //5. 把当前节点的右子树设置成右子树的右子树
        right = right.right;
        //6. 把当前节点的左子树设置为新节点
        left = newNode;
    }

    //右旋转
    public void rightRotate() {
        //1. 创建一个新的节点newNode，值等于当前根节点的值
        Node newNode = new Node(value);
        //2. 把新节点的右子树设置了当前节点的右子树
        newNode.right = right;
        //3. 把新节点的左子树设置为当前节点的左子树的右子树
        newNode.left = left.right;
        //4. 把当前节点的值换为左子节点的值
        value = left.value;
        //5. 把当前节点的左子树设置成左子树的左子树
        left = left.left;
        //6. 把当前节点的右子树设置为新节点
        right = newNode;
    }
}