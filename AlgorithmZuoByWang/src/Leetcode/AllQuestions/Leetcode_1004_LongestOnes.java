package Leetcode.AllQuestions;

public class Leetcode_1004_LongestOnes {
    //直接使用窗口，扩到不能阔才换头,不能阔的情况是：cnt==k并且下一个进来的是0
    public int longestOnes(int[] nums, int k) {
        int n=nums.length,l,r,cnt=0,ans=0;
        for(l=r=0;l<n;){
            while(r<n&&(nums[r]==1||cnt<k)) if(nums[r++]==0) cnt++;//一直阔
            if(r-l>ans) ans=r-l;
            if(nums[l++]==0) cnt--;
        }
        return ans;
    }

    //二分找答案
    public int longestOnes2(int[] nums, int k) {
        int n=nums.length,l=0,r=n,mid;
        while(l<=r){
            mid=l+(r-l>>1);
            if(can(nums,k,mid)) l=mid+1;
            else r=mid-1;
        }
        return r;
    }

    //假设最大可以有win个，也就是窗口的大小;窗口里面只要0的个数小于等于k就是成功了
    public boolean can(int[] nums,int k,int win){
        int n=nums.length,l,r;
        int cnt=0;//记录0的个数
        for(l=r=0;l+win-1<n;){
            while(r<l+win) if(nums[r++]==0) cnt++;
            if(cnt<=k) return true;
            if(nums[l++]==0) cnt--;
        }
        return false;
    }
}
