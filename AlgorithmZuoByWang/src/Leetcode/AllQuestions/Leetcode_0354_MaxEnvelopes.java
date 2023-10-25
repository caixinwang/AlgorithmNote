package Leetcode.AllQuestions;

import java.util.Arrays;

public class Leetcode_0354_MaxEnvelopes {
    //第一个维度需要从小到大，第二维度从大到小。
    //第二维度从大到小因为要从客观上杜绝相同底的信封互套
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes,(a, b)-> a[0]!=b[0]?a[0]-b[0]:b[1]-a[1]);
        int n=envelopes.length;
        int[] nums=new int[n];
        for(int i=0;i<n;i++) nums[i]=envelopes[i][1];
        return lengthOfLIS(nums);
    }

    public int lengthOfLIS(int[] nums) {
        int n=nums.length,len=0;
        int[] ends=new int[n];
        for(int num:nums){
            int l=0,r=len-1,mid=0;
            while(l<=r){
                mid=l+(r-l>>1);
                if(ends[mid]>=num) r=mid-1;
                else l=mid+1;
            }
            if(l==len) len++;
            ends[l]=num;
        }
        return len;
    }
}
