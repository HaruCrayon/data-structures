package com.lee.hashtable;

import java.util.Scanner;

/**
 * @author LiJing
 * @version 1.0
 * 哈希表
 */
public class HashTableDemo {
    public static void main(String[] args) {
        HashTab hashTab = new HashTab(7);

        String key = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("add:  添加雇员");
            System.out.println("list: 显示所有雇员");
            System.out.println("find: 查找雇员");
            System.out.println("exit: 退出");
            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("请输入id:");
                    int id = scanner.nextInt();
                    System.out.println("请输入name:");
                    String name = scanner.next();
                    Emp emp = new Emp(id, name);
                    hashTab.add(emp);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("请输入id:");
                    int empId = scanner.nextInt();
                    hashTab.find(empId);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                default:
                    break;
            }
        }

    }
}


class HashTab {
    private EmpLinkedList[] arr;
    private int size;

    public HashTab(int size) {
        this.size = size;
        arr = new EmpLinkedList[size];
        for (int i = 0; i < size; i++) {
            arr[i] = new EmpLinkedList();
        }
    }

    public void add(Emp emp) {
        int index = hashFunc(emp.id);
        arr[index].add(emp);
    }

    public void list() {
        for (int i = 0; i < size; i++) {
            arr[i].list(i);
        }
    }

    public void find(int id) {
        int index = hashFunc(id);
        Emp emp = arr[index].findEmpById(id);
        if (emp != null) {
            System.out.println("在第" + (index + 1) + "条链表找到了: " + emp);
        } else {
            System.out.println("没找到");
        }
    }

    //散列函数
    public int hashFunc(int id) {
        return id % size;
    }
}


class EmpLinkedList {
    public Emp head;

    //给当前链表添加一个Emp
    public void add(Emp emp) {
        if (head == null) {
            head = emp;
            return;
        }
        Emp curEmp = head;
        while (true) {
            if (curEmp.next == null) {
                break;
            }
            curEmp = curEmp.next;
        }
        curEmp.next = emp;
    }

    //遍历当前链表
    public void list(int no) {
        if (head == null) {
            System.out.println("第 " + (no + 1) + " 条链表为空");
            return;
        }
        System.out.print("第 " + (no + 1) + " 条链表信息为：");
        Emp curEmp = head;
        while (true) {
            System.out.printf("=> id=%d name=%s\t", curEmp.id, curEmp.name);
            if (curEmp.next == null) {
                break;
            }
            curEmp = curEmp.next;
        }
        System.out.println();
    }

    //在当前链表根据id查找Emp
    public Emp findEmpById(int id) {
        if (head == null) {
            return null;
        }
        Emp curEmp = head;
        while (true) {
            if (curEmp.id == id) {
                return curEmp;
            }
            if (curEmp.next == null) {
                return null;
            }
            curEmp = curEmp.next;
        }
    }
}


class Emp {
    public int id;
    public String name;
    public Emp next;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}