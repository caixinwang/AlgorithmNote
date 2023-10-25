package Leetcode.AllQuestions;


import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;

public class Leetcode_0236_LowestCommonAncestorOfBinaryTree {
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
	}

	class Info{
		boolean findA,findB;
		TreeNode ans;//最低公共祖先
		public Info(boolean findA,boolean findB,TreeNode ans){
			this.findA=findA;
			this.findB=findB;
			this.ans=ans;
		}
	}
	public TreeNode lowestCommonAncestor(TreeNode head, TreeNode a, TreeNode b) {
		return f(head,a,b).ans;
	}

	public Info f(TreeNode head,TreeNode a, TreeNode b){
		if (head==null) return new Info(false,false,null);
		Info l=f(head.left,a,b);
		Info r=f(head.right,a,b);
		boolean findA=l.findA||r.findA||head==a;
		boolean findB=l.findB||r.findB||head==b;
		TreeNode ans=l.ans!=null?l.ans:r.ans;
		if (l.ans==null&&r.ans==null&&findA&&findB){
			ans=head;
		}
		return new Info(findA,findB,ans);
	}

	public TreeNode lowestCommonAncestor2(TreeNode head, TreeNode a, TreeNode b) {
		HashMap<TreeNode,TreeNode> parentMap=new HashMap<>();
		parentMap.put(head,null);//头的父亲是null
		getParentMap(head,parentMap);
		HashSet<TreeNode> set=new HashSet<>();
		while(a!=null){
			set.add(a);
			a=parentMap.get(a);
		}
		while(!set.contains(b)&&b!=null){
			b= parentMap.get(b);
		}
		return b;
	}

	public void getParentMap(TreeNode head, HashMap<TreeNode,TreeNode> parentMap){
		if (head==null) return;
		if (head.left!=null) parentMap.put(head.left,head);
		if (head.right!=null) parentMap.put(head.right,head);
		getParentMap(head.left,parentMap);
		getParentMap(head.right,parentMap);
	}

	//分类讨论，如果找到了a或者b直接往上返回。
	public TreeNode lowestCommonAncestor3(TreeNode head, TreeNode a, TreeNode b) {
		if (head==null||head==a||head==b) return head;
		TreeNode left=lowestCommonAncestor3(head.left,a,b);
		TreeNode right=lowestCommonAncestor3(head.right,a,b);
		if (left!=null&&right!=null) return head;
		if (left!=null) return left;
		return right;
	}


}
