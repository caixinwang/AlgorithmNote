package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;
import com.sun.source.tree.Tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Leetcode_0145_PostorderTraversal {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ans=new LinkedList<>();
        f(root,ans);
        return ans;
    }

    public void f(TreeNode head,List<Integer> ans){
        if(head==null) return;
        f(head.left,ans);
        f(head.right,ans);
        ans.add(head.val);
    }

    public List<Integer> postorderTraversal2(TreeNode root) {
        List<Integer> ans=new LinkedList<>();
        TreeNode cur,mr;
        for (cur=root;cur!=null;){
            mr=cur.left;
            if (mr!=null){//处理左树
                for (;mr.right!=null&&mr.right!=cur;mr=mr.right);
                if (mr.right==null){//第一次来到
                    mr.right=cur;
                    cur=cur.left;
                    continue;
                }else {//第二次来到的时候就是打印的实际，按照cur的左子树的右边界逆序打印,mr此时就是cur左子树的最右
                    mr.right=null;
                    printEdge(cur.left,ans);
                }
            }
            cur=cur.right;
        }
        printEdge(root,ans);
        return ans;
    }
    public void printEdge(TreeNode from,List<Integer> ans){
        TreeNode cur,pre,next;
        for (cur=from,pre=null;cur!=null;next=cur.right,cur.right=pre,pre=cur,cur=next);
        for (cur=pre,pre=null;cur!=null;ans.add(cur.val),next=cur.right,cur.right=pre,pre=cur,cur=next);
    }

    public List<Integer> postorderTraversal3(TreeNode root) {
        LinkedList<Integer> ans=new LinkedList<>();
        Stack<TreeNode> stack=new Stack<>();
        if(root!=null) stack.add(root);
        while(!stack.isEmpty()){
            root=stack.pop();
            ans.addFirst(root.val);
            if (root.left!=null) stack.push(root.left);
            if (root.right!=null) stack.push(root.right);
        }
        return ans;
    }
}
