package Leetcode.AllQuestions;

public class Leetcode_0198_HouseRobber {//结合213题
	public int rob(int[] nums) {//可以做空间压缩
		if (nums==null||nums.length==0) return -1;
		if (nums.length==1) return nums[0];
		if (nums.length==2) return Math.max(nums[0],nums[1]);
		int[] dp=new int[nums.length];//0~i 总体的最大值，不要求i结尾
		dp[0]=nums[0];
		dp[1]=Math.max(nums[0],nums[1]);
		for (int i=2;i<nums.length;i++){
			dp[i]=Math.max(dp[i-1],nums[i]+dp[i-2]);
		}
		return dp[nums.length-1];
	}

	public int rob2(int[] nums) {//可以做空间压缩
		if (nums==null||nums.length==0) return -1;
		if (nums.length==1) return nums[0];
		if (nums.length==2) return Math.max(nums[0],nums[1]);
		int ans=0;
		int pre2=nums[0];
		int pre1=Math.max(nums[0],nums[1]);
		for (int i=2;i<nums.length;i++){
			ans=Math.max(pre1,nums[i]+pre2);
			pre2=pre1;
			pre1=ans;
		}
		return ans;
	}

	public int rob2(int[] nums,int l,int r) {
		if (nums==null||r-l+1==0) return -1;
		if (r-l+1==1) return nums[l];
		if (r-l+1==2) return Math.max(nums[l],nums[l+1]);
		int ans=0;
		int pre2=nums[l];
		int pre1=Math.max(nums[l],nums[l+1]);
		for (int i=l+2;i<=r;i++){
			ans=Math.max(pre1,nums[i]+pre2);
			pre2=pre1;
			pre1=ans;
		}
		return ans;
	}


}
