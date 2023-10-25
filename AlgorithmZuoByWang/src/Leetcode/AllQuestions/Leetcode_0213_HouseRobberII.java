package Leetcode.AllQuestions;

public class Leetcode_0213_HouseRobberII {//pair with 198/337

	/**
	 * 利用198的解法。但是加了点可能性分析。面对第一个房间和最后一个房间，有四种情况。其中两个都被偷的可能性为0。
	 * 所以只剩下三种情况。这三种情况的最大值可以用0~N-2和1~N-1来覆盖。
	 */
	public static int rob(int[] nums) {
		if (nums==null||nums.length==0) return -1;
		if (nums.length==1) return nums[0];
		if (nums.length==2) return Math.max(nums[0],nums[1]);
		return Math.max(rob(nums,0,nums.length-2),rob(nums,1,nums.length-1));
	}

	public static int rob(int[] nums,int l,int r) {
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
