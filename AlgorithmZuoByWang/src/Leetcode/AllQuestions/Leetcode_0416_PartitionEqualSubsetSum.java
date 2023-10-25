package Leetcode.AllQuestions;

public class Leetcode_0416_PartitionEqualSubsetSum {
    public boolean canPartition(int[] nums) {
        if (nums==null||nums.length<2) return false;
        int sum=0,N=nums.length;
        for (int n:nums) sum+=n;
        if ((sum&1)==1) return false;
        int half=sum/2;
        boolean[][] dp=new boolean[N][half+1];//dp[i][j]为0~i上自由选择能够凑出j的累加和
        for (int i=0;i<N;i++) dp[i][0]=true;
        if (nums[0]>=0&&nums[0]<=half) dp[0][nums[0]]=true;
        for (int i=1;i<N;i++){
            for (int j=1;j<=half;j++){
                dp[i][j]=dp[i-1][j]||(j - nums[i] >= 0 &&j - nums[i] <=half&& dp[i - 1][j - nums[i]]);
            }
        }
        return dp[N-1][half];
    }
}
