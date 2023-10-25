package Leetcode.SwordToOffer;

import Leetcode.LeetClass.ListNode;

public class SwordToOffer_018_DeleteNode {
    public ListNode deleteNode(ListNode head, int val) {
        ListNode dummy=new ListNode(),cur=head,pre=dummy;
        dummy.next=head;
        for(;cur.val!=val;pre=cur,cur=cur.next);
        pre.next=cur.next;
        return dummy.next;
    }
}
