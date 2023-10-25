package Leetcode.SwordToOffer;

import Leetcode.LeetClass.ListNode;

public class SwordToOffer_025_MergeTwoLists {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1==null||l2==null) return l1==null?l2:l1;
        if(l1.val>l2.val) return mergeTwoLists(l2,l1);
        ListNode p1=l1.next,p2=l2,pre=l1;
        while(p1!=null&&p2!=null){
            if(p1.val<=p2.val){
                pre.next=p1;
                p1=p1.next;
            }else{
                pre.next=p2;
                p2=p2.next;
            }
            pre=pre.next;
        }
        if(p1==null) pre.next=p2;
        else pre.next=p1;
        return l1;
    }
}
