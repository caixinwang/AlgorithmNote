package Leetcode.SwordToOffer;

import Leetcode.LeetClass.TreeNode;

public class SwordToOffer_054_KthLargest {
    //正经的morris遍历是求第k小，这题目是求第k大，所以我们把morris的方向改一下即可
    public int kthLargest(TreeNode root, int k) {
        TreeNode cur=root,ml=null;
        while(cur!=null){
            ml=cur.right;
            if(ml!=null){
                for(;ml.left!=null&&ml.left!=cur;ml=ml.left);
                if(ml.left==null){
                    ml.left=cur;
                    cur=cur.right;
                    continue;
                }else{
                    ml.left=null;
                }
            }
            if(--k==0) return cur.val;
            cur=cur.left;
        }
        return -1;
    }
}
