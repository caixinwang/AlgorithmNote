package Leetcode.AllQuestions;

public class Leetcode_0021_MergeTwoSortedLists {

	public static class ListNode {
		public int val;
		public ListNode next;
	}

	public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		if (l1==null||l2==null) return l1!=null?l1:l2;
		ListNode head,tail;
		if (l1.val<= l2.val){
			head=tail=l1;
			l1=l1.next;
		}else {
			head=tail=l2;
			l2=l2.next;
		}
		while(l1!=null&&l2!=null){
			if (l1.val<=l2.val){
				tail.next=l1;
				tail=tail.next;
				l1=l1.next;
			}else {
				tail.next=l2;
				tail=tail.next;
				l2=l2.next;
			}
		}
		tail.next=l1!=null?l1:l2;
		return head;
	}

	public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
		if(l1==null||l2==null) return l1!=null?l1:l2;
		if(l1.val>l2.val) return mergeTwoLists2(l2,l1);
		ListNode cur1=l1.next,cur2=l2,tail=l1;
		while(cur1!=null&&cur2!=null){
			if(cur1.val<cur2.val){
				tail.next=cur1;
				cur1=cur1.next;
			}else{
				tail.next=cur2;
				cur2=cur2.next;
			}
			tail=tail.next;
		}
		tail.next=cur1!=null?cur1:cur2;
		return l1;
	}

}
