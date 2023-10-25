package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Leetcode_0144_PreorderTraversal {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans=new LinkedList<>();
        f(root,ans);
        return ans;
    }

    public void f(TreeNode head,List<Integer> ans){
        if(head==null) return;
        ans.add(head.val);
        f(head.left,ans);
        f(head.right,ans);
    }

    public List<Integer> preorderTraversal2(TreeNode root) {
        Stack<TreeNode> stack=new Stack<>();
        List<Integer> ans=new ArrayList<>();
        while(!stack.isEmpty()||root!=null){
            for (;root!=null;ans.add(root.val),stack.add(root),root=root.left);
            root=stack.pop();
            root=root.right;
        }
        return ans;
    }

    public List<Integer> preorderTraversal3(TreeNode root) {
        Stack<TreeNode> stack=new Stack<>();
        List<Integer> ans=new ArrayList<>();
        if (root!=null)stack.add(root);
        while(!stack.isEmpty()){
            root=stack.pop();
            ans.add(root.val);
            if (root.right!=null) stack.add(root.right);
            if (root.left!=null) stack.add(root.left);
        }
        return ans;
    }


}
