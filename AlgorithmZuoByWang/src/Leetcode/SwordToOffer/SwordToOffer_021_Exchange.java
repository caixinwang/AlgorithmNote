package Leetcode.SwordToOffer;

public class SwordToOffer_021_Exchange {
    public int[] exchange(int[] nums) {
        int n=nums.length,l=0,r=n;//l开区间，r闭区间
        while(l<r){
            if((nums[l]&1)==1) l++;
            else swap(nums,l,--r);
        }
        return nums;
    }

    public void swap(int[] arr,int a,int b){
        int t=arr[a];
        arr[a]=arr[b];
        arr[b]=t;
    }
}
