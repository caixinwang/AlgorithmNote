package Leetcode.SwordToOffer;

import Leetcode.LeetClass.ListNode;

public class SwordToOfferII_025_AddTwoNumbers {
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {//在原串上改，需要长短交换以及pre
        l1=reverse(l1);
        l2=reverse(l2);
        ListNode cur,pre,head;
        int cnt=0,carry=0,sum=0;
        for(cur=l1;cur!=null;cur=cur.next,cnt++);
        for(cur=l2;cur!=null;cur=cur.next,cnt--);
        if(cnt<0){
            cur=l1;
            l1=l2;
            l2=cur;
        }
        head=l1;
        for(cur=l1,pre=null;l2!=null||l1!=null;pre=cur,cur=cur.next){
            sum=l1.val+carry+(l2!=null?l2.val:0);
            if(l2!=null) l2=l2.next;
            l1=l1.next;
            carry=sum/10;
            cur.val=sum%10;
        }
        if(carry!=0) pre.next=new ListNode(carry);
        return reverse(head);
    }

    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {//新返回一个串
        l1=reverse(l1);
        l2=reverse(l2);
        ListNode cur,dummy=new ListNode(0),p1,p2;
        int carry=0,sum=0;
        for(p1=l1,p2=l2,cur=dummy;p2!=null||p1!=null;cur=cur.next){
            sum=(p1!=null?p1.val:0)+(p2!=null?p2.val:0)+carry;
            if(p2!=null) p2=p2.next;
            if(p1!=null) p1=p1.next;
            carry=sum/10;
            cur.next=new ListNode(sum%10);
        }
        if(carry!=0) cur.next=new ListNode(carry);
        cur=dummy.next;
        dummy.next=null;
        return reverse(cur);
    }

    public static ListNode reverse(ListNode root){
        ListNode pre,cur,next;
        for(cur=root,pre=null;cur!=null;next=cur.next,cur.next=pre,pre=cur,cur=next);
        return pre;
    }

    public static void main(String[] args) {
        ListNode l1=new ListNode(0);
//        l1.next=new ListNode(7);
//        l1.next.next=new ListNode(4);
//        l1.next.next.next=new ListNode(3);
        ListNode l2=new ListNode(7);
        l2.next=new ListNode(3);
//        l2.next.next=new ListNode(4);
        addTwoNumbers(l1,l2);
    }
}
