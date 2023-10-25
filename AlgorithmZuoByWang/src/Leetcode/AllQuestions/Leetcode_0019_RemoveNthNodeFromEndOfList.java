package Leetcode.AllQuestions;
import Leetcode.LeetClass.ListNode;
public class Leetcode_0019_RemoveNthNodeFromEndOfList {
	//1.使用哨兵+pre指针可以极大的简化代码
	//2.注意n计数的使用
	public ListNode removeNthFromEnd2(ListNode head, int n) {//用cnt来控制，很舒服！
		if (head==null||head.next==null) return null;
		ListNode pre=null;//来到要删除结点的前一个
		ListNode cur=head;
		while(cur!=null){//要来到倒数第几个，就要让cur先走几步，我们需要来到倒数第n个的前一个，所以cur先走n+1步，用cnt来控制
			n--;
			if (n==-1) pre=head;//如果有前一个，那么这一步一定会执行
			if (n<-1) pre=pre.next;
			cur=cur.next;
		}
		if (n>0) return head;//不够n个，非法
		if (n==0) return head.next;//刚好够n个，那么就是删除了第一个
		pre.next=pre.next.next;//其它情况就是要删除的结点还有前一个
		return head;
	}

	public ListNode removeNthFromEnd(ListNode head, int n) {
		ListNode dummy=new ListNode(0,head),cur,pre,p;
		for(cur=p=head,pre=dummy;cur!=null;cur=cur.next,n--){
			if(n<=0) {
				pre=p;
				p=p.next;
			}
		}
		pre.next=p.next;
		return dummy.next;
	}

}
