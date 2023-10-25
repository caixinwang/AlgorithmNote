package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

public class Leetcode_2331_EvaluateTree {
    //本质就是一个后序遍历
    public boolean evaluateTree(TreeNode root) {
        if(root.left==null&&root.right==null) return root.val==1;
        boolean p1=evaluateTree(root.left);
        boolean p2=evaluateTree(root.right);
        return root.val==2?p1||p2:p1&&p2;
    }
}
