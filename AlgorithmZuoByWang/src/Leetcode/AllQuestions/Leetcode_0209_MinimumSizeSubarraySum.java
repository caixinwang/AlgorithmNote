package Leetcode.AllQuestions;

import java.util.HashMap;
import java.util.TreeMap;

public class Leetcode_0209_MinimumSizeSubarraySum {
    static int MIN=1<<31,MAX=MIN-1;
    public static int minSubArrayLen(int target, int[] nums) {
        int ans=MAX,n=nums.length,sum=0;
        for(int l=0,r=0;l<n;){//固定l，扩到满足条件就停止（换句话说，不满足条件就一直扩）
            while(sum<target&&r<n){
                sum+=nums[r++];
            }
            if(sum>=target&&r-l<ans) ans=r-l;
            sum-=nums[l++];
        }
        return ans==MAX?0:ans;
    }

    public static int minSubArrayLen2(int target, int[] nums) {
        int ans=MAX,n=nums.length,sum=0;
        for (int l=0,r=0;r<n;){//固定r，满足条件(sum>=target)的情况下能缩就缩
            sum+=nums[r++];//当前要固定的r进来
            while(l<n&&sum>=target&&sum-nums[l]>=target) sum-=nums[l++];//满足条件就一直缩小
            if (sum>=target&&r-l<ans) ans=r-l;
        }
        return ans==MAX?0:ans;
    }

    public static int minSubArrayLen3(int k, int[] arr) {//要求>=k使用有序表，题目要求短的，所以维持最晚出现的下标
        TreeMap<Integer,Integer> map=new TreeMap<>();//关心长度，维持下标位置
        map.put(0,-1);
        int ans=Integer.MAX_VALUE,sum=0;
        for(int i=0;i<arr.length;i++){
            sum+=arr[i];
            Integer find=map.floorKey(sum-k);//x~i <=> 0~i - 0~x-1  ==> 0~i - 0~x-1 >=k  ==>  0~x-1<=0~i -k
            if(find!=null) ans = Math.min(ans,i-map.get(find));;
            map.put(sum,i);//维持出现晚的位置，所以管它37=21，直接更新
        }
        return ans==Integer.MAX_VALUE?0:ans;
    }

}
