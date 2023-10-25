package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Leetcode_0863_DistanceK {
    HashMap<TreeNode,TreeNode> parent=new HashMap<>();
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        ArrayList<Integer> ans=new ArrayList();
        if(root==null) return ans;
        fillParents(root);
        f(target,null,k,0,ans);
        return ans;
    }

    public void f(TreeNode cur, TreeNode from , int k,int depth, ArrayList<Integer> ans){
        if(cur==null) return;
        if(depth==k) {
            ans.add(cur.val);
            return;
        }
        if(cur.left!=from) f(cur.left,cur,k,depth+1,ans);
        if(cur.right!=from) f(cur.right,cur,k,depth+1,ans);
        if(parent.containsKey(cur)&&parent.get(cur)!=from) f(parent.get(cur),cur,k,depth+1,ans);
    }

    public void fillParents(TreeNode root){
        if(root==null) return;
        if(root.left!=null) parent.put(root.left,root);
        if(root.right!=null) parent.put(root.right,root);
        fillParents(root.left);
        fillParents(root.right);
    }
}
