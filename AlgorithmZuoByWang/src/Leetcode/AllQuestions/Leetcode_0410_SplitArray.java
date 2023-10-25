package Leetcode.AllQuestions;

public class Leetcode_0410_SplitArray {
    //二分答案，设计一个函数看看让各块的最大和<=x,k块够不够
    public int splitArray(int[] nums, int k) {
        long l=0,r=0,mid;
        for(int num:nums) r+=num;
        while(l<=r){
            mid=l+(r-l>>1);
            if(can(nums,mid,k)) r=mid-1;
            else l=mid+1;
        }
        return (int)l;
    }

    public boolean can(int[] nums,long x,long k){
        long sum=0,need=1,n=nums.length;
        for(int i=0;i<n;i++){
            if(nums[i]>x) return false;
            if(sum+nums[i]>x){
                sum=nums[i];//新开一块
                need++;
            }else {
                sum+=nums[i];
            }
        }
        return k>=need;
    }
}
