package Leetcode.AllQuestions;

public class Leetcode_1029_ShortestCommonSupersequence {
    //从一个求出长度的动态规划表中倒推出具体的字符串是什么
    //dp[i+1][j+1] 表示 s 的前 i 个字母和 t 的前 j 个字母的最短公共超序列的长度
    public String shortestCommonSupersequence(String str1, String str2) {
        char[] s=str1.toCharArray(),t=str2.toCharArray();
        int n=s.length,m=t.length;
        int[][] dp=new int[n+1][m+1];
        for(int i=0;i<=n;i++) dp[i][0]=i;
        for(int j=0;j<=m;j++) dp[0][j]=j;
        for(int i=1;i<=n;i++)
            for(int j=1;j<=m;j++)
                dp[i][j]=s[i-1]==t[j-1]?1+dp[i-1][j-1]:1+Math.min(dp[i-1][j],dp[i][j-1]);
        char[] ans=new char[dp[n][m]];
        for(int i=n,j=m,index=ans.length-1;i>0||j>0;){
            if(i-1>=0&&j-1>=0&&s[i-1]==t[j-1]){
                ans[index--]=s[--i];
                j--;
            }else if(i-1>=0&&dp[i][j]==dp[i-1][j]+1){
                ans[index--]=s[--i];
            }else ans[index--]=t[--j];
        }
        return  String.valueOf(ans);
    }
}
