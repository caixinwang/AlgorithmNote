package Leetcode.SwordToOffer;

import java.util.HashSet;

public class SwordToOffer_003_FindRepeatNumber {
    //找重复用原地发派(映射关系：值->下标)。下标循环怼(映射关系：下标->下标)
    //缺失数字使用双指针
    public int findRepeatNumber(int[] nums) {//不使用额外数据结构，原地发牌
        for(int i=0;i<nums.length;i++){
            while(nums[i]!=i) {
                if(nums[nums[i]]==nums[i]) return nums[i];
                swap(nums,i,nums[i]);
            }
        }
        return -1;
    }

    public void swap(int[] arr,int a,int b){
        int t=arr[a];
        arr[a]=arr[b];
        arr[b]=t;
    }

    public int findRepeatNumber2(int[] nums) {
        HashSet<Integer> set=new HashSet<Integer>();
        for(int n: nums){
            if(set.contains(n)) return n;
            set.add(n);
        }
        return -1;
    }
}
