package Leetcode.AllQuestions;

public class Leetcode_0237_DeleteNodeInLinkedList {

	public static class ListNode {
		int val;
		ListNode next;
	}

	//本质是你把自己的值改了，然后把下一个结点删了。这种做法不好！！！
	public void deleteNode(ListNode node) {
		node.val = node.next.val;//牺牲node后面一个结点。题目保证node不是最后一个结点
		node.next = node.next.next;
	}

}
