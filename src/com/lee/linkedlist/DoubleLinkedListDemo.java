package com.lee.linkedlist;

/**
 * @author LiJing
 * @version 1.0
 * 双向链表
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        //创建双向链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();

        //创建节点
        HeroNode2 hero1 = new HeroNode2(1, "汤姆", "tom");
        HeroNode2 hero2 = new HeroNode2(2, "杰克", "jack");
        HeroNode2 hero3 = new HeroNode2(3, "哈利", "harry");
        HeroNode2 hero4 = new HeroNode2(4, "史密斯", "smith");

        //添加节点
//        doubleLinkedList.add(hero4);
//        doubleLinkedList.add(hero2);
//        doubleLinkedList.add(hero3);
//        doubleLinkedList.add(hero1);
        //添加节点，按编号顺序
        doubleLinkedList.addByOrder(hero4);
        doubleLinkedList.addByOrder(hero2);
        doubleLinkedList.addByOrder(hero3);
        doubleLinkedList.addByOrder(hero1);

        //显示链表
        System.out.println("原来的双向链表~~");
        doubleLinkedList.list();

        //修改节点
        HeroNode2 newHero = new HeroNode2(3, "小明", "ming");
        doubleLinkedList.update(newHero);
        System.out.println("修改后的双向链表~~");
        doubleLinkedList.list();

        //删除节点
        doubleLinkedList.del(2);
        System.out.println("删除后的双向链表~~");
        doubleLinkedList.list();
    }
}


class DoubleLinkedList {
    private HeroNode2 head = new HeroNode2(0, "", "");

    public HeroNode2 getHead() {
        return head;
    }

    //遍历双向链表，显示
    public void list() {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode2 temp = head.next;
        while (true) {
            if (temp == null) {
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }
    }

    //添加一个节点到双向链表的最后
    public void add(HeroNode2 hero) {
        HeroNode2 temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        temp.next = hero;
        hero.pre = temp;
    }

    //双向链表的第二种添加方式，按照编号顺序
    public void addByOrder(HeroNode2 hero) {
        HeroNode2 temp = head;
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
            hero.pre = temp;
            if (temp.next != null) {
                temp.next.pre = hero;
            }
            temp.next = hero;
        }
    }

    //修改节点
    public void update(HeroNode2 newHero) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode2 temp = head.next;
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

    //删除节点
    public void del(int no) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        HeroNode2 temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }
            if (temp.no == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.pre.next = temp.next;
            if (temp.next != null) {
                temp.next.pre = temp.pre;
            }
        } else {
            System.out.printf("要删除的英雄编号 %d 不存在\n", no);
        }
    }
}


class HeroNode2 {
    public int no;
    public String name;
    public String nickname;
    public HeroNode2 next;
    public HeroNode2 pre;

    public HeroNode2(int no, String name, String nickname) {
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