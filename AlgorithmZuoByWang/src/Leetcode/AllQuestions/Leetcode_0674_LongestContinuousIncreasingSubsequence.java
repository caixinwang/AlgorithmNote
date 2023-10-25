package Leetcode.AllQuestions;

public class Leetcode_0674_LongestContinuousIncreasingSubsequence {
    public int findLengthOfLCIS(int[] arr) {//所谓连续递增子序列，其实就是子串！！！！
        if (arr==null||arr.length==0) return -1;
        int N=arr.length;
        int[] dp=new int[N];
        dp[0]=1;
        for (int i=1;i<N;i++){
            dp[i]=1;
            if (arr[i-1]<arr[i]) dp[i]=dp[i-1]+1;
        }
        int ans=0;
        for (int n:dp){
            ans = Math.max(ans, n);
        }
        return ans;
    }
}
