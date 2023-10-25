package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

public class Leetcode_0699_TrimBST {
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root==null) return null;
        if (root.val<low) return trimBST(root.right,low,high);
        if (root.val>high) return trimBST(root.left,low,high);
        root.left=trimBST(root.left,low,high);
        root.right=trimBST(root.right,low,high);
        return root;
    }

    //其实就是先把根节点调整好，卡一个左边界，卡一个右边界下去
    //具体做法就是站在调整好的根节点上看，把左子树的根节点调整好了才跳下去。
    public TreeNode trimBST2(TreeNode root, int low, int high) {
        while(root!=null&&(root.val<low||root.val>high)){//把根节点换对
            root=root.val<low?root.right: root.left;
        }
        if (root==null) return null;
        for (TreeNode c=root;c.left!=null;){
            if (c.left.val<low) c.left=c.left.right;
            else c=c.left;
        }
        for (TreeNode c=root;c.right!=null;){
            if (c.right.val>high) c.right=c.right.left;
            else c=c.right;
        }
        return root;
    }
}
