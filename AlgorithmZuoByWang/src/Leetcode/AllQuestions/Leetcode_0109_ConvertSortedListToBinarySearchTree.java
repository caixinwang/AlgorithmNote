package Leetcode.AllQuestions;

import Leetcode.LeetClass.ListNode;
import Leetcode.LeetClass.TreeNode;

public class Leetcode_0109_ConvertSortedListToBinarySearchTree {
    ListNode gnode;
    public TreeNode sortedListToBST(ListNode head) {
        if(head==null) return null;
        gnode=head;
        int n=0;
        for(ListNode cur=gnode;cur!=null;cur=cur.next,n++);
        return f(0,n-1);
    }

     public TreeNode f(int l,int r){
         if(l>r) return null;
         int mid=l+(r-l>>1);
         TreeNode head=new TreeNode();//不着急填值
         head.left=f(l,mid-1);
         head.val=getVal();//到中序的位置再填值
         head.right=f(mid+1,r);
         return head;
     }

    public TreeNode f2(int l,int r){//画个图会发现，这样分解，只会刚好分出n个结点
        if(l==r) return new TreeNode(getVal());
        int mid=l+(r-l>>1);
        TreeNode head=new TreeNode();//不着急填值
        if(mid-1>=l) head.left=f2(l,mid-1);
        head.val=getVal();//到中序的位置再填值
        if(mid+1<=r) head.right=f2(mid+1,r);
        return head;
    }

    public int getVal(){
        int ans=gnode.val;
        gnode=gnode.next;
        return ans;
    }
}
