package Leetcode.AllQuestions;

import java.util.LinkedList;
import java.util.Stack;

public class Leetcode_0394_DecodeString {

    static class Node {
        String ans;
        int stop;

        public Node(String ans, int stop) {
            this.ans = ans;
            this.stop = stop;
        }
    }

    public static String decodeString(String s) {
        return g(s.toCharArray(),0).ans;
    }

    //遇到左括号进递归，最简洁
    public static Node g(char[] str, int index) {//有数字的话，index一定压中数字，其它随意
        StringBuilder builder = new StringBuilder();
        int times=0;
        while (index < str.length && str[index] != ']') {
            if (Character.isDigit(str[index])) {//遇到数字进递归
                times=times*10+str[index++]-'0';
            } else if (str[index] == '[') {//遇到左括号就进递归，递归出来之后直接把times清零，题目保证遇到[ times必不为0
                Node info = g(str, index + 1);
                for (int i=0;i<times;i++) {//进递归出来肯定是结算times
                    for (char c:info.ans.toCharArray()) builder.append(c);
                }
                times=0;
                index=info.stop+1;
            } else {//字母
                builder.append(str[index++]);
            }
        }
        return new Node(builder.toString(), index);
    }


    /**
     * 出现了[]前面一定会有数字，反之亦然。由于遇到了数字就一定会有[]，所以这题遇到数字就进递归也行。
     */
    public static String decodeString1(String s) {
        StringBuilder builder = new StringBuilder();
        char[] str = s.toCharArray();
        int index = 0;
        while (index < str.length) {
            Node f = f(str, index);
            builder.append(f.ans);
            index = f.stop + 1;
        }
        return builder.toString();
    }

    public static Node f(char[] str, int index) {//有数字的话，index一定压中数字，其它随意
        StringBuilder builder = new StringBuilder();
        StringBuilder path = new StringBuilder();
        int times = 0;
        while ('0' <= str[index] && str[index] <= '9') {//算出重复次数
            times = times * 10 + str[index] - '0';
            index++;
        }
        if (times == 0) times = 1;//没有数字默认重复一次
        while (index < str.length && str[index] != ']') {
            if ('0' <= str[index] && str[index] <= '9') {//遇到数字进递归
                Node f = f(str, index);
                path.append(f.ans);
                index = f.stop + 1;
            } else if (str[index] == '[') {//[没有用，直接跳过
                index++;
            } else {//字母
                path.append(str[index++]);
            }
        }
        String p = path.toString();
        for (int i = 0; i < times; i++) {
            builder.append(p);
        }
        return new Node(builder.toString(), index);
    }

    /**
     * 一对中括号，前面一定有倍数。所以我们就一路压栈，遇到]才结算，往前弹出字符串，再弹出[，再确定倍数。
     * 翻倍了之后放回去。
     */
    public static String decodeString2(String s) {
        Stack<Character> stack=new Stack<>();
        for (char c:s.toCharArray()){
            if (c!=']'){
                stack.push(c);
                continue;
            }
            StringBuilder sb=new StringBuilder();
            while(!stack.isEmpty()&&Character.isLetter(stack.peek())){
                sb.insert(0,stack.pop());
            }
            char[] letters = sb.toString().toCharArray();
            stack.pop();// "["
            sb=new StringBuilder();
            while(!stack.isEmpty()&&Character.isDigit(stack.peek())){
                sb.insert(0,stack.pop());
            }
            int times=Integer.parseInt(sb.toString());
            for (int i = 0; i < times; i++) {
                for (Character cc:letters) stack.push(cc);
            }
        }
        StringBuilder ans=new StringBuilder();
        while(!stack.isEmpty()){
            ans.insert(0,stack.pop());
        }
        return ans.toString();
    }

    public static String decodeString3(String s) {
        Stack<Character> stack=new Stack<>();
        for (char c:s.toCharArray()){
            if (c!=']'){
                stack.push(c);
                continue;
            }
            LinkedList<Character> list=new LinkedList<>();
            while(!stack.isEmpty()&&Character.isLetter(stack.peek())){
                list.addFirst(stack.pop());
            }
            char[] letters = listToString(list).toCharArray();
            stack.pop();// "["
            list.clear();
            while(!stack.isEmpty()&&Character.isDigit(stack.peek())){
                list.addFirst(stack.pop());//栈
            }
            int times=Integer.parseInt(listToString(list));
            for (int i = 0; i < times; i++) {
                for (Character cc:letters) stack.push(cc);
            }
        }
        LinkedList<Character> ans=new LinkedList<>();
        while(!stack.isEmpty()){
            ans.addFirst(stack.pop());
        }
        return listToString(ans);
    }

    public static String listToString(LinkedList<Character> list){
        StringBuilder sb=new StringBuilder();
        for (char c: list){
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = decodeString("100[leetcode]");
        System.out.println(s);
    }
}
