package Leetcode.AllQuestions;

import java.util.HashMap;

public class Leetcode_0105_ConstructBinaryTreeFromPreorderAndInorderTraversal {
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode() {}
		TreeNode(int val) { this.val = val; }
		TreeNode(int val, TreeNode left, TreeNode right) {
			this.val = val;
			this.left = left;
			this.right = right;
		}
	}

	public TreeNode buildTree(int[] preorder, int[] inorder) {
		HashMap<Integer,Integer> map=new HashMap<>();//找一个值在inorder里面的哪个位置 省掉二分。
		for (int i = 0; i < inorder.length; i++) {
			map.put(inorder[i],i);
		}
		return f(preorder,inorder,0,preorder.length-1,0,inorder.length-1,map);
	}

	private TreeNode f(int[] preorder, int[] inorder, int ps, int pe, int is, int ie, HashMap<Integer, Integer> map) {
		if (ps>pe) return null;//这个要记得，两个结点的范围会调用出一个null。
		if (ps==pe) return new TreeNode(preorder[ps]);
		TreeNode head=new TreeNode(preorder[ps]);
		int index=map.get(preorder[ps]);
		int len=index-is;//左树结点数
		head.left=f(preorder,inorder,ps+1,ps+len,is,index-1,map);
		head.right=f(preorder,inorder,ps+len+1,pe,index+1,ie,map);
		return head;
	}

}
