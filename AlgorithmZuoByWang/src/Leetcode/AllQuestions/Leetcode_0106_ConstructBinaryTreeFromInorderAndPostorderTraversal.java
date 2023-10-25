package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

import java.util.HashMap;

public class Leetcode_0106_ConstructBinaryTreeFromInorderAndPostorderTraversal {
    HashMap<Integer,Integer> map=new HashMap<Integer,Integer>();
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int n=inorder.length;
        for(int i=0;i<n;i++){
            map.put(inorder[i],i);
        }
        return buildTree(inorder,0,n-1,postorder,0,n-1);
    }

    public TreeNode buildTree(int[] inorder,int s1,int e1, int[] postorder,int s2,int e2) {
        if(s1==e1) return new TreeNode(inorder[s1]);
        int R=map.get(postorder[e2]);
        TreeNode head=new TreeNode(postorder[e2]);
        if(R-1>=s1){
            head.left=buildTree(inorder,s1,R-1,postorder,s2,s2+(R-s1)-1);
        }
        if(R+1<=e1){
            head.right=buildTree(inorder,R+1,e1,postorder,s2+(R-s1),e2-1);
        }
        return head;
    }
}
