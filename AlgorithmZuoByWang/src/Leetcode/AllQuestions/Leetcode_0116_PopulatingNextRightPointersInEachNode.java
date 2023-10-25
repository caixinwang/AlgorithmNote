package Leetcode.AllQuestions;

import java.util.LinkedList;
import java.util.Queue;

public class Leetcode_0116_PopulatingNextRightPointersInEachNode {

	public static class Node {
		public int val;
		public Node left;
		public Node right;
		public Node next;
	}

	/**
	 * 一个层序遍历，通过一个for循环每次搞定一层的东西，把这一层的东西像串单链表一样串起来。
	 * 这是使用辅助空间的
	 */
	public Node connect(Node root) {
		if (root==null) return null;
		Queue<Node> queue=new LinkedList<>();
		queue.add(root);
		Node cur=null,tail=null;
		while(!queue.isEmpty()){
			int nums=queue.size();
			for (int i = 0; i < nums; i++) {
				cur=queue.poll();
				if (cur.left!=null) queue.add(cur.left);
				if (cur.right!=null) queue.add(cur.right);
				if (tail==null){
					tail=cur;
				}else {
					tail.next=cur;
					tail=tail.next;
				}
			}
			tail=null;
		}
		return root;
	}

	public class MyQueue{
		Node head;
		Node tail;
		int size ;

		public MyQueue() {
			head=null;
			tail=null;
			size=0;
		}

		public int size(){
			return size;
		}

		public boolean isEmpty(){
			return size==0;
		}

		public void add(Node node){
			size++;
			if (head==null){
				head=node;
				tail=node;
			}else {
				tail.next=node;
				tail=node;
			}
		}

		public Node poll(){
			size--;
			Node res=head;
			head=head.next;
			res.next=null;//好习惯--断联之后弹出
			return res;
		}

	}

	/**
	 * 无缝衔接上面的代码，巧妙的利用了空闲的next指针当做我们的队列结构
	 * 自己实现了一个单链表的队列，利用了题目给的next空指针域，不让用额外结构是不可能滴！
	 * 我们用题目给的next空指针作为我们的额外结构！
	 */
	public Node connect2(Node root) {
		if (root==null) return null;
		MyQueue queue=new MyQueue();
		queue.add(root);
		Node cur=null,tail=null;
		while(!queue.isEmpty()){
			int nums=queue.size();
			for (int i = 0; i < nums; i++) {
				cur=queue.poll();
				if (cur.left!=null) queue.add(cur.left);
				if (cur.right!=null) queue.add(cur.right);
				if (tail==null){
					tail=cur;
				}else {
					tail.next=cur;
					tail=tail.next;
				}
			}
			tail=null;
		}
		return root;
	}

}
