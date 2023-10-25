package Leetcode.SwordToOffer;

import Leetcode.LeetClass.ListNode;

public class SwordToOffer_022_GetKthFromEnd {
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode cur,pre;
        for(cur=head,pre=null;cur!=null;cur=cur.next){
            if(--k==0) pre=head;
            else if(pre!=null)pre=pre.next;
        }
        if(k>0) return null;
        return pre;
    }
}
