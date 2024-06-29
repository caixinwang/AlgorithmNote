package Leetcode.AllQuestions;

import java.util.LinkedList;

public class    Leetcode_0239_SlidingWindowMaximum {
    public int[] maxSlidingWindow(int[] nums, int k) {
        LinkedList<Integer> queue=new LinkedList<>();
        int n=nums.length,l=0,r=0,index=0;
        int[] ans=new int[n-k+1];
        for(;r<k;r++){
            while(!queue.isEmpty()&&nums[r]>=nums[queue.peekLast()]) queue.pollLast();
            queue.addLast(r);
        }
        ans[index++]=nums[queue.peekFirst()];
        for(;r<n;r++){
            while(!queue.isEmpty()&&nums[r]>=nums[queue.peekLast()]) queue.pollLast();
            queue.addLast(r);
            if(queue.peekFirst()==l++)queue.pollFirst();
            ans[index++]=nums[queue.peekFirst()];
        }
        return ans;
    }
    public int[] maxSlidingWindow2(int[] nums, int k) {
        int n=nums.length,h=0,t=-1;
        int[] ans=new int[n-k+1];
        int[] dq=new int[n];
        for(int l=0,r=0,index=0;l+k-1<n;){
            while(r<l+k){
                while(h<=t&&nums[r]>nums[dq[t]]) t--;
                dq[++t]=r++;
            }
            ans[index++]=nums[dq[h]];
            if(dq[h]==l++) h++;
        }
        return ans;
    }

    public int[] maxSlidingWindow3(int[] nums, int k) {
        int n=nums.length,h=0,t=-1;
        int[] ans=new int[n-k+1],dq=new int[n];
        for(int l=0,r=0,index=0;l+k-1<n;r++){
            while(h<=t&&nums[r]>nums[dq[t]]) t--;
            dq[++t]=r;
            if(r-l+1==k) {//窗口大小达到k
                ans[index++]=nums[dq[h]];//收集答案
                if(dq[h]==l++) h++;//缩窗口
            }

        }
        return ans;
    }
}
