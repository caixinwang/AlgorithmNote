package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Leetcode_0078_Subsets {

	public static List<List<Integer>> subsets(int[] nums) {
		List<List<Integer>> ans=new ArrayList<>();
		for (int rest = 0; rest <= nums.length; rest++) {
			f(nums,0,rest,new LinkedList<Integer>(),ans);
		}
		return ans;
	}

	/**
	 *
	 * @param nums 数组
	 * @param index 从index往后严格选够rest个数
	 * @param rest -
	 * @param path 路径
	 * @param ans 选满了rest个的path加入ans中
	 */
	private static void f(int[] nums, int index, int rest, LinkedList<Integer> path,List<List<Integer>> ans) {
		if (rest==0){
			ans.add(new LinkedList<>(path));
			return;
		}
		if (index==nums.length){
			return;
		}
		f(nums,index+1,rest,path,ans);//不选index位置的数
		path.addLast(nums[index]);
		f(nums,index+1,rest-1,path,ans);//选了index位置的
		path.pollLast();
	}

	public static List<List<Integer>> subsets2(int[] nums) {
		List<List<Integer>> ans=new ArrayList<>();
		f2(nums,0,new LinkedList<Integer>(),ans);
		return ans;
	}

	/**
	 *
	 * @param nums 数组
	 * @param index 从index往后随意去选
	 * @param path 路径
	 * @param ans 选满了rest个的path加入ans中
	 */
	private static void f2(int[] nums, int index, LinkedList<Integer> path,List<List<Integer>> ans) {
		if (index==nums.length){
			ans.add(new LinkedList<>(path));
			return;
		}
		f2(nums,index+1,path,ans);//不选index位置的数
		path.addLast(nums[index]);
		f2(nums,index+1,path,ans);//选了index位置的
		path.pollLast();
	}


}
