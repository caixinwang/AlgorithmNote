package Leetcode.SwordToOffer;

import Leetcode.LeetClass.TreeNode;

public class SwordToOffer_027_MirrorTree {
    public TreeNode mirrorTree(TreeNode root) {
        return f(root);
    }
    public TreeNode f(TreeNode root){
        if(root==null) return null;
        TreeNode ans=new TreeNode(root.val);
        ans.left=f(root.right);
        ans.right=f(root.left);
        return ans;
    }
}
