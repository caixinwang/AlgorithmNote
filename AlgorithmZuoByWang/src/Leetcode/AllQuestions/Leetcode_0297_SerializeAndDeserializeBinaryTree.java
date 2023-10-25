package Leetcode.AllQuestions;

import java.util.LinkedList;
import java.util.Queue;

public class Leetcode_0297_SerializeAndDeserializeBinaryTree {

	/**
	 * 将内存里一棵树的结构变成一个字符串的结构，并且要没有歧义。序列化的方式有：层序、先序、后序。中序没有办法序列化。
	 * 怎么反序列化呢，其实就是在普通的层序遍历的过程中，加了一个连左右孩子的过程，左右孩子来自于序列化的字符串。
	 */


	// 提交代码时不要提交TreeNode类
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(int value) {
			val = value;
		}
	}

	public class Codec {

		// Encodes a tree to a single string.
		public String serialize(TreeNode root) {//层序遍历的方式序列化
			StringBuilder ans=new StringBuilder();
			if (root==null) return "null";
			Queue<TreeNode> queue=new LinkedList<>();
			queue.add(root);
			add(ans,root);
			TreeNode cur;
			while (!queue.isEmpty()){//其实就是层序遍历，但是打印时机不是在当前层，而是在上一层。
				cur=queue.poll();
				add(ans,cur.left);
				add(ans,cur.right);
				if (cur.left!=null) queue.add(cur.left);
				if (cur.right!=null) queue.add(cur.right);
			}
			return ans.toString();
		}

		private void add(StringBuilder ans,TreeNode node){
			if (ans.length()!=0) ans.append(",");
			ans.append(node==null?"null":node.val);
		}

		// Decodes your encoded data to tree.
		public TreeNode deserialize(String data) {
			if (data.equals("null")) return null;
			String[] nodes = data.split(",");
			int index=0;
			TreeNode head=convert(nodes[index++]);
			Queue<TreeNode> queue=new LinkedList<>();
			queue.add(head);
			TreeNode cur;
			while (!queue.isEmpty()){
				cur=queue.poll();
				cur.left=convert(nodes[index++]);
				cur.right=convert(nodes[index++]);
				if (cur.left!=null) queue.add(cur.left);
				if (cur.right!=null) queue.add(cur.right);
			}
			return head;
		}

		private TreeNode convert(String node){
			if (node.equals("null")) return null;
			return new TreeNode(Integer.parseInt(node));
		}
	}
}
