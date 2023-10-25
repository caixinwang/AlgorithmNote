package Leetcode.AllQuestions;

import Leetcode.LeetClass.ListNode;

public class Leetcode_0061_RotateRight {
    //技巧:1.寻找倒数第k个结点 2.用pre保存前驱。
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null) return head;
        ListNode tail,cur,nh,nhpre;
        int size=0;
        for(cur=head;cur!=null;cur=cur.next,size++);
        k%=size;
        if(k==0) return head;
        for(cur=nh=head,tail=nhpre=null;cur!=null;tail=cur,cur=cur.next,k--){
            if(k<=0) {//找倒数第k个结点,k减到0了之后开始动nh。
                nhpre=nh;
                nh=nh.next;
            }
        }
        tail.next=head;
        nhpre.next=null;
        return nh;
    }
}
