package Leetcode.AllQuestions;

import Leetcode.LeetClass.ListNode;

import java.util.Stack;

public class Leetcode_0445_AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        l1=reverse(l1);
        l2=reverse(l2);
        ListNode cur,dummy=new ListNode(0),p1,p2;
        int carry=0,sum=0;
        for(p1=l1,p2=l2,cur=dummy;p2!=null||p1!=null;cur=cur.next){//这里的cur其实是链表的尾巴
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

    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode cur;
        int carry=0,sum=0,top1=-1,top2=-1;
        int[] s1=new int[100];
        int[] s2=new int[100];
        for(cur=l1;cur!=null;cur=cur.next) s1[++top1]=cur.val;
        for(cur=l2;cur!=null;cur=cur.next) s2[++top2]=cur.val;
        for(cur=null;top1!=-1||top2!=-1||carry!=0;cur=cur.next){//cur其实就是单链表的head,头插法，不要dummy了。
            sum=(top1!=-1?s1[top1--]:0)+(top2!=-1?s2[top2--]:0)+carry;
            carry=sum/10;
            ListNode node=new ListNode(sum%10);
            node.next=cur;
            cur=node;
        }
        return cur;
    }

    public ListNode reverse(ListNode root){
        ListNode pre,cur,next;
        for(cur=root,pre=null;cur!=null;next=cur.next,cur.next=pre,pre=cur,cur=next);
        return pre;
    }


}
