package Leetcode.AllQuestions;

public class Leetcode_0053_MaximumSubarray {
    public static int maxSubArray(int[] nums) {
        if (nums==null||nums.length==0) return 0;
        int N=nums.length;
        int[] dp=new int[N];//dp[i]代表必须以i结尾的最大和连续子数组的值
        dp[0]=nums[0];
        for (int i = 1; i < dp.length; i++) {
            dp[i]=dp[i-1]<0?nums[i]:nums[i]+dp[i-1];
        }
        int ans=dp[0];
        for (int i = 1; i < dp.length; i++) {
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    public static int maxSubArray2(int[] nums) {//空间压缩
        if (nums==null||nums.length==0) return 0;
        int max;
        int pre=nums[0];
        int ans=pre;
        for (int i = 1; i < nums.length; i++) {
            max=pre<0?nums[i]:nums[i]+pre;
            pre=max;
            ans = Math.max(ans, max);
        }
        return ans;
    }

    public static int maxSubArray3(int[] nums) {//用一个变量划过
        if (nums==null||nums.length==0) return 0;
        int ans=nums[0];
        int cur=0;
        for (int i = 0; i < nums.length; i++) {
            cur+=nums[i];
            ans = Math.max(ans, cur);
            if (cur<0) cur=0;
        }
        return ans;
    }
}
