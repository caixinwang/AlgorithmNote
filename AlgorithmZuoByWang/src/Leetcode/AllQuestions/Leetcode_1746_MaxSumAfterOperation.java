package Leetcode.AllQuestions;

public class Leetcode_1746_MaxSumAfterOperation {
    //dp[i][j]:必须以第i个元素结尾，j为0代表子数组没有任何平方元素，为1代表子数组有平方元素（不知道是哪个）
    public int maxSumAfterOperation(int[] nums) {
        int n=nums.length,ans=1<<31;
        int[][] dp=new int[n][2];
        dp[0][0]=nums[0];
        dp[0][1]=nums[0]*nums[0];
        for(int i=1;i<n;i++){
            dp[i][0]=nums[i]+max(0,dp[i-1][0]);
            dp[i][1]=max(nums[i]*nums[i]+max(dp[i-1][0],0),nums[i]+max(dp[i-1][1],0));
        }
        for(var a:dp) for(var b:a) ans=max(ans,b);
        return ans;
    }
    public int max(int a,int b){return a>b?a:b;}
    public int min(int a,int b){return a<b?a:b;}
}
