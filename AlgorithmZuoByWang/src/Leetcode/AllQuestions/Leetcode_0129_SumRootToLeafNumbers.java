package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

public class Leetcode_0129_SumRootToLeafNumbers {
    int ans=0;
    public int sumNumbers(TreeNode root) {
        if(root==null) return 0;
        f(root,0);
        return ans;
    }

    public void f(TreeNode root,int pre){
        if(root==null) return;
        int sum=pre*10+root.val;
        if(root.left==null&&root.right==null) ans+=sum;//到叶子结点结算
        f(root.left,sum);
        f(root.right,sum);
    }

    public int sumNumbers2(TreeNode root) {
        if(root==null) return 0;
        return f2(root,0);
    }

    public int f2(TreeNode root,int pre){
        if(root==null) return 0;
        int sum=pre*10+root.val;
        if(root.left==null&&root.right==null) return sum;
        return f2(root.left,sum)+ f2(root.right,sum);

    }
}
