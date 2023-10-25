package Leetcode.AllQuestions;

import Leetcode.LeetClass.ListNode;

public class Leetcode_0876_MiddleNode {
    public ListNode middleNode(ListNode head) {
        ListNode s,f;
        for(s=f=head;f!=null&&f.next!=null;s=s.next,f=f.next.next);
        return s;
    }
}
