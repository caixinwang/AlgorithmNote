package Leetcode.AllQuestions;


import java.util.Stack;

public class Leetcode_0020_ValidParentheses {

    //左括号就压栈，右括号就弹栈，最后检查空。
    public boolean isValid(String s) {
        int n=s.length(),top=-1;
        char[] stack=new char[n];
        for(var c:s.toCharArray()){
            if(c==')'){//遇到右括号一定要和栈顶是匹配的
                if(top==-1||stack[top--]!='(') return false;
            }else if(c==']'){
                if(top==-1||stack[top--]!='[') return false;
            }else if(c=='}'){
                if(top==-1||stack[top--]!='{') return false;
            }else{
                stack[++top]=c;//左括号就进栈
            }
        }
        return top==-1;//别忘了最后检查，栈要为空才行
    }

}
