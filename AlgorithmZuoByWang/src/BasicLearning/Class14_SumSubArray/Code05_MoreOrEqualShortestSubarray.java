package BasicLearning.Class14_SumSubArray;

import TestUtils.ArrayUtil;

/**
 * 给你一个整数数组 nums 和一个整数 k ，找出 nums 中和至少为 k 的 最短非空子数组 ，
 * 并返回该子数组的长度。如果不存在这样的 子数组 ，返回 -1 。
 */
public class Code05_MoreOrEqualShortestSubarray {
    public static int shortestSubarray(int[] nums, int k) {
        int n=nums.length,ans=1<<30,h=0,t=-1;
        int[] dq=new int[n+1];//存的是下标
        long[] s=new long[n+1];//long 防止溢出
        for(int i=0;i<n;i++) s[i+1]=s[i]+nums[i];//填好前缀和 s数组，后面在s数组上维持递增的序列
        for(int i=0;i<=n;i++){
            while(h<=t&&s[i]-s[dq[h]]>=k) {//满足>=k 产生一个候选答案,队头就可以退出了,后面的长度肯定比这个长
                ans=Math.min(ans,i-dq[h++]);//注意这里产生一个候选答案之后队头就退出！
            }
            while(h<=t&&s[i]<s[dq[t]]) t--;//队尾在入队之前可能已经和若干个队头产生候选答案了，放心踢掉
            dq[++t]=i;
        }
        return ans==1<<30?-1:ans;
    }

    public static int correct(int[] nums,int k){//暴力
        int n=nums.length,ans=n+1;
        for(int l=0;l<n;l++){
            for (int r=l;r<n;r++){
                int sum=0;
                for (int i=l;i<=r;i++) sum+=nums[i];
                if (sum>=k) ans = Math.min(ans, r-l+1);
            }
        }
        return ans==n+1?-1:ans;
    }
    static ArrayUtil au=new ArrayUtil();

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            int[] nums=au.generateRandomArr(5,-10,10);
            int k=au.ran(-10,10);
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
