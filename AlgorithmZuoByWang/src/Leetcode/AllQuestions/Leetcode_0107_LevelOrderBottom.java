package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Leetcode_0107_LevelOrderBottom {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<List<Integer>> ans=new LinkedList<>();
        if(root==null) return ans;
        Queue<TreeNode> queue=new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            int size=queue.size();
            List<Integer> list=new LinkedList<>();
            for(int i=0;i<size;i++){
                TreeNode poll= queue.poll();
                list.add(poll.val);
                if(poll.left!=null) queue.add(poll.left);
                if(poll.right!=null) queue.add(poll.right);
            }
            ans.addFirst(list);//从头加，最后就是倒序的
        }
        return ans;
    }
}
