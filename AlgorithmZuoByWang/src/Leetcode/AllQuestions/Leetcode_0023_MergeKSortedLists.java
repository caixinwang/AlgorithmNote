package Leetcode.AllQuestions;

import Leetcode.LeetClass.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Leetcode_0023_MergeKSortedLists {


	public static class NodeComparator implements Comparator<ListNode> {
		@Override
		public int compare(ListNode o1, ListNode o2) {
			return o1.val-o2.val;
		}
	}

	public ListNode mergeKLists(ListNode[] lists) {
		if (lists==null||lists.length==0) return null;
		ListNode head=null,tail=null;
		PriorityQueue<ListNode> queue=new PriorityQueue<>(new NodeComparator());
		for (ListNode list : lists) {
			if (list!=null)queue.add(list);//头先放进来
		}
		if (queue.isEmpty()) return null;
		head = tail= queue.poll();
		if (head.next!=null) queue.add(head.next);
		while (!queue.isEmpty()){
			tail.next=queue.poll();
			tail=tail.next;
			if (tail.next!=null) queue.add(tail.next);
		}
		return head;
	}

	public ListNode mergeKLists2(ListNode[] lists) {
		if(lists.length==0) return null;
		ListNode dummy=new ListNode(0),cur;
		PriorityQueue<ListNode> pq=new PriorityQueue<>((a,b)->a.val-b.val);
		for(ListNode node:lists) if(node!=null)pq.add(node);
		for(cur=dummy;!pq.isEmpty();cur=cur.next){
			ListNode poll=pq.poll();
			cur.next=poll;
			if(poll.next!=null) pq.add(poll.next);
		}
		return dummy.next;
	}
}
