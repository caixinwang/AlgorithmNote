package Leetcode.AllQuestions;


public class Leetcode_0230_KthSmallestElementInBST {//可看题解的另外两种做法

	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	public static int kthSmallest(TreeNode head, int k) {//morris
		if (head==null) return -1;
		TreeNode cur=head,mr=null;
		while(cur!=null){
			if (cur.left!=null){//有左树
				for (mr=cur.left;mr.right!=null&&mr.right!=cur;mr=mr.right);
				if (mr.right==null){//第一次来到cur结点
					mr.right=cur;
					cur=cur.left;
					continue;
				}else {//第二次来到cur结点
					mr.right=null;
				}
			}
			if (--k==0) return cur.val;
			cur=cur.right;
		}
		return -1;
	}

}
