package Leetcode.AllQuestions;

import java.util.Stack;

public class Leetcode_0098_ValidateBinarySearchTree {

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	public boolean isValidBST(TreeNode root) {//morris中序遍历
		if (root==null) return true;
		TreeNode cur=root;
		TreeNode mr=null;
		TreeNode pre=null;
		while(cur!=null){
			mr=cur.left;
			if (mr!=null){
				for (;mr.right!=null&&mr.right!=cur;mr=mr.right);
				if (mr.right==null){//mr.right==null,第一次来到cur结点
					mr.right=cur;
					cur=cur.left;
					continue;
				}else {//mr.right指向了cur，也就是第二次来到了
					mr.right=null;
				}
			}
			if (pre!=null&&pre.val>=cur.val) return false;
			pre=cur;
			cur=cur.right;
		}
		return true;
	}

	public boolean isValidBST2(TreeNode head) {//用栈的中序遍历
		if (head==null) return true;
		Stack<TreeNode> stack=new Stack<>();
		TreeNode pre=null;//记录结点的前驱
		while(head!=null||!stack.isEmpty()){
			while(head!=null){
				stack.push(head);
				head= head.left;
			}
			head= stack.pop();
			if (pre!=null&&head.val<=pre.val) return false;//注意判空
			pre=head;
			head=head.right;
		}
		return true;
	}

	public boolean isValidBST3(TreeNode head) {//使用先序遍历验证，需要传入这结点需要满足的区间(left,right)，闭区间
		return f(head,Long.MIN_VALUE,Long.MAX_VALUE);
	}

	//当前结点head.val要满足在上层传进来的[min,max]之间
	public boolean f (TreeNode head,long left,long right){//防止越界
		if (head==null) return true;
		return head.val>left&&head.val<right&&f(head.left,left,head.val)&&f(head.right,head.val,right);
	}

	public boolean isValidBST4(TreeNode head) {//使用后序遍历验证，后序就是从底向上的返回区间
		return g(head)[0]!=Long.MIN_VALUE;
	}

	//后序遍历左树给一个区间，右树给一个区间，head.val要大于左树的最大值，小于右树的最小值
	public long[] g (TreeNode head){//防止越界
		if (head==null) return new long[]{Long.MAX_VALUE,Long.MIN_VALUE};//注意这里是+00到-00，不会干扰答案
		long[] p1=g(head.left);
		long[] p2=g(head.right);
		if (head.val>p1[1]&&head.val<p2[0]) return new long[]{Math.min(p1[0],head.val),Math.max(p2[1],head.val)};//base case使然
		return new long[]{Long.MIN_VALUE,Long.MAX_VALUE};
	}

}
