package Leetcode.AllQuestions;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Leetcode_0018_FourSum {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans=new LinkedList<>();
        int n=nums.length;
        if(n<4) return ans;
        Arrays.sort(nums);
        for(int i1=0;i1<n-3;i1++){
            if(i1!=0&&nums[i1]==nums[i1-1])continue;
            for(int i2=i1+1;i2<n-2;i2++){
                if(i2!=i1+1&&nums[i2-1]==nums[i2]) continue;
                int l=i2+1,r=n-1;
                long aim=(long)target-(nums[i1]+nums[i2]);
                while(l<r){
                    if(l!=i2+1&&nums[l-1]==nums[l]) {
                        l++;
                        continue;
                    }
                    if(r!=n-1&&nums[r+1]==nums[r]){
                        r--;
                        continue;
                    }
                    if((long)nums[l]+nums[r]<aim) l++;
                    else if((long)nums[l]+nums[r]>aim) r--;
                    else ans.add(List.of(nums[i1],nums[i2],nums[l++],nums[r]));
                }
            }
        }
        return ans;
    }
}
