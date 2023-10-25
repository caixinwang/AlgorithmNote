package Leetcode.AllQuestions;

public class Leetcode_0234_PalindromeLinkedList {

	public static class ListNode {
		public int val;
		public ListNode next;
	}

	//1.找中点 2.翻转后半部分 3.再次翻转顺便对比
	public boolean isPalindrome2(ListNode head) {
		ListNode f,s,cur,pre,next;
		for(s=f=head;f!=null&&f.next!=null;s=s.next,f=f.next.next);//找中点--中点和下中点
		for(cur=s,pre=null;cur!=null;next=cur.next,cur.next=pre,pre=cur,cur=next);//翻转后半部分
		for(cur=pre,pre=null;cur!=null&&head.val==cur.val;next=cur.next,cur.next=pre,pre=cur,cur=next,head=head.next);
		return cur==null;
	}

}
