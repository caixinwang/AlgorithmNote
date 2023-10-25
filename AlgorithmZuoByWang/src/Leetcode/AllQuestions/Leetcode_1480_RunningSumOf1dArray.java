package Leetcode.AllQuestions;

public class Leetcode_1480_RunningSumOf1dArray {
    public int[] runningSum(int[] nums) {
        int[] ans=new int[nums.length];
        ans[0]=nums[0];
        for (int i = 1; i < ans.length; i++) {
            ans[i]=nums[i]+ans[i-1];
        }
        return ans;
    }
}
