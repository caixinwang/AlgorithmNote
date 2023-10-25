package Leetcode.AllQuestions;

import Leetcode.LeetClass.ListNode;

public class Leetcode_0086_Partition {
    //1.哨兵
    //2.断联+串
    //3.连小串和大串
    public ListNode partition(ListNode head, int x) {
        ListNode dummy1=new ListNode(0),dummy2=new ListNode(0);//哨兵，分别是小于链和大于等于链
        ListNode cur,p1,p2,next;
        for(cur=head,p1=dummy1,p2=dummy2;cur!=null;cur=next){
            next=cur.next;
            cur.next=null;//需要断联，不然会形成环
            if(cur.val<x) {
                p1.next=cur;
                p1=p1.next;
            }else {
                p2.next=cur;
                p2=p2.next;
            }
        }
        if(p1==dummy1) return dummy2.next;//说明小于链没有东西，直接返回大于链
        p1.next=dummy2.next;//小于链尾连大于链头
        return dummy1.next;
    }

    //如果不断联链表，就需要知道环其实是由于p2指回了小链表导致的，所以只需要在最后断联p2即可。
    public ListNode partition2(ListNode head, int x) {
        ListNode dummy1=new ListNode(0),dummy2=new ListNode(0);//哨兵，分别是小于链和大于等于链
        ListNode cur,p1,p2,next;
        for(cur=head,p1=dummy1,p2=dummy2;cur!=null;cur=cur.next){
            if(cur.val<x) {
                p1.next=cur;
                p1=p1.next;
            }else {
                p2.next=cur;
                p2=p2.next;
            }
        }
        p2.next=null;//导致环
        if(p1==dummy1) return dummy2.next;//说明小于链没有东西，直接返回大于链
        p1.next=dummy2.next;//小于链尾连大于链头
        return dummy1.next;
    }
}
