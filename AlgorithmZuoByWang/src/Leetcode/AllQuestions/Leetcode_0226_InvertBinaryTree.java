package Leetcode.AllQuestions;

public class Leetcode_0226_InvertBinaryTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    public TreeNode invertTree(TreeNode root) {
        if (root==null) return null;
        TreeNode lchild=invertTree(root.left);
        TreeNode rchild=invertTree(root.right);
        root.left=rchild;
        root.right=lchild;
        return root;
    }
}
