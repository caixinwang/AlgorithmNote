package Leetcode.AllQuestions;

import Leetcode.LeetClass.ListNode;

public class Leetcode_0083_DeleteDuplicates {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode cur,pre;
        for(cur=head,pre=null;cur!=null;cur=cur.next){
            if(pre!=null&&cur.val==pre.val) pre.next=cur.next;
            else pre=cur;
        }
        return head;
    }
}
