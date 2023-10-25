package Leetcode.AllQuestions;

public class Leetcode_0035_SearchInsertPosition {
    //找大于等于target最右的位置
    public int searchInsert(int[] nums, int target) {
        int l=0,r=nums.length-1;
        while(l<=r){
            int mid=(l+r)/2;
            if(nums[mid]>=target){
                r=mid-1;
            }else{
                l=mid+1;
            }
        }
        return l;
    }
}

