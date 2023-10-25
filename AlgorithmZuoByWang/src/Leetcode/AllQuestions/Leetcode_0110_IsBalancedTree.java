package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

public class Leetcode_0110_IsBalancedTree {
    public boolean isBalanced(TreeNode head) {//后序遍历，先判断左右是不是平衡的，然后判断头节点是不是平衡的
        if(head==null) return true;
        return isBalanced(head.left)&&isBalanced(head.right)
                &&(Math.abs(f(head.left)-f(head.right))<=1);
    }

    public int f(TreeNode head){
        if(head==null) return 0;
        return 1+Math.max(f(head.left),f(head.right));
    }

    public boolean isBalanced2(TreeNode head) {//在后续遍历的过程中直接用-1代表此时左右已经高度差超过1了
        return g(head)!=-1;
    }

    public int g(TreeNode head){//在计算数的深度的时候插入了两行代码
        if (head==null) return 0;
        int p1=g(head.left);
        int p2=g(head.right);
        if (p1==-1||p2==-1) return -1;//
        if (Math.abs(p1-p2)>1) return -1;//
        return 1+Math.max(p1,p2);
    }


}
