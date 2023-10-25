package Leetcode.AllQuestions;

public class Leetcode_0138_CopyListWithRandomPointer {
	class Node {
		int val;
		Node next;
		Node random;

		public Node(int val) {
			this.val = val;
			this.next = null;
			this.random = null;
		}
	}
	public Node copyRandomList(Node head) {
		if (head==null) return null;
		Node cur= head;
		while(cur!=null){//新节点插在老结点后面
			Node node=new Node(cur.val);
			node.next=cur.next;
			cur.next=node;
			cur=node.next;
		}
		cur=head;
		while(cur!=null){//连random
			Node n=cur.next;
			if (cur.random!=null) n.random=cur.random.next;
			cur=n.next;
		}
		cur=head;
		Node ans=cur.next;
		while(cur!=null){//独立两个链表
			Node n=cur.next;
			cur.next=n.next;
			cur=cur.next;
			if (cur!=null) {
				n.next = cur.next;
			}
		}
		return ans;
	}
}
