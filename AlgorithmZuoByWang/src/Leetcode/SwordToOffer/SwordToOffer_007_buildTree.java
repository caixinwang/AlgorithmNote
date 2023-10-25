package Leetcode.SwordToOffer;

import Leetcode.LeetClass.TreeNode;

import java.util.HashMap;

public class SwordToOffer_007_buildTree {
    HashMap<Integer,Integer> map=new HashMap<>();
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length==0) return null;
        int n= preorder.length;
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i],i);
        }
        return buildTree(preorder,0,n-1,inorder,0,n-1);
    }
    public TreeNode buildTree(int[] preorder,int s1,int e1 ,int[] inorder,int s2,int e2){
        if (s1==e1) return new TreeNode(preorder[s1]);
        if (s1>e1||s2>e2) return null;
        int rt=map.get(preorder[s1]),len=rt-s2;
        TreeNode head=new TreeNode(preorder[s1]);
        head.left=buildTree(preorder,s1+1,s1+len,inorder,s2,rt-1);
        head.right=buildTree(preorder,s1+len+1,e1,inorder,rt+1,e2);
        return head;
    }

}
