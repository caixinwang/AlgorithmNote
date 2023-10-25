package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Leetcode_0015_3Sum {

	/**
	 * 我们利用二元组问题来做。需要先把nums排序，然后调用二元组问题。但是要注意，为了效率，避免头插，所以我们从后往前开始固定
	 * @param nums 并非有序数组，返回nums数组中和为0的三元组。
	 * @return -
	 */
	public static List<List<Integer>> threeSum(int[] nums) {
		List<List<Integer>> res=new LinkedList<>();
		Arrays.sort(nums);
		for (int i=nums.length-1;i>=2;i--){//i>=2 至少前面能有一对二元组
			if (i+1<nums.length&&nums[i]==nums[i+1]) continue;
			int fix=nums[i];//固定
			List<List<Integer>> lists = twoSum(nums, 0, i -1, -fix);
			if (lists.size()==0) continue;
			for (List<Integer> list : lists) {
				list.add(fix);
				res.add(list);
			}
		}
		return res;
	}

	/**
	 *
	 * @param nums 在有序数组nums的[left,right]范围上找所有的累加和为k的二元组
	 * @param k 对二元组的要求是累加和为k
	 * @return 找到所有累加和为k的二元组返回。这个二元组要求不重复。(x,y)是值而不是下标
	 */
	public static List<List<Integer>> twoSum(int[] nums,int left,int right,int k){
		List<List<Integer>> res=new LinkedList<>();
		int l=left,r= right;
		while(l<r){//撞上就没有二元组了
			if (nums[l]+nums[r]<k){
				while(++l<r&&nums[l]==nums[l-1]);//不等于前面了或者是和r撞上了
			}else if (nums[l]+nums[r]>k){
				while(l<--r&&nums[r]==nums[r+1]);//不等于后面了或者是和r撞上了
			}else {// ==
				List<Integer> list=new LinkedList<>();
				list.add(nums[l]);
				list.add(nums[r]);
				res.add(list);
				while(++l<r&&nums[l]==nums[l-1]);//不等于前面了或者是和r撞上了
				while(l<--r&&nums[r]==nums[r+1]);//不等于后面了或者是和r撞上了
			}
		}
		return res;
	}

	public List<List<Integer>> threeSum2(int[] nums) {
		Arrays.sort(nums);
		int i=0,n=nums.length;
		List<List<Integer>> ans=new ArrayList<List<Integer>>();
		while(i<n-2){
			int j=i+1,k=n-1;
			while(j<k){
				int s=nums[j]+nums[k];
				if(s==-nums[i]){
					ans.add(List.of(nums[i],nums[j],nums[k]));
					while(++j<k&&nums[j]==nums[j-1]);
					while(j<--k&&nums[k]==nums[k+1]);
				}else if(s<-nums[i]){
					while(++j<k&&nums[j]==nums[j-1]);
				}else{
					while(j<--k&&nums[k]==nums[k+1]);
				}
			}
			while(++i<n-2&&nums[i]==nums[i-1]);
		}
		return ans;
	}
}
