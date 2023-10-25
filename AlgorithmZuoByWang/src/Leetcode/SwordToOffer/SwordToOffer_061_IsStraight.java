package Leetcode.SwordToOffer;

import java.util.Arrays;

public class SwordToOffer_061_IsStraight {
    //排序，求出大小王的数量。从第一个不是0的位置开始遍历，如果重复就直接不合法，否则利用大小王来填补和后一张之间的差距，填补不了就false
    public boolean isStraight(int[] nums) {
        Arrays.sort(nums);
        int cnt=0,i;
        for(i=0;i<nums.length&&nums[i]==0;i++) cnt++;
        for(;i<nums.length-1;i++){
            if(nums[i]==nums[i+1]) return false;
            if(nums[i+1]-nums[i]>=2){
                cnt-=nums[i+1]-nums[i]-1;
                if (cnt<0) return false;
            }
        }
        return true;
    }
}
