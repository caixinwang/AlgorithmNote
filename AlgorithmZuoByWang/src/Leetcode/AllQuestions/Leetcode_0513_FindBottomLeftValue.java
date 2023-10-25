package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class Leetcode_0513_FindBottomLeftValue {
    int globalDepth=0;
    int ans=0;
    public int findBottomLeftValue(TreeNode root) {//方法一，先序遍历，先序遍历按照左边界分解，第一次来到这个深度的时候记录答案
        dfs(root,0);
        return ans;
    }
    public void dfs(TreeNode root,int depth){
        if(root==null) return ;
        if(depth==globalDepth){//第一次来到这个深度
            ans=root.val;
            globalDepth++;
        }
        dfs(root.left,depth+1);
        dfs(root.right,depth+1);
    }


    public int findBottomLeftValue2(TreeNode root) {//方法二层序遍历，队列第一个出来的结点就是一层中最左的结点。
        Queue<TreeNode> queue=new LinkedList<>();
        queue.add(root);
        TreeNode ans=null;
        while(!queue.isEmpty()){
            for(int i=0,size=queue.size();i<size;i++){
                TreeNode cur=queue.poll();
                if(cur.left!=null) queue.add(cur.left);
                if(cur.right!=null) queue.add(cur.right);
                if(i==0) ans=cur;
            }
        }
        return ans.val;
    }
}
