package Leetcode.AllQuestions;

import TestUtils.ArrayUtil;

public class Leetcode_0862_ShortestSubarrayII {
    public static int shortestSubarray(int[] nums, int k) {//累加和<=k的最短子数组
        int n=nums.length,h=0,t=-1,ans=n+1;
        int[] dq=new int[n+1];
        long[] s=new long[n+1];
        for(int i=0;i<n;i++) s[i+1]=s[i]+nums[i];
        for(int i=0;i<=n;i++){
            while(h<=t&&s[i]-s[dq[h]]<=k) {
                ans=Math.min(ans,i-dq[h++]);
            }
            while(h<=t&&s[i]>s[dq[t]]) t--;//要<=k，那肯定维持一个递减序列要好点，那么队头就是大的元素
            dq[++t]=i;
        }
        return ans==n+1?-1:ans;
    }

    public static int correct(int[] nums,int k){//暴力
        int n=nums.length,ans=n+1;
        for(int l=0;l<n;l++){
            for (int r=l;r<n;r++){
                int sum=0;
                for (int i=l;i<=r;i++) sum+=nums[i];
                if (sum<=k) ans = Math.min(ans, r-l+1);
            }
        }
        return ans==n+1?-1:ans;
    }
    static ArrayUtil au=new ArrayUtil();

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            int[] nums=au.generateRandomArr(5,-10,10);
            int k=au.ran(-50,50);
            int ans1=shortestSubarray(nums,k);
            int ans2=correct(nums,k);
            if (ans1!=ans2){
                au.printArr(nums);
                System.out.println("k = " + k);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("================");
                shortestSubarray(nums,k);
                correct(nums,k);
                break;
            }
        }
    }
}
