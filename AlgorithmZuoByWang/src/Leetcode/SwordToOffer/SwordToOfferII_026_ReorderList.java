package Leetcode.SwordToOffer;

import Leetcode.LeetClass.ListNode;

public class SwordToOfferII_026_ReorderList {
    public void reorderList(ListNode head) {
        ListNode dummy=new ListNode(0),cur,pre,next,s,f,p1,p2;
        for(s=f=head;f!=null&&f.next!=null;s=s.next,f=f.next.next);//s来到中点or下中点
        for(cur=s,pre=null;cur!=null;next=cur.next,cur.next=pre,pre=cur,cur=next);//翻转
        for(cur=dummy,p1=head,p2=pre;p1!=p2&&p2!=null;){
            next=p1.next;
            cur.next=p1;
            p1.next=p2;
            p1=next;
            cur=p2;
            p2=p2.next;
        }
    }
}
