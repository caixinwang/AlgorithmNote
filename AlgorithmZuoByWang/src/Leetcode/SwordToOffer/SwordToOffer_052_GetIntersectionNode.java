package Leetcode.SwordToOffer;

import Leetcode.LeetClass.ListNode;

public class SwordToOffer_052_GetIntersectionNode {
    ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int n=0;
        ListNode cur;
        for(cur=headA;cur!=null;cur=cur.next) n++;
        for(cur=headB;cur!=null;cur=cur.next) n--;
        if(n<0){//A做长的
            cur=headA;
            headA=headB;
            headB=cur;
        }
        for(n=Math.abs(n);n!=0;n--,headA=headA.next);
        while(headA!=null&&headA!=headB){
            headA=headA.next;
            if(headB!=null) headB=headB.next;
        }
        return headA;
    }
}
