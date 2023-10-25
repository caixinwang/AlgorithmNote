package Leetcode.SwordToOffer;

public class SwordToOffer_060_DicesProbability {
    //dp[i][j]代表投掷i个骰子，投出的点数为j的概率。
    //可能性讨论：第i次投出1~6,如果第i次投出了1，那么前i-1次就需要投出j-1.所以dp[i][j]=sum{dp[i-1],...,dp[i-6]},i-k需要大于等于1
    //由于只需要i-1行，所以用两行滚动更新即可。
    public double[] dicesProbability(int n) {
        double[] dp=new double[6*n+1];
        for(int i=1;i<=6;i++) dp[i]=1d/6;//只有一个骰子的时候，都是1/6的概率。
        for(int i=2;i<=n;i++){//i代表投掷的骰子的数量
            double [] ndp=new double[6*n+1];
            for(int j=1;j<=i*6;j++){
                for(int k=1;k<=6&&j-k>=1;k++){
                    ndp[j]+=dp[j-k]*(1d/6);
                }
            }
            dp=ndp;
        }
        int i=0;
        for(;dp[i]==0;i++);//找到第1个不为0的位置，i后面的位置都不可能为0。
        double[] ans=new double[6*n-i+1];
        System.arraycopy(dp,i,ans,0,ans.length);
        return ans;
    }
}
