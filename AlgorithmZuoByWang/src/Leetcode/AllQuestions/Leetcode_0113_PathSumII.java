package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

import java.util.LinkedList;
import java.util.List;

public class Leetcode_0113_PathSumII {
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> ans=new LinkedList<>();
        if(root==null) return ans;
        f(root,0,targetSum,new LinkedList<>(),ans);
        return ans;
    }


    public void f(TreeNode head,int presum,int targetSum,
                  LinkedList<Integer> path,List<List<Integer>> ans){
        if(head==null) return;
        int sum=presum+head.val;
        path.addLast(head.val);
        if(head.left==null&&head.right==null&&sum==targetSum) {
            ans.add(new LinkedList<>(path));
        }
        f(head.left,sum,targetSum,path,ans);
        f(head.right,sum,targetSum,path,ans);
        path.pollLast();
    }
}
