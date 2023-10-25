package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

public class Leetcode_0111_MinimumDepthOfBinaryTree {
    public int minDepth(TreeNode root) {//根据题意，空节点不算叶子结点，如果孩子为空，说明没有路
        if(root==null) return 0;
        if(root.left==null) return 1+minDepth(root.right);
        if(root.right==null) return 1+minDepth(root.left);
        return 1+Math.min(minDepth(root.right),minDepth(root.left));
    }
}
