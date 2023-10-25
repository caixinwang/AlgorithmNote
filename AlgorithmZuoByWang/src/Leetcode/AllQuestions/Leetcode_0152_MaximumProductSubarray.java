package Leetcode.AllQuestions;

public class Leetcode_0152_MaximumProductSubarray {

	/**
	 * 可能性划分：1.只有nums[i]自己 2.有num[i]以及nums[i-1]之前的数  两种情况求最大值
	 * 分情况讨论，nums[i]小于0的时候，需要乘一个小的。nums[i]大于0的时候需要乘一个大的
	 * 那么除了有一个提供大的值的dp以外还需要一个提供小的dp，所以这题需要有两个dp相辅相成
	 * @param nums 有正有负，返回子数组的最大值乘积
	 * @return -
	 */
	public static int maxProduct(int[] nums) {//可以再去试试空间压缩
		int[] dpmx=new int[nums.length];//必须以i结尾的最大子数组乘积
		int[] dpmn=new int[nums.length];//必须以i结尾的最小子数组乘积
		dpmx[0]=nums[0];
		dpmn[0]=nums[0];
		for (int i=1;i< nums.length;i++){
//			dpmx[i]=nums[i]>=0?Math.max(dpmx[i-1]*nums[i],nums[i]):Math.max(dpmn[i-1]*nums[i],nums[i]);
//			dpmn[i]=nums[i]<=0?Math.min(dpmx[i-1]*nums[i],nums[i]):Math.min(dpmn[i-1]*nums[i],nums[i]);
			dpmx[i]=Math.max(dpmx[i-1]*nums[i],Math.max(nums[i],dpmn[i-1]*nums[i]));
			dpmn[i]=Math.min(Math.min(dpmx[i-1]*nums[i],nums[i]),dpmn[i-1]*nums[i]);
		}
		int ans=Integer.MIN_VALUE;
		for (int i : dpmx) {
			ans = Math.max(ans, i);
		}
		return ans;
	}

}
