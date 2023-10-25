package Leetcode.AllQuestions;
import Leetcode.LeetClass.ListNode;

public class Leetcode_0160_IntersectionOfTwoLinkedLists {
	public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		ListNode cur,p1,p2;
		int cnt=0;
		for(cur=headA;cur!=null;cur=cur.next,cnt++);
		for(cur=headB;cur!=null;cur=cur.next,cnt--);
		if(cnt<0) {//headA做长的
			cnt=-cnt;
			cur=headA;
			headA=headB;
			headB=cur;
		}
		for(p1=headA;cnt!=0;p1=p1.next,cnt--);
		for(p2=headB;p1!=null&&p1!=p2;p1=p1.next,p2=p2.next);
		return p1;
	}
}
