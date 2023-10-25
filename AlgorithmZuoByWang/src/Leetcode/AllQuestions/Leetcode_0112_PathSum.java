package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

public class Leetcode_0112_PathSum {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root==null) return false;
        return f(root,0,targetSum);
    }

    public boolean f(TreeNode head,int presum,int targetSum){
        if(head==null) return false;
        int sum=presum+head.val;
        if(head.left==null&&head.right==null&&sum==targetSum) return true;//到了叶子结点才去看
        return f(head.left,sum,targetSum)||f(head.right,sum,targetSum);
    }

}
