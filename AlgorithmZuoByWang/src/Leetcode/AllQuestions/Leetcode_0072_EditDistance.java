package Leetcode.AllQuestions;

import java.util.TreeSet;

public class Leetcode_0072_EditDistance {
    //dp[i][j]是长度为i的word1和变为长度为j的word2需要的操作次数
    //如果str1[i]==str2[j]那么直接就等于dp[i-1][j-1]
    //如果不等，那么分三种情况：
    //1.我们可以直接把i位置的字符删掉，那么就还剩下i-1长度的word1要匹配j长度的word2，也就是1+dp[i-1][j]
    //2.我们可以直接把word2[j]插入到[i]的后面，还剩下j-1长度的word2要搞定，也就是1+dp[i][j-1]
    //3.我们可以直接把i位置的字符替换成word2[j]，那么i-1长度的word1以及j-1长度的word2，也就是1+dp[i-1][j-1]
    public int minDistance(String word1, String word2) {
        char[] s1=word1.toCharArray(),s2=word2.toCharArray();
        int n=s1.length,m=s2.length;
        int[][] dp=new int[n+1][m+1];
        for(int i=0;i<=n;i++) dp[i][0]=i;
        for(int j=0;j<=m;j++) dp[0][j]=j;
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                if(s1[i-1]==s2[j-1]) dp[i][j]=dp[i-1][j-1];
                else dp[i][j]=1+Math.min(Math.min(dp[i-1][j-1],dp[i][j-1]),dp[i-1][j]);
            }
        }
        return dp[n][m];
    }

    public static void main(String[] args) {
        TreeSet set=new TreeSet();
    }
}
