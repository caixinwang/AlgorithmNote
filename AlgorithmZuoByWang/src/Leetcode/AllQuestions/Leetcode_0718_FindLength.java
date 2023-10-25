package Leetcode.AllQuestions;

public class Leetcode_0718_FindLength {
    //最后一个位置nums1[i]和nums[j]必须匹配的dp
    public int findLength(int[] nums1, int[] nums2) {
        int n=nums1.length,m=nums2.length,ans=0;
        int[][] dp=new int[n+1][m+1];//[i][j]代表nums1前i个元素和nums2前j个元素的最长重复子数组,[i][j]匹配
        for(int i=1;i<=n;i++){
            for(int j=1;j<=m;j++){
                if(nums1[i-1]==nums2[j-1]) ans=Math.max(ans,dp[i][j]=dp[i-1][j-1]+1);
            }
        }
        return ans;
    }

    public int findLength2(int[] nums1, int[] nums2) {
        int n=nums1.length,m=nums2.length,ans=0;
        for(int i=0;i<n;i++)ans=max(ans,f(nums1,i,nums2));//固定nums1，nums2移动
        for(int j=0;j<m;j++)ans=max(ans,f(nums2,j,nums1));//nums1移动
        return ans;
    }

    //offset为nums1开始的下标 和 nums2和0下标对齐
    public int f(int[] nums1,int offset, int[] nums2){
        int ans=0,cnt=0;
        for(int i=offset,j=0;i<nums1.length&&j<nums2.length;i++,j++){
            if(nums1[i]==nums2[j]) cnt++;
            else cnt=0;
            if(cnt>ans) ans=cnt;
        }
        return ans;
    }
    public int max(int a,int b){return a>b?a:b;}
}
