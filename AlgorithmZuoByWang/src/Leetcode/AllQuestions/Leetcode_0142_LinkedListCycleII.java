package Leetcode.AllQuestions;

public class Leetcode_0142_LinkedListCycleII {
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode detectCycle(ListNode head) {
        if (head==null||head.next==null) return null;
        ListNode f=head,s=head;
        do {
            f=f.next.next;
            s=s.next;
        }while (f!=null&&f.next!=null&&f!=s);
        if (f!=s) return null;
        for (s=head;s!=f;s=s.next,f=f.next);
        return s;
    }
}
