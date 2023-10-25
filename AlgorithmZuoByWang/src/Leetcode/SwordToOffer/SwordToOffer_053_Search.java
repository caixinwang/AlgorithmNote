package Leetcode.SwordToOffer;

public class SwordToOffer_053_Search {
    public int search(int[] nums, int target) {
        int n=nums.length,left=0,right=n-1,l=0,r=n-1,mid;
        while(l<=r){//找打小于等于的最右
            mid=l+(r-l>>1);
            if(nums[mid]<=target) l=mid+1;
            else r=mid-1;
        }
        right=r;
        for(l=0,r=n-1;l<=r;){//大于等于的最左
            mid=l+(r-l>>1);
            if(nums[mid]>=target) r=mid-1;
            else l=mid+1;
        }
        left=l;
        return right-left+1;
    }
}
