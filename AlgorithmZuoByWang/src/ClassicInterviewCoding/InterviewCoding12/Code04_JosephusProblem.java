package ClassicInterviewCoding.InterviewCoding12;

public class Code04_JosephusProblem {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	/**
	 * 报数到k的时候你需要知道前面那个人是谁，所以需要预备一个pre跟在屁股后面。
	 * @param head 第一个人
	 * @param k 报数到k就死一个
	 * @return 返回最后谁活了下来的那个结点
	 */
	public static Node josephusKill1(Node head, int k) {
		if (head==null||head.next==head||k<1) return head;
		Node pre=head;
		for (;pre.next!=head;pre=pre.next);//找到第一个人的前面一个，不能进去再找，可能进去第一个人就要删了,k=1
		int count=0;
		while(head.next!=head){
			if (++count==k){
				count=0;
				pre.next=head.next;
			}else {
				pre=head;
			}
			head= head.next;
		}
		return head;
	}

	public static Node josephusKill2(Node head, int k) {
		if (head == null || head.next == head || k < 1) {
			return head;
		}
		Node cur=head;
		int size=1;//提前把最后一个算进去
		while (cur.next!=head){//到最后一个退出循环了，最后一个没算进去
			size++;
			cur=cur.next;
		}
		int live = getLive(size, k);//活着结点是第live个，head算第1个,要偏移live-1次
		while(--live!=0){//live等于1的时候就退出了，相当于偏移了live-1次.如果换成live--就是偏移live次
			head= head.next;
		}
		head.next=head;//孤立自己然后返回
		return head;
	}

	// 现在一共有i个节点，数到m就杀死节点，最终会活下来的节点，请返回它在有i个节点时候的编号
	// 旧
	// 获得i个结点，数到m死一个，会活下来的那个结点的编号
	// getLive(N, m)
	public static int getLive(int i, int k) {
		if (i==1) return 1;//只有你一个人，当然是你活下来
		//旧=(新+k-1)%i + 1  ==>  f(i,k)=(f(i-1,k)+k-1)%i+1
		return (getLive(i-1,k)+k-1)%i+1;
	}

	public static void printCircularList(Node head) {
		if (head == null) {
			return;
		}
		System.out.print("Circular List: " + head.value + " ");
		Node cur = head.next;
		while (cur != head) {
			System.out.print(cur.value + " ");
			cur = cur.next;
		}
		System.out.println("-> " + head.value);
	}

	public static void main(String[] args) {
		Node head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = head1;
		printCircularList(head1);
		head1 = josephusKill1(head1, 3);
		printCircularList(head1);

		Node head2 = new Node(1);
		head2.next = new Node(2);
		head2.next.next = new Node(3);
		head2.next.next.next = new Node(4);
		head2.next.next.next.next = new Node(5);
		head2.next.next.next.next.next = head2;
		printCircularList(head2);
		head2 = josephusKill2(head2, 3);
		printCircularList(head2);

	}

}