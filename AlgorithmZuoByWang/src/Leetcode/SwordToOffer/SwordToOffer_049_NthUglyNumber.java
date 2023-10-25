package Leetcode.SwordToOffer;

public class SwordToOffer_049_NthUglyNumber {
    //后面的丑数肯定是由前面较小的丑数乘以2-3-5得到的。
    //我们可以把dp[i]的候选定义为由2得来的、3得来的、5得来的。2-3-5分别对应一个指针。
    //例如最开始的1， 2-3-5的指针肯定都指向1，然后2率先胜出，2的指针就移动到了2下标。也就是说谁胜出谁就移动
    public int nthUglyNumber(int n) {
        int[] dp=new int[n+1];//dp[i]代表第i个丑数
        int p2,p3,p5;
        p2=p3=p5=dp[1]=1;
        for(int i=2;i<=n;i++){
            dp[i]=Math.min(Math.min(dp[p2]*2,dp[p3]*3),dp[p5]*5);
            if(dp[p2]*2==dp[i])p2++;
            if(dp[p3]*3==dp[i])p3++;
            if(dp[p5]*5==dp[i])p5++;
        }
        return dp[n];
    }
}
