package Leetcode.AllQuestions;

public class Leetcode_2560_MinCapability {
    public int minCapability(int[] nums, int k) {
        int l=1,r=0,mid;
        for(int num:nums) r=max(num,r);
        while(l<=r){
            mid=l+(r-l>>1);
            if(can2(nums,k,mid)) r=mid-1;
            else l=mid+1;
        }
        return l;
    }

    //dp[i][j]表示在0~i中偷，i号房间状态为j，0没被偷、1被偷 ，所能偷的最多的房间数
    public boolean can(int[] nums,int k,int ability){
        int n=nums.length;
        int[][] dp=new int[n][2];
        dp[0][1]=ability>=nums[0]?1:0;//能力值大于等于这个房间的钱数才能偷
        for(int i=1;i<n;i++){
            dp[i][0]=max(dp[i-1][0],dp[i-1][1]);
            dp[i][1]=dp[i-1][0]+(ability>=nums[i]?1:0);
        }
        return max(dp[n-1][0],dp[n-1][1])>=k;
    }

    public boolean can2(int[] nums,int k,int ability){//不动态规划，能抢就抢
        int pre=-2;//上一次抢的位置
        for(int i=0;i<nums.length;i++){
            if(ability>=nums[i]&&i-pre>1) {
                pre=i;
                if(--k==0) return true;
            }
        }
        return false;
    }

    public int max(int a,int b){return a>b?a:b;}
}
