package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

public class Leetcode_0156_BinaryTreeUpsideDown {
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if(root==null||(root.left==null&&root.right==null)) return root;
        TreeNode nr=upsideDownBinaryTree(root.left);
        root.left.left=root.right;//三角转化
        root.left.right=root;
        root.left=root.right=null;//自己变成了叶子，指针置空
        return nr;
    }
}
