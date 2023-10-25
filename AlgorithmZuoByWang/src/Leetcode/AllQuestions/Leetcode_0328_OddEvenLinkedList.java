package Leetcode.AllQuestions;

/**
 * 思路和LinkedListCode05一样。你要分成什么，我就搞几个头出来，最后再合起来。
 */
public class Leetcode_0328_OddEvenLinkedList {

	// 提交时不要提交这个类
	public static class ListNode {
		int val;
		ListNode next;
	}

	public ListNode oddEvenList(ListNode head) {
		if (head==null||head.next==null||head.next.next==null) return head;
		ListNode oddHead=head;
		ListNode evenHead=head.next;
		ListNode oddTail=oddHead;
		ListNode evenTail=evenHead;
		head=evenTail.next;
		int i=1;
		while(head!=null){
			if ((i++&1)==1) {
				oddTail.next = head;
				oddTail=oddTail.next;
			}
			else {
				evenTail.next = head;
				evenTail=evenTail.next;
			}
			head=head.next;
		}
		evenTail.next=null;
		oddTail.next=evenHead;
		return oddHead;
	}

}
