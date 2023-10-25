package Leetcode.AllQuestions;

public class Leetcode_0063_UniquePathsII {
    //一个位置可以往右走也可以往下走，所以依赖右边的和下面的格子
    public static int uniquePathsWithObstacles(int[][] obstacleGrid) {//登上位置之后检查
        int N=obstacleGrid.length,M=obstacleGrid[0].length;
        int[][] dp=new int[N][M];
        if (obstacleGrid[N-1][M-1]==0) dp[N-1][M-1]=1;
        dp[N-1][M-1]=obstacleGrid[N-1][M-1]==0?1:0;
        for (int i=N-1;i>=0;i--){
            for (int j=M-1;j>=0;j--){
                if (i==N-1&&j==M-1) continue;
                if (obstacleGrid[i][j]==0) dp[i][j]=(i+1<N?dp[i+1][j]:0)+(j+1<M?dp[i][j+1]:0);
            }
        }
        return dp[0][0];
    }

}
