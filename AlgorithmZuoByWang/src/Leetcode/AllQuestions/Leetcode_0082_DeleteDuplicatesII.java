package Leetcode.AllQuestions;

import Leetcode.LeetClass.ListNode;

public class Leetcode_0082_DeleteDuplicatesII {
    // 1.建立哨兵，方便链表头被清理。因为删除一个结点需要前驱，有了哨兵，就算头节点需要清理也能有哨兵作为前驱。
    // 2.遍历到原本的链表的每一个结点，如果不是要删除的d，就往后看看是否d需要更新了
    // 3.d的含义为最近的冗余的值，需要清理
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy=new ListNode(0,head),cur,pre;
        int d=200;
        for(cur=head,pre=dummy;cur!=null;cur=cur.next){
            if(cur.val!=d&&cur.next!=null&&cur.next.val==cur.val) d=cur.val;
            if(cur.val==d) pre.next=cur.next;
            else pre=cur;
        }
        return dummy.next;
    }
}
