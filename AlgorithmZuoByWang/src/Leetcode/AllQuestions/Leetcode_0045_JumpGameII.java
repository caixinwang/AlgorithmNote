package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0045_JumpGameII {
    public int jump(int[] nums) {
        if (nums == null || nums.length <= 1) return 0;
        int[] dp = new int[nums.length];
        Arrays.fill(dp,-1);//-1代表跳不到
        dp[nums.length - 1] = 0;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i]==0) continue;
            for (int step = 1; step <= nums[i] && i + step < nums.length; step++) {
                if (dp[i + step]!=-1) {
                    dp[i] = Math.min(dp[i]==-1?Integer.MAX_VALUE:dp[i], dp[i+step]+1);
                }
            }
        }
        return dp[0];
    }

    public int jump2(int[] nums) {
        if (nums == null || nums.length <= 1) return 0;
        int step=0;//当前是第几步
        int R=0;//当前步数能到达的最远边界
        int nextR=0;//下一步能到达的最远边界
        int i=0;
//        for (; i < nums.length i++) {//保证能跳到，for每步都动，while可以不动
//            if (i<=R){
//                nextR = Math.max(nextR,i+nums[i]);
//            }else {//i位置在step步内来不了
//                step++;
//                R=nextR;
//                nextR = Math.max(nextR,i+nums[i]);
//            }
//        }
        while(i<nums.length){
            if (i<=R){
                nextR = Math.max(nextR,i+nums[i++]);
            }else {
                step++;
//                if (i>nextR) return -1;//根本到不了
                R=nextR;
            }
        }
        return step;
    }
}
