package Leetcode.AllQuestions;

import java.util.HashMap;

public class Leetcode_0325_MinimumSizeSubarraySum {
    public int maxSubArrayLen(int[] arr, int k) {
        HashMap<Integer,Integer> map=new HashMap<>();//关心长度，维持下标位置
        map.put(0,-1);
        int ans=-1,sum=0;
        for(int i=0;i<arr.length;i++){
            sum+=arr[i];
            if(map.containsKey(sum-k)) ans=Math.max(ans,i-map.get(sum-k));
            if(!map.containsKey(sum)) map.put(sum,i);//题目要求的是最长的长度，所以我们维持最早出现的下标的位置
        }
        return ans==-1?0:ans;
    }
}
