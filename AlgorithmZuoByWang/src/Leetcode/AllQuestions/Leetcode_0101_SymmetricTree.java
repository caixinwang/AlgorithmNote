package Leetcode.AllQuestions;

public class Leetcode_0101_SymmetricTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	public boolean isSymmetric(TreeNode root) {
		if (root==null) return true;
		return f(root.left,root.right);
	}

	public boolean f(TreeNode head1,TreeNode head2){
		if (head1==null&&head2==null) return true;
		if (head1==null||head2==null) return false;
		return head1.val==head2.val&&f(head1.left,head2.right)&&f(head1.right,head2.left);
	}

}
