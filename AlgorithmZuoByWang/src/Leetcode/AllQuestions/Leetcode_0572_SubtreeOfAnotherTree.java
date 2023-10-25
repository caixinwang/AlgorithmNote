package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

public class Leetcode_0572_SubtreeOfAnotherTree {
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {//root的一个先序遍历，在每一个root结点上检查
        if (root==null) return false;
        if (f(root,subRoot)) return true;
        return isSubtree(root.left,subRoot)||isSubtree(root.right,subRoot);
    }
    public boolean f(TreeNode root,TreeNode subRoot){//subRoot的一个先序遍历
        if (subRoot==null) return root==null;
        if (root==null||root.val!=subRoot.val) return false;
        return f(root.left, subRoot.left)&&f(root.right, subRoot.right);
    }
}
