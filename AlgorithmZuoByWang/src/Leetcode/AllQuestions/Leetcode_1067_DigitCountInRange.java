package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_1067_DigitCountInRange {
    public int digitsCount(int d, int low, int high) {
        char[] str_high=String.valueOf(high).toCharArray();
        char[] str_low=String.valueOf(low).toCharArray();
        int[][] dp_high=new int[str_high.length+1][str_high.length+1];
        int[][] dp_low=new int[str_low.length+1][str_low.length+1];
        for (int[] a:dp_high) Arrays.fill(a,-1);
        for (int[] a:dp_low) Arrays.fill(a,-1);
        int count=0;
        for(char c:str_low)if(c-'0'==d)count++;
        return f(str_high,d,0,0,true,false,dp_high)
                -f(str_low,d,0,0,true,false,dp_low)+count;
    }

    public int f(char[] str,int target,int index,int cnt,boolean is_limit,boolean is_fill,int[][] dp){
        if (!is_limit&&is_fill&&dp[index][cnt]!=-1) return dp[index][cnt];
        if (index==str.length) {
            if (!is_limit&&is_fill) dp[index][cnt]=cnt;
            return cnt;
        }
        int up=is_limit?str[index]-'0':9;
        int ans=0;
        if (!is_fill) ans=f(str,target,index+1,cnt,false,false,dp);
        for (int d=is_fill?0:1;d<=up;d++){
            ans+=f(str,target,index+1,d==target?cnt+1:cnt,is_limit&&d==up,true,dp);
        }
        if (!is_limit&&is_fill) dp[index][cnt]=ans;
        return ans;
    }

}
