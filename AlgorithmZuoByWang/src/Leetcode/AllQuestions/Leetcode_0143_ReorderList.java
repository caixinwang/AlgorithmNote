package Leetcode.AllQuestions;

public class Leetcode_0143_ReorderList {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void reorderList(ListNode head) {
        ListNode mid = getMid(head);
        ListNode p1 = head, p2 = reverse(mid), next1, next2;
        while (p2 != mid) {
            next1 = p1.next;
            next2 = p2.next;
            p1.next = p2;
            p2.next = next1;
            p1 = next1;
            p2 = next2;
        }
    }

    public static ListNode getMid(ListNode head) {//返回中、下中
        ListNode f, s;
        for (f = s = head; f != null && f.next != null; f = f.next.next, s = s.next) ;
        return s;
    }

    public static ListNode reverse(ListNode head) {
        ListNode pre = null, next;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public void reorderList2(ListNode head) {
        ListNode dummy=new ListNode(0),cur,pre,next,s,f,p1,p2;
        for(s=f=head;f!=null&&f.next!=null;s=s.next,f=f.next.next);//s来到中点or下中点
        for(cur=s,pre=null;cur!=null;next=cur.next,cur.next=pre,pre=cur,cur=next);//翻转
        for(cur=dummy,p1=head,p2=pre;p1!=p2&&p2!=null;){
            next=p1.next;
            cur.next=p1;
            p1.next=p2;
            p1=next;
            cur=p2;
            p2=p2.next;
        }
    }


}
