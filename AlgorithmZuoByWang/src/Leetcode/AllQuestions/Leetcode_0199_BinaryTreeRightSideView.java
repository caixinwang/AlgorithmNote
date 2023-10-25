package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Leetcode_0199_BinaryTreeRightSideView {
    public List<Integer> rightSideView(TreeNode root) {//层序遍历
        List<Integer> ans=new LinkedList<>();
        if(root==null) return ans;
        Queue<TreeNode> queue=new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            int size=queue.size();
            for(int i=0;i<size;i++){
                TreeNode cur=queue.poll();
                if(i==0)ans.add(cur.val);
                if(cur.right!=null) queue.add(cur.right);//先进右边，因为是右视图。
                if(cur.left!=null) queue.add(cur.left);
            }

        }
        return ans;
    }

    public List<Integer> rightSideView2(TreeNode root) {//深度优先遍历，改版的先序遍历。记录一个深度，判断要不要加答案
        List<Integer> ans=new ArrayList<>();
        f(root,ans,0);
        return ans;
    }

    public void f(TreeNode root, List<Integer> ans,int depth){
        if(root==null) return;
        if (depth==ans.size()) ans.add(root.val);
        f(root.right,ans,depth+1);
        f(root.left,ans,depth+1);
    }

}
