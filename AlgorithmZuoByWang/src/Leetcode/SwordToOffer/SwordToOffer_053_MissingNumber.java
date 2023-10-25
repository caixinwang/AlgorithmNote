package Leetcode.SwordToOffer;

public class SwordToOffer_053_MissingNumber {
    public int missingNumber(int[] nums) {
        int n=nums.length;
        int l=0,r=n;
        while(l<r){
            if(nums[l]==l) l++;
            else if(l<nums[l]&&nums[l]<=r-1&&nums[nums[l]]!=nums[l]) swap(nums,l,nums[l]);
            else swap(nums,l,--r);
        }
        return l;
    }
    public void swap(int[] arr,int a,int b){
        int t=arr[a];
        arr[a]=arr[b];
        arr[b]=t;
    }
}
