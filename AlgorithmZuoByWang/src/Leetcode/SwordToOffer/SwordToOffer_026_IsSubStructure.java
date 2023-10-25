package Leetcode.SwordToOffer;

import Leetcode.LeetClass.TreeNode;

public class SwordToOffer_026_IsSubStructure {

    public static boolean isSubStructure(TreeNode A, TreeNode B) {//这个函数本质是A的一个先序遍历
        if(A==null) return false;//要返回早就返回了
        if(B!=null&&g(A,B)) return true;
        return isSubStructure(A.left,B)||isSubStructure(A.right,B);
    }


    //调用需要保证B一开始不是空
    public static boolean g(TreeNode A,TreeNode B){//必须从A出发匹配B。本质就是B的先序遍历，在遍历的过程中和A进行比较
        if(B==null) return true;
        if(A==null) return false;
        return g(A.left,B.left)&&g(A.right,B.right)&&A.val==B.val;
    }

    public static void main(String[] args) {
        TreeNode head=new TreeNode(10);
        head.left=new TreeNode(12);
        head.right=new TreeNode(6);
        head.left.left=new TreeNode(8);
        head.left.right=new TreeNode(3);
        head.right.left=new TreeNode(11);

        TreeNode head2=new TreeNode(10);
        head2.left=new TreeNode(12);
        head2.right=new TreeNode(6);
        head2.left.left=new TreeNode(8);

        System.out.println(isSubStructure(head,head2));
    }


}
