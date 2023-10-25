package Leetcode.AllQuestions;

public class Leetcode_0343_IntegerBreak {
    //1.动态规划
    //2.数学，求导，发现e是极值点，那么全部代入3
    public int integerBreak(int n) {
        if(n<=3) return n-1;
        long res = 1;
        while(n > 4){
            res *= 3;
            n -= 3;
        }
        return (int)(res * n );
    }

    public int integerBreak2(int n) {
        if(n==2||n==3) return n-1;//题目要求裂开至少2次，2和3单独处理返回
        int[] dp=new int[n+1];//一个数可以裂开也可以不裂开的最大值。
        dp[1]=1;
        for(int i=2;i<=n;i++){
            dp[i]=i;
            for(int j=1;j<i;j++){
                dp[i]=Math.max(j*dp[i-j],dp[i]);
            }
        }
        return dp[n];
    }
}
