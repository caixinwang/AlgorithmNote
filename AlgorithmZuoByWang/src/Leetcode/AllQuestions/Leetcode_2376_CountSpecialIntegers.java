package Leetcode.AllQuestions;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Leetcode_2376_CountSpecialIntegers {
    public int countSpecialNumbers(int n) {//数位dp把n转为字符串来做
        char[] str=String.valueOf(n).toCharArray();
        return f(str,0,0,true,false);
    }

    /**
     *
     * @param str n转成的字符串，方便处理
     * @param index 当前处理到的位置，[index ... 往后的位置需要处理
     * @param mask 代表集合，当前有哪些数字填过了？mask>>d&1==1 代表d已经填过了
     * @param is_limit 当前index位置能填的数字的上限有没有被约束，如果被约束了，那么只能填到str[i],没有被约束就能填到9
     * @param is_fill 代表index位置前面有没有填过数字。一旦填过数字之后，后面的位置就不能跳过了，不然填出来的东西会有坑
     * @return 返回有多少种数字符合题意
     */
    public int f(char[] str,int index,int mask,boolean is_limit,boolean is_fill){
        if (index==str.length) return is_fill?1:0;
        int up=is_limit?str[index]-'0':9;
        int ans=0;
        if (!is_fill) ans=f(str,index+1,mask,false,false);//只有一开始可以跳过，一旦开始填了就不能跳过了
        for (int d=is_fill?0:1;d<=up;d++){
            if ((mask>>d&1)==0){
                ans+=f(str,index+1,1<<d|mask,is_limit&&d==up,true);
            }
        }
        return ans;
    }

    int[][] dp;//只需记忆化前两个参数，因为is_limit==true的时候以及is_fill为false的时候只会计算一次

    public int countSpecialNumbers2(int n) {//数位dp把n转为字符串来做
        char[] str=String.valueOf(n).toCharArray();
        dp=new int[str.length+1][1<<10];
        for (int[] a:dp) Arrays.fill(a,-1);
        return f2(str,0,0,true,false);
    }

    public int f2(char[] str,int index,int mask,boolean is_limit,boolean is_fill){
        if (!is_limit&&is_fill&&dp[index][mask]!=-1) return dp[index][mask];
        if (index==str.length) {
            if (!is_limit&&is_fill) dp[index][mask]=is_fill ? 1 : 0;
            return is_fill ? 1 : 0;
        }
        int up=is_limit?str[index]-'0':9;
        int ans=0;
        if (!is_fill) ans=f2(str,index+1,mask,false,false);//只有一开始可以跳过，一旦开始填了就不能跳过了
        for (int d=is_fill?0:1;d<=up;d++){
            if ((mask>>d&1)==0){
                ans+=f2(str,index+1,1<<d|mask,is_limit&&d==up,true);
            }
        }
        if (!is_limit&&is_fill) dp[index][mask]=ans;
        return ans;
    }

}
