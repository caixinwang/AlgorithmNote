package Leetcode.AllQuestions;

import Leetcode.LeetClass.ListNode;

public class Leetcode_1669_MergeInBetween {
    //1.建立哨兵，a==0的时候也不用特殊处理。
    //2.利用pre，以及cnt，可以在遍历的过程中保存我们需要的结点。
    //3.pre的移动规则，删除的时候不动，不删除的时候才动。
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        ListNode dummy=new ListNode(0,list1),cur,pre,p1,p2;
        int cnt=0;
        for(cur=list1,pre=dummy,p1=p2=null;cur!=null;cur=cur.next,cnt++){
            if(cnt==a) p1=pre;//删除区间的前一个结点
            if(cnt==b) p2=cur;//删除区间的最后一个结点
            if(cnt>=a&&cnt<=b) pre.next=cur.next;
            else pre=cur;
        }
        for(cur=list2,pre=null;cur!=null;pre=cur,cur=cur.next);
        p1.next=list2;
        pre.next=p2.next;
        return dummy.next;
    }
}
