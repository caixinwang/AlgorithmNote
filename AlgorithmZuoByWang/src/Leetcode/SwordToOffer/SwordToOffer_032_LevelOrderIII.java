package Leetcode.SwordToOffer;

import Leetcode.LeetClass.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SwordToOffer_032_LevelOrderIII {
    //一次搞一层的层序遍历不变，但是我们根据flag，改变加入list中的结点值的顺序即可。
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ans=new LinkedList<>();
        Queue<TreeNode> queue=new LinkedList<>();
        if(root!=null)queue.add(root);
        boolean flag=true;
        while(!queue.isEmpty()){
            LinkedList<Integer> list=new LinkedList<>();
            for(int size=queue.size(),i=0;i<size;i++){
                TreeNode cur=queue.poll();
                if(flag) list.addLast(cur.val);
                else list.addFirst(cur.val);
                if(cur.left!=null) queue.add(cur.left);
                if(cur.right!=null) queue.add(cur.right);
            }
            ans.add(list);
            flag=!flag;
        }
        return ans;
    }

}
