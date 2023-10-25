package Leetcode.AllQuestions;

import java.util.*;

public class Leetcode_0301_RemoveInvalidParentheses {

    public static List<String> removeInvalidParentheses(String s) {
        int left=0,right=0;//需要去除的左括号和右括号的数量
        char[] str = s.toCharArray();
        for (char c:str){
            if (c=='('){
                left++;
            }
            if (c==')'){
                if (left==0) right++;//右括号太多了，要去掉
                else left--;
            }
        }
        HashSet<String> ans=new HashSet<>();//去重
        LinkedList<Character> path=new LinkedList<>();
        f(str,0,left,right,path,ans);
        return new ArrayList<>(ans);
    }

    private static void f(char[] str, int index, int left, int right, LinkedList<Character> path, HashSet<String> ans) {
        if (index==str.length&&left==0&&right==0){
            String s = pathToStr(path);
            if (valid(s)) ans.add(s);
            return;
        }
        if (str.length-index<left+right) return;
        path.addLast(str[index]);//任何字符都有不去掉这种情况
        f(str,index+1,left,right,path,ans);//不去掉
        path.pollLast();
        if (str[index]=='('&&left>0){
            f(str,index+1,left-1,right,path,ans);//去掉
        }
        if(str[index]==')'&&right>0){
            f(str,index+1,left,right-1,path,ans);//去掉
        }
    }

    private static String pathToStr(LinkedList<Character> path){
        if (path.size()==0) return "";
        StringBuilder builder=new StringBuilder();
        for (char c:path){
            builder.append(c);
        }
        return builder.toString();
    }

    private static boolean valid(String s){
        if (s==null||s.length()==0) return true;
        char[] str = s.toCharArray();
        int cur=0;
        for (int c:str){
            if (c=='('){
                cur++;
            }
            if (c==')'){
                if (--cur<0) return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        String s="()())()";
        removeInvalidParentheses(s);
    }

}
