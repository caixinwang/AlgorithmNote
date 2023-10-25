package Leetcode.SwordToOffer;

import Leetcode.LeetClass.TreeNode;

public class SwordToOffer_068_LowestCommonAncestor {
    //后序遍历抓p、q。同时抓到了说明当前结点就是最近的公共祖先。
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root==p||root==q||root==null) return root;
        TreeNode p1=lowestCommonAncestor(root.left,p,q);
        TreeNode p2=lowestCommonAncestor(root.right,p,q);
        if(p1!=null&&p2!=null) return root;
        return p1!=null?p1:p2;
    }
}
