package Leetcode.AllQuestions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Leetcode_0103_BinaryTreeZigzagLevelOrderTraversal {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {//改list
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode cur;
        boolean gol = true;//控制list
        while (!queue.isEmpty()) {
            int nodeNums = queue.size();
            LinkedList<Integer> list = new LinkedList<>();
            for (int i = 0; i < nodeNums; i++) {
                cur = queue.poll();
                if (cur.left != null) queue.add(cur.left);
                if (cur.right != null) queue.add(cur.right);
                if (gol) list.addLast(cur.val);
                else list.addFirst(cur.val);
            }
			ans.add(list);
            gol ^= true;//正着加，倒着加，轮着来
        }
        return ans;
    }

	public List<List<Integer>> zigzagLevelOrder2(TreeNode root) {//改队列
		List<List<Integer>> ans = new ArrayList<>();
		if (root == null) return ans;
		LinkedList<TreeNode> queue = new LinkedList<>();
		queue.addLast(root);
		TreeNode cur;
		boolean pollHead = true;
		while (!queue.isEmpty()) {
			int nodeNums = queue.size();
			List<Integer> list = new LinkedList<>();
			for (int i = 0; i < nodeNums; i++) {
				if (pollHead){//cur从头出，孩子就从尾巴进
					cur=queue.pollFirst();
					if (cur.left != null) queue.addLast(cur.left);
					if (cur.right != null) queue.addLast(cur.right);
					list.add(cur.val);
				}else {
					cur=queue.pollLast();
					if (cur.right != null) queue.addFirst(cur.right);//先加右再加左
					if (cur.left != null) queue.addFirst(cur.left);
					list.add(cur.val);
				}
			}
			ans.add(list);
			pollHead ^= true;
		}
		return ans;
	}
}
