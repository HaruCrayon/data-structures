package com.lee.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author LiJing
 * @version 1.0
 * 逆波兰计算器（中缀表达式转后缀表达式）
 */
public class PolandNotation {
    public static void main(String[] args) {
        //完成将一个中缀表达式转成后缀表达式的功能
        //说明
        //1. 1+((2+3)×4)-5  转成  1 2 3 + 4 × + 5 –
        //2. 因为直接对str进行操作，不方便，因此 先将  "1+((2+3)×4)-5" =》 中缀表达式对应的List
        //   即 "1+((2+3)×4)-5" => ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]
        //3. 将得到的中缀表达式对应的List => 后缀表达式对应的List
        //   即 ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]  =》 ArrayList [1,2,3,+,4,*,+,5,–]
        String expression = "1+((2+3)*4)-5";
        List<String> infixExpressionList = toInfixExpressionList(expression);
        System.out.println("中缀表达式= " + infixExpressionList);
        List<String> suffixExpreesionList = parseSuffixExpreesionList(infixExpressionList);
        System.out.println("后缀表达式= " + suffixExpreesionList);
        int res = calculate(suffixExpreesionList);
        System.out.println("res=" + res);


        /*

        //先定义一个逆波兰表达式
        //(30+4)×5-6  => 30 4 + 5 × 6 - => 164
        // 4 * 5 - 8 + 60 + 8 / 2 => 4 5 * 8 - 60 + 8 2 / +  => 76
        //测试
        //为了方便，逆波兰表达式 的数字和符号使用空格隔开
//        String suffixExpression = "30 4 + 5 * 6 -";
        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";
        //思路
        //1. 先将 "3 4 + 5 × 6 - " => 放到ArrayList中
        //2. 将 ArrayList 传递给一个方法，遍历 ArrayList 配合栈 完成计算
        List<String> list = getListString(suffixExpression);
        int res = calculate(list);
        System.out.println("res=" + res);

         */
    }

    //方法：将中缀表达式转成对应的List
    //  s="1+((2+3)×4)-5";
    public static List<String> toInfixExpressionList(String s) {
        List<String> list = new ArrayList<>();
        int i = 0;//这是一个指针，用于遍历 中缀表达式字符串
        char c;//每遍历到一个字符，就放入到c
        String str;//用于拼接多位数
        do {
            //如果c是一个非数字，就直接加入到list   '0'[48]->'9'[57]
            if ((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57) {
                list.add("" + c);
                i++;
            } else {
                //如果c是一个数字，需要考虑多位数
                str = "";//先置空
                while (i < s.length() && (c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57) {
                    str += c;//拼接
                    i++;
                }
                list.add(str);
            }
        } while (i < s.length());
        return list;
    }

    //方法：将得到的中缀表达式对应的List => 后缀表达式对应的List
    //即 ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]  =》 ArrayList [1,2,3,+,4,*,+,5,–]
    public static List<String> parseSuffixExpreesionList(List<String> list) {
        //1) 初始化两个栈：运算符栈s1和储存中间结果的栈s2
        Stack<String> s1 = new Stack<>();//符号栈
        //说明：因为s2这个栈，在整个转换过程中，没有pop操作，而且后面还需要逆序输出
        //因此比较麻烦，这里就不用Stack<String> s2，直接使用 List<String> s2
//        Stack<String> s2 = new Stack<>();//储存中间结果的栈s2
        List<String> s2 = new ArrayList<>();//储存中间结果的List s2

        //2) 从左至右扫描中缀表达式，即遍历list
        for (String item : list) {
            if (item.matches("\\d+")) {
                //3) 遇到操作数时，将其加入s2
                s2.add(item);
            } else if (item.equals("(")) {
                //5) 遇到括号时：
                //  (1)如果是左括号“(”，则直接压入s1
                s1.push(item);
            } else if (item.equals(")")) {
                //  (2)如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop();//将 ( 弹出s1栈， 消除小括号
            } else {
                //4) 遇到运算符时，比较其与s1栈顶运算符的优先级：
                //  (1)如果s1为空，或栈顶运算符为左括号“(”，则直接将此运算符入栈
                //  (2)否则，若优先级比栈顶运算符的高，也将运算符压入s1
                //  (3)否则，将s1栈顶的运算符弹出并压入到s2中，再次转到(4.1)与s1中新的栈顶运算符相比较
                while (s1.size() != 0 && Operation.getValue(item) <= Operation.getValue(s1.peek())) {
                    s2.add(s1.pop());
                }
                s1.push(item);
            }
        }
        //6) 重复步骤2至5，直到表达式的最右边
        //7) 将s1中剩余的运算符依次弹出并压入s2
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        //8) 依次弹出s2中的元素并输出，结果的逆序即为中缀表达式对应的后缀表达式
        return s2;//注意：因为是存放到List, 因此按顺序输出就是后缀表达式对应的List
    }

    //方法：将一个逆波兰表达式，依次将数字和运算符 放入到 ArrayList中
    public static List<String> getListString(String suffixExpression) {
        //将 suffixExpression 分割
        String[] split = suffixExpression.split(" ");
        List<String> list = new ArrayList<>();
        for (String ele : split) {
            list.add(ele);
        }
        return list;
    }

    //方法：完成对逆波兰表达式的运算
	/*
	 *  (3+4)×5-6  => 3 4 + 5 × 6 -
	    1)从左至右扫描，将3和4压入堆栈；
	    2)遇到+运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈；
	    3)将5入栈；
		4)接下来是×运算符，因此弹出5和7，计算出7×5=35，将35入栈；
		5)将6入栈；
		6)最后是-运算符，计算出35-6的值，即29，由此得出最终结果
	 */
    public static int calculate(List<String> list) {
        Stack<String> stack = new Stack<>();
        //遍历 list
        for (String item : list) {
            //这里使用正则表达式来取出数
            if (item.matches("\\d+")) {//匹配的是多位数
                //入栈
                stack.push(item);
            } else {
                //pop出两个数，并运算，再入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if (item.equals("-")) {
                    res = num1 - num2;
                } else if (item.equals("*")) {
                    res = num1 * num2;
                } else if (item.equals("/")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("运算符有误");
                }
                //把res入栈
                stack.push("" + res);
            }
        }
        //最后留在stack中的数据是运算结果
        return Integer.parseInt(stack.pop());
    }
}


//编写一个类 Operation 可以返回一个运算符 对应的优先级
class Operation {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    //写一个方法，返回运算符对应的优先级数字
    public static int getValue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                System.out.println("不存在该运算符" + operation);
                break;
        }
        return result;
    }
}