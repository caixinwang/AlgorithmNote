package Leetcode.SwordToOffer;

import Leetcode.LeetClass.TreeNode;

public class SwordToOffer_028_IsSymmetric {
    //本质上就是在验证两棵树是不是镜像的。我们重新开一个函数，验证两棵树是不是镜像的。
    public boolean isSymmetric(TreeNode root) {
        if(root==null) return true;
        return f(root.left,root.right);
    }

    public boolean f(TreeNode root1,TreeNode root2){
        if(root1==null&&root2==null) return true;
        if(root1==null||root2==null) return false;
        return root1.val==root2.val&&f(root1.left,root2.right)&&f(root2.left,root1.right);
    }

}
