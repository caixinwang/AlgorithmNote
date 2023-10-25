package Leetcode.AllQuestions;

public class Leetcode_0518_Change {
    //dp[i][j]代表使用前i种货币，凑成j金额的组合数
    public int change(int amount, int[] coins) {
        int n=coins.length;
        int[][] dp=new int[n+1][amount+1];
        for(int i=0;i<=n;i++) dp[i][0]=1;//凑0元，不用凑就成功了
        for(int i=1;i<=n;i++){
            for(int j=1;j<=amount;j++){
                // for(int k=0;j-k>=0;j+=coins[i-1])dp[i][j]+=dp[i-1][j-k];斜率优化
                dp[i][j]=dp[i-1][j]+(j-coins[i-1]>=0?dp[i][j-coins[i-1]]:0);
            }
        }
        return dp[n][amount];
    }
}
