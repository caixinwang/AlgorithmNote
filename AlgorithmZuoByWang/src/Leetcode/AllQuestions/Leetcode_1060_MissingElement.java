package Leetcode.AllQuestions;

public class Leetcode_1060_MissingElement {
    //分析：num[mid]-num[0]+1 为 nums[0]~num[mid] 的数值的个数，下标0~mid如果连续只能放mid+1个数值
    //     两个的差值就是缺失的数值的数量，二分找到差值<k的最右的位置，然后算一下还缺几个，加上即可
    //(num[mid]-num[0]+1)-(mid+1) < k，那么说明第k个缺失的数在右边
    //找到(num[mid]-num[0]+1)-(mid+1) < k 最右的位置，缺失的数必定大于nums[rightMost]
    public int missingElement(int[] nums, int k) {
        int n=nums.length,l=0,r=n-1,mid;
        while(l<=r){
            mid=l+(r-l>>1);
            if((nums[mid]-nums[0]+1)-(mid+1) < k) l=mid+1;
            else r=mid-1;
        }
        return nums[r]+k-((nums[r]-nums[0]+1)-(r+1));
    }
}
