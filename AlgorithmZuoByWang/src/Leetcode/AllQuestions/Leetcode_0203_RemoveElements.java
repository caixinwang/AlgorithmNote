package Leetcode.AllQuestions;

import Leetcode.LeetClass.ListNode;

public class Leetcode_0203_RemoveElements {
    //1：建立一个哨兵，不用讨论开头的特殊情况，代码极简。
    //2：pre指针记录前驱结点方便删除
    //3：删除的时候前驱不变
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy=new ListNode(0,head),cur=head,pre=dummy;
        for(;cur!=null;cur=cur.next){
            if(cur.val==val) pre.next=cur.next;
            else pre=cur;
        }
        return dummy.next;
    }
}
