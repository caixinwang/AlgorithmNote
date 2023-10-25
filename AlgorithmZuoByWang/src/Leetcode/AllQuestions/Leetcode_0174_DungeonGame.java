package Leetcode.AllQuestions;

public class Leetcode_0174_DungeonGame {
    /**
     * 依赖关系，一个格子要有多少血，取决于后面的关卡的情况，所以需要从[N-1][M-1]开始填。
     * 最右下角的格子：x+hp>=1 ==> x>=1-hp => x=1-hp
     * 在一个普遍的格子有两种情况，一种是骑士打算走右边，一种是打算走下边
     * x+hp[i][j]>=dp[i+1][j]  或者  x+hp[i][j]>=dp[i][j+1]
     * => x=max{1,dp[i+1][j]-hp[i][j]}  或者  x=max{1,dp[i][j+1]-hp[i][j]}
     * x=min{max{1,dp[i+1][j]-hp[i][j]},max{1,dp[i][j+1]-hp[i][j]}}
     * @param hp 地图
     * @return 返回至少需要的血量
     */
    public static int calculateMinimumHP(int[][] hp) {
        int N=hp.length,M=hp[0].length;
        int[][] dp=new int[N][M];
        dp[N-1][M-1]=Math.max(1,1-hp[N-1][M-1]);
        for (int i=N-1;i>=0;i--){
            for (int j=M-1;j>=0;j--){
                if (i==N-1&&j==M-1) continue;
                int p1=i+1<N?Math.max(1,dp[i+1][j]-hp[i][j]):Integer.MAX_VALUE;
                int p2=j+1<M?Math.max(1,dp[i][j+1]-hp[i][j]):Integer.MAX_VALUE;
                dp[i][j]=Math.min(p1,p2);
            }
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        int[][]hp=new int[][]{
                {-2,-3,3},
                {-5,-10,1},
                {10,30,-5},
        };
        System.out.println(calculateMinimumHP(hp));
    }
}
