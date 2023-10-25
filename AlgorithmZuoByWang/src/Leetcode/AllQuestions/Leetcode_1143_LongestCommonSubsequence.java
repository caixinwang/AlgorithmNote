package Leetcode.AllQuestions;

public class Leetcode_1143_LongestCommonSubsequence {
    public int longestCommonSubsequence(String text1, String text2) {
        char[] str1=text1.toCharArray(),str2=text2.toCharArray();
        int n=str1.length,m=str2.length;
        int[][] dp=new int[n+1][m+1];
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                dp[i][j]=str1[i-1]==str2[j-1]?dp[i-1][j-1]+1:Math.max(dp[i-1][j],dp[i][j-1]);            }
        }
        return dp[n][m];
    }
}
