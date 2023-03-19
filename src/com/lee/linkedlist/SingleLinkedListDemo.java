package com.lee.linkedlist;

import java.util.Stack;

/**
 * @author LiJing
 * @version 1.0
 * 单向链表
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        //创建单向链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        SingleLinkedList singleLinkedList2 = new SingleLinkedList();

        //创建节点
        HeroNode hero1 = new HeroNode(1, "汤姆", "tom");
        HeroNode hero2 = new HeroNode(2, "杰克", "jack");
        HeroNode hero3 = new HeroNode(3, "哈利", "harry");
        HeroNode hero4 = new HeroNode(4, "史密斯", "smith");

        HeroNode hero5 = new HeroNode(5, "哈哈", "haha");
        HeroNode hero6 = new HeroNode(6, "张三", "zs");
        HeroNode hero7 = new HeroNode(7, "小明", "ming");

        //添加节点
        singleLinkedList.add(hero1);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero4);
        singleLinkedList.add(hero3);

        singleLinkedList2.add(hero5);
        singleLinkedList2.add(hero6);
        singleLinkedList2.add(hero7);

        //添加节点，按照编号的顺序
//        singleLinkedList.addByOrder(hero1);
//        singleLinkedList.addByOrder(hero3);
//        singleLinkedList.addByOrder(hero4);
//        singleLinkedList.addByOrder(hero2);

        //显示单链表
        System.out.println("原来的单链表~~");
        singleLinkedList.list();
        System.out.println("原来的单链表2~~");
        singleLinkedList2.list();

        //合并两个有序的单链表，合并之后的链表依然有序
        mergeList(singleLinkedList.getHead(), singleLinkedList2.getHead());
        System.out.println("合并后的单链表~~");
        singleLinkedList.list();

        //将单链表反转
//        reverseList(singleLinkedList.getHead());
//        System.out.println("反转后的链表~~");
//        singleLinkedList.list();

        //逆序打印单链表
//        System.out.println("逆序打印单链表~~");
//        reversePrint(singleLinkedList.getHead());

/*
        //修改节点
        HeroNode newHero = new HeroNode(2, "小杰", "jack~~");
        singleLinkedList.update(newHero);
        System.out.println("修改节点后的链表~~");
        singleLinkedList.list();

        //删除节点
        singleLinkedList.del(3);
        singleLinkedList.del(1);
        System.out.println("删除节点后的链表~~");
        singleLinkedList.list();

        //求单链表中有效节点的个数
        System.out.println("单链表中有效节点的个数=" + getLength(singleLinkedList.getHead()));

        //查找单链表中的倒数第k个结点
        HeroNode res = findLastIndexNode(singleLinkedList.getHead(), 1);
        System.out.println("res= " + res);

 */
    }

    /**
     * 合并两个有序的单链表，合并之后的链表依然有序
     *
     * @param head1 链表1的头节点
     * @param head2 链表2的头节点
     */
    public static void mergeList(HeroNode head1, HeroNode head2) {
        if (head1.next == null || head2.next == null) {
            return;
        }
        HeroNode temp = head2.next;
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        temp.next = head1.next;
        head1.next = head2.next;
    }

    /**
     * 从尾到头打印单链表（利用栈的先进后出的特点，实现逆序打印的效果）
     *
     * @param head 链表的头节点
     */
    public static void reversePrint(HeroNode head) {
        if (head.next == null) {
            return;
        }
        Stack<HeroNode> stack = new Stack<>();
        HeroNode cur = head.next;
        //将链表的所有节点压入栈
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        //将栈中的节点进行打印, pop出栈
        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }
    }

    /**
     * 单链表的反转
     *
     * @param head 链表的头节点
     */
    public static void reverseList(HeroNode head) {
        if (head.next == null || head.next.next == null) {
            return;
        }
        HeroNode cur = head.next;
        HeroNode next = null;
        HeroNode reverseHead = new HeroNode(0, "", "");
        while (cur != null) {
            next = cur.next;//先暂时保存当前节点的下一个节点，因为后面需要使用
            cur.next = reverseHead.next;
            reverseHead.next = cur;
            cur = next;
        }
        head.next = reverseHead.next;
    }

    /**
     * @param head  链表的头节点
     * @param index 倒数第index个节点
     * @return 返回倒数第index个节点
     */
    public static HeroNode findLastIndexNode(HeroNode head, int index) {
        if (head.next == null) {
            return null;
        }
        int size = getLength(head);
        if (index <= 0 || index > size) {
            return null;
        }
        HeroNode cur = head.next;
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    /**
     * @param head 链表的头节点
     * @return 返回有效节点的个数
     */
    public static int getLength(HeroNode head) {
        if (head.next == null) {
            return 0;
        }
        HeroNode cur = head.next;
        int length = 0;
        while (cur != null) {
            length++;
            cur = cur.next;
        }
        return length;
    }
}


class SingleLinkedList {
    private HeroNode head = new HeroNode(0, "", "");

    public HeroNode getHead() {
        return head;
    }

    public void add(HeroNode hero) {
        HeroNode temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        temp.next = hero;
    }

    public void addByOrder(HeroNode hero) {
        HeroNode temp = head;
        boolean flag = false;//flag标识添加的编号是否存在，默认为false
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no > hero.no) {
                break;
            } else if (temp.next.no == hero.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            System.out.printf("要加入的英雄编号 %d 已存在~~\n", hero.no);
        } else {
            hero.next = temp.next;
            temp.next = hero;
        }
    }

    public void update(HeroNode newHero) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.no == newHero.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.name = newHero.name;
            temp.nickname = newHero.nickname;
        } else {
            System.out.printf("要修改的英雄编号 %d 不存在\n", newHero.no);
        }
    }

    public void del(int no) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode temp = head;
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                break;
            }
            if (temp.next.no == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.next = temp.next.next;
        } else {
            System.out.printf("要删除的英雄编号 %d 不存在\n", no);
        }
    }

    public void list() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode temp = head.next;
        while (true) {
            if (temp == null) {
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }
    }
}


class HeroNode {
    public int no;
    public String name;
    public String nickname;
    public HeroNode next;

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
