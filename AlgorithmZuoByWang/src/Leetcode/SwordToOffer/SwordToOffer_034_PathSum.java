package Leetcode.SwordToOffer;

import Leetcode.LeetClass.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SwordToOffer_034_PathSum {
    //到叶子结点，这题比较捞
    public List<List<Integer>> pathSum(TreeNode root, int target) {
        List<List<Integer>> ans=new ArrayList<>();
        if(root==null) return ans;
        f(root,0,new LinkedList<>(),target,ans);
        return ans;
    }

    public void f(TreeNode root,int sum,
                  LinkedList<Integer> path,int target,List<List<Integer>> ans){
        if(root==null) return;
        if(root.left==null&&root.right==null) {//在先序遍历的基础上，到叶子结点结算
            path.addLast(root.val);
            if(sum+root.val==target) ans.add(new LinkedList<>(path));
            path.pollLast();
            return;
        }
        path.addLast(root.val);
        f(root.left,sum+root.val,path,target,ans);
        f(root.right,sum+root.val,path,target,ans);
        path.pollLast();
    }
}
