package Leetcode.SwordToOffer;

public class SwordToOffer_057_TwoSum {
    //相向双指针
    public int[] twoSum(int[] nums, int target) {
        for(int l=0,r=nums.length-1;l<r;){
            if(nums[l]+nums[r]==target)return new int[]{nums[l],nums[r]};
            else if(nums[l]+nums[r]>target) r--;
            else l++;
        }
        return new int[]{};
    }
}
