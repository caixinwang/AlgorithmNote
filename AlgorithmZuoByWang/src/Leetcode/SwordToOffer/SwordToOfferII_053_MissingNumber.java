package Leetcode.SwordToOffer;

public class SwordToOfferII_053_MissingNumber {
    //递增排序数组 使用二分 对于一个位置mid 如果nums[mid]<=mid 那么说明0~mid位置都是不缺的
    //我们要的是最左的缺的位置
    //O(log n)
    public int missingNumber1(int[] nums) {
        int n=nums.length,l=0,r=n-1,mid;
        while(l<=r){
            mid=l+(r-l>>1);
            if(nums[mid]>mid) r=mid-1;
            else l=mid+1;
        }
        return l;
    }

    //O(N)
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
