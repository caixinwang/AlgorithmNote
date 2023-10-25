package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0233_NumberOfDigitOne {
    int [][] dp;
    public int countDigitOne(int n) {
        char[] str = String.valueOf(n).toCharArray();
        dp=new int[str.length+1][str.length+1];
        for (int[] a:dp) Arrays.fill(a,-1);
        return f(str,0,0,true);
    }

    public int f(char[] str, int index, int cnt, boolean is_limit) {
        if (!is_limit&&dp[index][cnt]!=-1) return dp[index][cnt];
        if (index == str.length) {
            if (!is_limit) dp[index][cnt]=cnt;
            return cnt;
        }
        int up = is_limit ? str[index] - '0' : 9;
        int ans = 0;
        for (int d = 0; d <= up; d++) {
            ans += f(str, index + 1,d==1?cnt+1:cnt,is_limit&&d==up);
        }
        if (!is_limit) dp[index][cnt]=ans;
        return ans;
    }

}
