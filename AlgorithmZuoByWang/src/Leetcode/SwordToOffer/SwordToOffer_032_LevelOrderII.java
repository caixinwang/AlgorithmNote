package Leetcode.SwordToOffer;

import Leetcode.LeetClass.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SwordToOffer_032_LevelOrderII {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans=new ArrayList<>();
        Queue<TreeNode> queue=new LinkedList<>();
        if(root!=null)queue.add(root);
        while(!queue.isEmpty()){
            List<Integer> list=new ArrayList<>();
            for(int size=queue.size(),i=0;i<size;i++){
                TreeNode cur=queue.poll();
                list.add(cur.val);
                if(cur.left!=null) queue.add(cur.left);
                if(cur.right!=null) queue.add(cur.right);
            }
            ans.add(list);
        }
        return ans;
    }

}
