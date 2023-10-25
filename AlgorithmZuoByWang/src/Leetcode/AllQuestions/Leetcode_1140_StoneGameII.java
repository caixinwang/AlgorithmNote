package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_1140_StoneGameII {
    //从后面往前转移，因为希望知道后面对手能拿多少
    public int stoneGameII(int[] piles) {
        int n=piles.length;
        int[][] dp=new int[n+1][n+1];//从i位置出发，M=j,先手最多可以拿走多少石头;n位置没有石头
        Arrays.fill(dp[n-1],piles[n-1]);
        int[] tsum=new int[n+1];
        for(int i=n-1;i>=0;i--) tsum[i]=tsum[i+1]+piles[i];
        for(int i=n-2;i>=0;i--){
            for(int j=1;j<=n;j++){
                if(i+2*j>=n) {//如果可以直接拿走全部石头了，不用犹豫。优化，不加也对
                    dp[i][j]=tsum[i];
                    continue;
                }
                for(int take=1;take<=j*2&&i+take<=n;take++){//如果上面有优化，这里条件可以少一个<=n
                    dp[i][j]=max(dp[i][j],tsum[i]-dp[i+take][max(j,take)]);
                }
            }
        }
        return dp[0][1];
    }
    public int max(int a,int b){return a>b?a:b;}
}
