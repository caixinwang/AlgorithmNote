package Leetcode.AllQuestions;

public class Leetcode_0115_DistinctSubsequences {

    //可能性划分：(i,j)位置，多出来了一个j。
    // 1.i位置参与搞定j位置的字符，这种情况只有i位置字符和j位置字符一样，dp[i-1][j-1]
    // 2.i位置不参与搞定j位置的字符，那么就是前面的去搞定dp[i-1][j]
    public static int numDistinct(String s, String t) {
        char[] str1=s.toCharArray(),str2=t.toCharArray();
        int N=str1.length,M=str2.length;
        int[][] dp=new int[N+1][M+1];//s长度为i子串的子序列有多少个t长度为j的子串
        dp[0][0]=1;//第一行
        for (int i=1;i<=N;i++){//1th col
            dp[i][0]=1;
        }
        for (int i=1;i<=N;i++){
            for (int j=1;j<=M;j++){
                dp[i][j]=dp[i-1][j];
                if (str1[i-1]==str2[j-1]) dp[i][j]+=dp[i-1][j-1];
            }
        }
        return dp[N][M];
    }

    public static void main(String[] args) {
        String s = "rabbbit", t = "rabbit";
        System.out.println(numDistinct(s,t));
    }
}
