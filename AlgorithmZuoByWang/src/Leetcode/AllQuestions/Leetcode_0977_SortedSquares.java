package Leetcode.AllQuestions;

public class Leetcode_0977_SortedSquares {
    //双指针，最大的肯定在两边，从最大的开始填
    public int[] sortedSquares(int[] nums) {
        int n=nums.length,index=n-1,l=0,r=n-1,v1,v2;
        int[] ans=new int[n];
        while(l<=r){
            v1=nums[l]*nums[l];
            v2=nums[r]*nums[r];
            if(v1>v2){
                ans[index--]=v1;
                l++;
            }else{
                ans[index--]=v2;
                r--;
            }
        }
        return ans;
    }
}
