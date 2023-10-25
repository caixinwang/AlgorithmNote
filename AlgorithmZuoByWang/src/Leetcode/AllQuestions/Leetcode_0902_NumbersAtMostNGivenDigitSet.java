package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0902_NumbersAtMostNGivenDigitSet {
    int [] dp;
    boolean[] dg=new boolean[10];
    public int atMostNGivenDigitSet(String[] digits, int n) {
        char[] str = String.valueOf(n).toCharArray();
        for (int i = 0; i < digits.length; i++) {
            dg[digits[i].charAt(0)-'0']=true;
        }
        dp=new int[str.length+1];
        Arrays.fill(dp,-1);
        return f(str,0,true,false);
    }

    //这题需要考虑前导0，因为题目可能不给你0使用，所以需要用is_fill来考虑跳过的情况
    public int f(char[] str, int index, boolean is_limit,boolean is_fill) {
        if (is_fill&&!is_limit&&dp[index]!=-1) return dp[index];
        if (index == str.length) {
            if (is_fill&&!is_limit) dp[index]=is_fill?1:0;
            return is_fill?1:0;
        }
        int up = is_limit ? str[index] - '0' : 9;
        int ans = 0;
        if (!is_fill) ans=f(str,index+1,false,false);
        for (int d = is_fill?0:1; d <= up; d++) {
            if (dg[d])ans += f(str, index + 1,is_limit&&d==up,true);
        }
        if (is_fill&&!is_limit) dp[index]=ans;
        return ans;
    }

    public static void main(String[] args) {
        String[] strings=new String[]{"1","3","5","7"};
        System.out.println(new Leetcode_0902_NumbersAtMostNGivenDigitSet().atMostNGivenDigitSet(strings,100));
    }


}
