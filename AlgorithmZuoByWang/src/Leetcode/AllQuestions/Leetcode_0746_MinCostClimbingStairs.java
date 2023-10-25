package Leetcode.AllQuestions;

public class Leetcode_0746_MinCostClimbingStairs {
    //dp[i]代表登上第i层最小开销，最后需要登上第n层,直接写了省空间的做法
    public int minCostClimbingStairs(int[] cost) {
        int f1=0,f2=0;//分别代表dp[i-1]和dp[i-2].初始化：去0层和1层开销为0
        for(int i=2;i<=cost.length;i++){
            int nf1=Math.min(f1+cost[i-1],f2+cost[i-2]);
            f2=f1;
            f1=nf1;
        }
        return f1;
    }
}
