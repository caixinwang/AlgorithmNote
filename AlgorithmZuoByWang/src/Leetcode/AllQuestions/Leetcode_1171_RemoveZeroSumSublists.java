package Leetcode.AllQuestions;

import Leetcode.LeetClass.ListNode;

import java.util.HashMap;

public class Leetcode_1171_RemoveZeroSumSublists {
    //为什么需要dummy？为了防止漏掉整个前缀就是移出目标的情况。
    //做法：先遍历一遍记录前缀值最晚出现的位置。然后从头遍历一遍。
    public ListNode removeZeroSumSublists(ListNode head) {
        HashMap<Integer,ListNode> map=new HashMap<>();
        ListNode dummy=new ListNode(),cur=head;
        dummy.next=head;
        int sum=0;
        while(cur!=null){
            sum+=cur.val;
            map.put(sum,cur);
            cur=cur.next;
        }
        for(cur=dummy,sum=0;cur!=null;cur=cur.next){
            sum+=cur.val;
            if(map.containsKey(sum-0)){
                cur.next=map.get(sum-0).next;
            }
        }
        return dummy.next;
    }
}
