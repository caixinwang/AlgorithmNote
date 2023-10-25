package Leetcode.AllQuestions;

public class Leetcode_0221_MaximalSquare {
    public int maximalSquare(char[][] matrix) {
        int N=matrix.length,M=matrix[0].length;
        int[][] dp=new int[N][M];//[i][j]代表正方形必须以(i,j)作为右下角点，最长的正方形边长。
        for (int i=0;i<N;i++){
            for (int j=0;j<M;j++){
                dp[i][j]=matrix[i][j]=='1'?1:0;
            }
        }
        for (int i=1;i<N;i++){
            for (int j=1;j<M;j++){
                if (matrix[i][j]=='1') {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j - 1], dp[i][j - 1]), dp[i - 1][j]);
                }
            }
        }
        int ans=0;
        for (int i=0;i<N;i++){
            for (int j=0;j<M;j++){
                ans = Math.max(ans, dp[i][j]);
            }
        }
        return ans*ans;
    }
}
