package Leetcode.AllQuestions;

public class Leetcode_0027_RemoveElement {
    //方法1：有效区
    //方法2：垃圾区，这种方法比较好实现
    public int removeElement(int[] nums, int val) {
        int n=nums.length,r=n,l=0;//[r... 是垃圾区；  ... ,l)是有效区
        while(l<r){
            if(nums[l]==val)swap(nums,l,--r);
            else l++;
        }
        return r;
    }
    public void swap(int[] nums,int a,int b){
        int t=nums[a];
        nums[a]=nums[b];
        nums[b]=t;
    }
}
