package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_1012_NumbersWithRepeatedDigits {//原路照搬2376，答案就是n-x
    int[][] dp;
    public int numDupDigitsAtMostN(int n) {//数位dp把n转为字符串来做
        char[] str=String.valueOf(n).toCharArray();
        dp=new int[str.length+1][1<<10];
        for (int[] a:dp) Arrays.fill(a,-1);
        return n-f(str,0,0,true,false);
    }

    public int f(char[] str,int index,int mask,boolean is_limit,boolean is_fill){
        if (!is_limit&&is_fill&&dp[index][mask]!=-1) return dp[index][mask];
        if (index==str.length) {
            if (!is_limit&&is_fill) dp[index][mask]=is_fill ? 1 : 0;
            return is_fill ? 1 : 0;
        }
        int up=is_limit?str[index]-'0':9;
        int ans=0;
        if (!is_fill) ans=f(str,index+1,mask,false,false);//只有一开始可以跳过，一旦开始填了就不能跳过了
        for (int d=is_fill?0:1;d<=up;d++){
            if ((mask>>d&1)==0){
                ans+=f(str,index+1,1<<d|mask,is_limit&&d==up,true);
            }
        }
        if (!is_limit&&is_fill) dp[index][mask]=ans;
        return ans;
    }


}
