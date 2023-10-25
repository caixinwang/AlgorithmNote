package Leetcode.AllQuestions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Leetcode_0047_PermutationsII {


	/**
	 *
	 * @param nums nums数值可能重复，你给我返回全排列。由于nums中会重复，所以你需要去重了
	 * @return 返回全排列
	 */
	public List<List<Integer>> permuteUnique(int[] nums) {
		List<List<Integer>> res=new LinkedList<>();
		process(nums,0,new int[nums.length],new boolean[nums.length],res);
		return res;
	}

	private void process(int[] nums, int index, int[] path, boolean[] choose, List<List<Integer>> res) {
		if (index==nums.length) {
			List<Integer> list=new LinkedList<>();
			for (int i = 0; i < path.length; i++) {
				list.add(path[i]);
			}
			res.add(list);
			return;
		}
		HashSet<Integer> set=new HashSet<>();
		for (int i = 0; i < nums.length; i++) {//path[index]放nums[index~N-1],没选过的才选
			if (!choose[i]&&(!set.contains(nums[i]))){//相同值不放在相同位置
				set.add(nums[i]);
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
	public List<List<Integer>> permuteUnique2(int[] nums) {
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
		HashSet<Integer> set=new HashSet<>();
		for (int i=index;i<nums.length;i++){
			if (!set.contains(nums[i])){
				set.add(nums[i]);
				swap(nums,index,i);
				process2(nums,index+1,res);
				swap(nums,index,i);
			}
		}
	}

	private static void swap (int[]arr,int a,int b){
		int t=arr[a];
		arr[a]=arr[b];
		arr[b]=t;
	}
}
