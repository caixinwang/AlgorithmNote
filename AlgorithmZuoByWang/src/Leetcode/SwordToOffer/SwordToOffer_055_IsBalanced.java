package Leetcode.SwordToOffer;

import Leetcode.LeetClass.TreeNode;

public class SwordToOffer_055_IsBalanced {
    public boolean isBalanced(TreeNode root) {
        if(root==null) return true;
        return isBalanced(root.left)&&isBalanced(root.right)
                &&Math.abs(h(root.left)-h(root.right))<2;
    }
    public int h(TreeNode root){
        if(root==null) return 0;
        return 1+Math.max(h(root.left),h(root.right));
    }
}
