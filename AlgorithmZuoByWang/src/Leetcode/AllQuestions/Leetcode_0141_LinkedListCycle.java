package Leetcode.AllQuestions;

public class Leetcode_0141_LinkedListCycle {
	class ListNode {
		int val;
		ListNode next;
		ListNode(int x) {
			val = x;
			next = null;
		}
	}

	public boolean hasCycle(ListNode head) {
		if (head==null||head.next==null) return false;
//		ListNode f=head.next.next;
//		ListNode s=head.next;
//		while (f!=null&&f.next!=null&&f!=s){//跳到第一个f==s的位置出来，或者f走到头了
//			f=f.next.next;
//			s=s.next;
//		}
		ListNode f=head;
		ListNode s=head;
		do {
			f=f.next.next;
			s=s.next;
		}while(f!=null&&f.next!=null&&f!=s);
		return f==s;
	}

	public boolean hasCycle2(ListNode head) {
		return getEnterCycle(head)!=null;
	}

	public ListNode getEnterCycle(ListNode head) {
		if (head==null||head.next==null) return null;
		ListNode f=head.next.next;//有判断f！=s所以要这样初始化。
		ListNode s=head.next;
		while (f!=null&&f.next!=null&&f!=s){//跳到第一个f==s的位置出来，或者f走到头了
			f=f.next.next;
			s=s.next;
		}
		if (f!=s) return null;//f走到头了，说明没有环
		s=head;//从头走
		while(s!=f){
			s=s.next;
			f=f.next;
		}
		return f;
	}




}
