package Leetcode.AllQuestions;

public class Leetcode_0713_SubarrayProductLessThanK {
    public static int numSubarrayProductLessThanK(int[] nums, int k) {
        int n=nums.length;
        int ans=0;
        long mul=1;
        for (int r=0,l=0;r<n;r++){
            mul*=nums[r];
            while(l<=r&&mul>=k) mul/=nums[l++];
            if (mul<k)ans+=r-l+1;//需要if判断么，因为以r作为右端点不一定有答案。
        }
        return ans;
    }

}
