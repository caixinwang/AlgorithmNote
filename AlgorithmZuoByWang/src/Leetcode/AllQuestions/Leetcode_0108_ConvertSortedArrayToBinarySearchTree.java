package Leetcode.AllQuestions;

public class Leetcode_0108_ConvertSortedArrayToBinarySearchTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int val) {
			this.val = val;
		}
	}

	public TreeNode sortedArrayToBST(int[] nums) {
		return f(nums,0,nums.length-1);
	}

	/**
	 * 用中点做头即可
	 * @param nums 严格递增
	 * @param start 开始
	 * @param end 结束
	 * @return 生成一个高度平衡的二叉树
	 */
	private TreeNode f(int[] nums, int start, int end) {
		if (start>end) return null;
		if (start==end) return new TreeNode(nums[start]);
		int mid=start+(end-start>>1);
		TreeNode head=new TreeNode(nums[mid]);
		head.left=f(nums,start,mid-1);
		head.right=f(nums,mid+1,end);
		return head;
	}


}
