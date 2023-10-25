package Leetcode.AllQuestions;

import java.util.LinkedList;

public class Leetcode_0772_Calculate {
    public int calculate(String s) {
        char[] str=s.replaceAll(" ","").toCharArray();
        return f(str,0)[0];
    }

    public int[] f(char[] str,int start){//从表达式的i位置开始算，返回结果以及算到的位置。
        int cur,i;
        LinkedList<Integer> dq=new LinkedList<>();
        for(i=start,cur=0;i<str.length&&str[i]!=')';){
            if(Character.isDigit(str[i])){
                cur=cur*10+str[i++]-'0';
            }else if(str[i]=='('){
                int[] infos=f(str,i+1);
                cur=infos[0];
                i=infos[1]+1;
            }else{// 剩下 +  -  *  /
                add(cur,dq);
                cur=0;
                dq.addLast((int)str[i++]);
            }
        }
        add(cur,dq);
        return new int[]{compute(dq),i};
    }

    public void add(int cur,LinkedList<Integer> dq){
        if(!dq.isEmpty()&&(dq.peekLast()=='*'||dq.peekLast()=='/')){
            cur=dq.pollLast()=='*'?dq.pollLast()*cur:dq.pollLast()/cur;
        }
        dq.addLast(cur);
    }

    public int compute(LinkedList<Integer> dq){//只有加减法，乘除法在add的时候算了
        if(dq.isEmpty()) return 0;
        int ans=dq.pollFirst();//到真正计算的时候，需要从左往右
        while(!dq.isEmpty()){
            ans=dq.pollFirst()=='+'?ans+dq.pollFirst():ans-dq.pollFirst();
        }
        return ans;
    }
}
