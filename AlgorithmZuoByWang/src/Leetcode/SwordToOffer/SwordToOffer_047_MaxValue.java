package Leetcode.SwordToOffer;

public class SwordToOffer_047_MaxValue {
    public int maxValue(int[][] grid) {
        int n=grid.length,m=grid[0].length;
        int[][] dp=new int[n][m];
        dp[n-1][m-1]=grid[n-1][m-1];
        for(int i=n-1;i>=0;i--){
            for(int j=m-1;j>=0;j--){
                if(i==n-1&&j==m-1) continue;
                int p1=i+1<n?dp[i+1][j]:0;
                int p2=j+1<m?dp[i][j+1]:0;
                dp[i][j]=grid[i][j]+Math.max(p1,p2);
            }
        }
        return dp[0][0];
    }
}
