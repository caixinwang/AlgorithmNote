package Leetcode.AllQuestions;

public class Leetcode_0097_InterleavingString {
    //第一种情况，i和j位置都参与。第二种只有i参与。第三种只有j参与。第四种都不参与，这种情况不符合题意！
    //这里参与的意思是参与s3最后一两个字符的构成
    public static boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length()+s2.length()!=s3.length()) return false;
        int N=s1.length(),M=s2.length();
        char[] str1=s1.toCharArray(),str2=s2.toCharArray(),str3=s3.toCharArray();
        boolean[][] dp=new boolean[N+1][M+1];//dp[i][j]代表s1长度为i的前缀和s2长度为j的前缀能不能交错租出s3 i+j长度的前缀
        dp[0][0]=true;
        for (int i = 1; i <= N; i++) {
            dp[i][0]=dp[i-1][0]&&str1[i-1]==str3[i-1];
        }
        for (int i = 1; i <= M; i++) {
            dp[0][i]=dp[0][i-1]&&str2[i-1]==str3[i-1];
        }
        for (int i=1;i<=N;i++){
            for (int j=1;j<=M;j++){
                boolean p1=((str1[i-1]==str3[i+j-1]&&str2[j-1]==str3[i+j-2])
                        || (str1[i-1]==str3[i+j-2]&&str2[j-1]==str3[i+j-1])) && dp[i-1][j-1];
                boolean p2=str1[i-1]==str3[i+j-1]&&dp[i-1][j];
                boolean p3=str2[j-1]==str3[i+j-1]&&dp[i][j-1];
                dp[i][j]=p1||p2||p3;
            }
        }
        return dp[N][M];
    }

    public static void main(String[] args) {
        String s1="aabcc",s2="dbbca",s3="aadbbcbcac";
        System.out.println(isInterleave(s1,s2,s3));
    }
}
