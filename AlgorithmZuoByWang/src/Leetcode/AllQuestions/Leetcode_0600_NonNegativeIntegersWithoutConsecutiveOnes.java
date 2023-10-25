package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0600_NonNegativeIntegersWithoutConsecutiveOnes {
    int [][] dp;
    public int findIntegers(int n) {
        char[] str = Integer.toBinaryString(n).toCharArray();//转成二进制,因为题目要求是按照二进制来写
        dp=new int[str.length+1][2];
        for (int[] a:dp)Arrays.fill(a,-1);
        return f(str,0,0,true);
    }

    public int f(char[] str, int index, int pre_bit, boolean is_limit) {
        if (!is_limit&&dp[index][pre_bit]!=-1) return dp[index][pre_bit];
        if (index == str.length) {
            if (!is_limit) dp[index][pre_bit]=1;
            return 1;
        }
        int up = is_limit? str[index] - '0' : 1;
        int ans = 0;
        for (int d = 0; d <= up; d++) {
            if (!(pre_bit==1&&d==1)) ans += f(str, index + 1,d,is_limit&&d==up);
        }
        if (!is_limit)dp[index][pre_bit]=ans;
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(200));
    }
}
