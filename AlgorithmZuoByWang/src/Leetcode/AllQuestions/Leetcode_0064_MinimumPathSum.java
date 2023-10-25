package Leetcode.AllQuestions;

public class Leetcode_0064_MinimumPathSum {
    //一个位置明显依赖自己右边的和下边的，所以从下往上，从右往左填。
    //可以空间压缩
    public int minPathSum(int[][] grid) {
        int N=grid.length,M=grid[0].length;
        int[][] dp=new int[N][M];
        dp[N-1][M-1]=grid[N-1][M-1];
        for (int i=N-2;i>=0;i--){
            dp[i][M-1]=grid[i][M-1]+dp[i+1][M-1];
        }
        for (int i=M-2;i>=0;i--){
            dp[N-1][i]=dp[N-1][i+1]+grid[N-1][i];
        }
        for (int i=N-2;i>=0;i--){
            for (int j=M-2;j>=0;j--){
                dp[i][j]=grid[i][j]+Math.min(dp[i+1][j],dp[i][j+1]);
            }
        }
        return dp[0][0];
    }

    public int minPathSum2(int[][] grid) {
        int N=grid.length,M=grid[0].length;
        int[][] dp=new int[N][M];
        dp[N-1][M-1]=grid[N-1][M-1];
        for (int i=N-1;i>=0;i--){
            for (int j=M-1;j>=0;j--){
                if (i==N-1&&j==M-1) continue;
                dp[i][j]=grid[i][j]+Math.min(i+1<N?dp[i+1][j]:Integer.MAX_VALUE,j+1<M?dp[i][j+1]:Integer.MAX_VALUE);
            }
        }
        return dp[0][0];
    }
}
