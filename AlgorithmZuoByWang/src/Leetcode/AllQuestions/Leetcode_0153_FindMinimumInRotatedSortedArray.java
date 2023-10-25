package Leetcode.AllQuestions;

public class Leetcode_0153_FindMinimumInRotatedSortedArray {
    public static int findMin(int[] nums) {
        int l=0,r=nums.length-1;
        while(l<=r){
            int mid=l+(r-l>>1);
            if (nums[mid]<nums[0]) r=mid-1;//在下半区就往左动，此时r右边是可能为答案，l左边不可能为答案
            else l=mid+1;
        }
        return nums[l%nums.length];//最特殊的情况就是没有下半区，那么一直是l在动，此时最小值就是[0]
    }

    public static void main(String[] args) {
        int[] nums=new int[]{11,13,15,17};
        System.out.println(findMin(nums));
    }
}
