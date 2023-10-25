package Leetcode.AllQuestions;

import Leetcode.LeetClass.TreeNode;

public class Leetcode_0114_FlattenBinaryTreeToLinkedListII {//中序的flatten
    //中序的话可以直接改，因为右指针改完之后在中序的morris用不到了
    public TreeNode flatten(TreeNode root) {
        if(root==null) return null;
        TreeNode cur,mr,pre,head;
        for(cur=root,mr=head=pre=null;cur!=null;){
            mr=cur.left;
            if(mr!=null){
                for(;mr.right!=null&&mr.right!=cur;mr=mr.right);
                if(mr.right==null){
                    mr.right=cur;
                    cur=cur.left;
                    continue;
                }else{
                    mr.right=null;
                }
            }
            if(head==null) head=cur;
            if(pre!=null) {
                pre.left=null;
                pre.right=cur;
            }
            pre=cur;
            cur=cur.right;
        }
        pre.left=null;//中序遍历不要忘记了，最后一个结点还没有设置
        pre.right=cur;
        return head;
    }

    public TreeNode flatten2(TreeNode root) {//也可以使用左指针先串，最后再连起来
        if(root==null) return null;
        TreeNode cur,mr,pre,head;
        for(cur=root,mr=head=pre=null;cur!=null;){
            mr=cur.left;
            if(mr!=null){
                for(;mr.right!=null&&mr.right!=cur;mr=mr.right);
                if(mr.right==null){
                    mr.right=cur;
                    cur=cur.left;
                    continue;
                }else{
                    mr.right=null;
                }
            }
            if(head==null) head=cur;
            if(pre!=null) pre.left=cur;
            pre=cur;
            cur=cur.right;
        }
        if(pre!=null) pre.left=cur;
        for (cur=head;cur!=null;cur=cur.right){
            cur.right=cur.left;
            cur.left=null;
        }
        return head;
    }


}
