package Leetcode.AllQuestions;

import java.util.*;

/**
 * 参考Class04_Tree_求树的最大宽度的解法
 * 普通的宽度优先遍历只要求顺序，只使用一个队列就够了。但是这一题其实需要你分出层来，所以你需要有一个机制来做这件事情。
 * 第一种方法：每一轮记录一下队列的长度，一次性弹出一整个记录的长度。例如一开始只有root，那么就一次性弹出1个，然后把
 * 弹出的结点的左右孩子继续进队列，然后现在假设有2的长度，那么再一次性弹出2个结点，然后把他们的左右孩子也入队。
 * 第二种方法：利用两个指针来记录当前层的最后一个结点以及下一层的最后一个结点，这样你就知道弹出的cur是不是当前层的最后一个，
 * 这样就可以结算了。
 *
 *  重要技巧！！！宽度优先遍历可以不一个一个来，可以一次干一批
 */
public class Leetcode_0102_BinaryTreeLevelOrderTraversal {
	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
	}

	public List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> ans=new ArrayList<>();
		if (root==null) return ans;
		Queue<TreeNode> queue=new LinkedList<>();
		queue.add(root);
		TreeNode cur=null;
		while (!queue.isEmpty()){
			List<Integer> list=new ArrayList<>();
			int nums= queue.size();
			for (int i = 0; i < nums; i++) {
				cur = queue.poll();
				list.add(cur.val);
				if (cur.left!=null) queue.add(cur.left);
				if (cur.right!=null) queue.add(cur.right);
			}
			ans.add(list);
		}
		return ans;
	}

	public List<List<Integer>> levelOrder2(TreeNode root) {
		List<List<Integer>> ans=new ArrayList<>();
		if (root==null) return ans;
		Queue<TreeNode> queue=new LinkedList<>();
		queue.add(root);
		TreeNode cur=null;
		TreeNode last=root;
		TreeNode next=null;
		List<Integer> list=new ArrayList<>();
		while (!queue.isEmpty()){
			cur=queue.poll();
			if (cur.left!=null){
				queue.add(cur.left);
				next=cur.left;//更新
			}
			if (cur.right!=null){
				queue.add(cur.right);
				next=cur.right;//更新
			}
			list.add(cur.val);
			if (cur==last){//放在next更新之后，因为要用到
				ans.add(list);
				list=new ArrayList<>();//需要搞一个新的
				last=next;//更新
			}
		}
		return ans;
	}


}
