package Leetcode.AllQuestions;

import java.util.LinkedList;
import java.util.List;

public class Leetcode_0046_Permutations {
	/**
	 * @param nums 不含重复数字
	 * @return 返回所有可能的全排列
	 */
	public List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> res=new LinkedList<>();
		process(nums,0,new int[nums.length],new boolean[nums.length],res);
		return res;
	}

	private void process(int[] nums, int index,int[] path,boolean[] choose, List<List<Integer>> res) {
		if (index==nums.length) {
			List<Integer> list=new LinkedList<>();
			for (int i = 0; i < path.length; i++) {
				list.add(path[i]);
			}
			res.add(list);
			return;
		}
		for (int i = 0; i < nums.length; i++) {//path[index]放nums[index~N-1],没选过的才选
			if (!choose[i]){
				choose[i]=true;
				path[index]=nums[i];
				process(nums,index+1,path,choose,res);
				choose[i]=false;
			}

		}
	}

	/**
	 *
	 * @param nums 不含重复数字
	 * @return 返回所有可能的全排列
	 */
	public List<List<Integer>> permute2(int[] nums) {
		List<List<Integer>> res=new LinkedList<>();
		process2(nums,0,res);
		return res;
	}

	private void process2(int[] nums, int index,List<List<Integer>> res) {
		if (index== nums.length){
			List<Integer> list=new LinkedList<>();
			for (int i = 0; i < nums.length; i++) {
				list.add(nums[i]);
			}
			res.add(list);
			return;
		}
		for (int i=index;i<nums.length;i++){
			swap(nums,index,i);
			process2(nums,index+1,res);
			swap(nums,index,i);
		}
	}

	private static void swap (int[]arr,int a,int b){
		int t=arr[a];
		arr[a]=arr[b];
		arr[b]=t;
	}

}
