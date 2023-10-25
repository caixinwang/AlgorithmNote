package Leetcode.AllQuestions;

import Leetcode.LeetClass.ListNode;

public class Leetcode_0092_ReverseLinkedListII {

    public static ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode p1=null,p2=null,p3=null,p4=null,cur,pre;
        int cnt=1;
        for (cur=head,pre=null;cur!=null&&cnt<=right+1;pre=cur,cur=cur.next,cnt++){//在cnt的过程中去动态改指针
            if (cnt==left){
                p2=cur;
                p1=pre;
            }
            if (cnt==right) p3=cur;
            if (cnt==right+1) p4=cur;
        }
        reverse(p2, p3);
        if (p1!=null) p1.next=p3;
        p2.next=p4;
        return left==1?p3:head;
    }

    public static void reverse(ListNode s,ListNode e){
        ListNode pre=null,next,cur=s,end=e.next;
        while(cur!=end){//end一定不能写成e.next
            next=cur.next;
            cur.next=pre;
            pre=cur;
            cur=next;
        }
    }

    public ListNode reverseBetween2(ListNode head, int left, int right) {
        ListNode dummy=new ListNode(0,head),cur,pre,next,p;//设置哨兵，left为1的时候也可以统一处理
        int cnt=0;
        for(cur=dummy,p=null;cnt<left;p=cur,cur=cur.next,cnt++);//设置p，p为翻转的l~r段的前一个结点
        for(pre=next=null;cnt<=right;next=cur.next,cur.next=pre,pre=cur,cur=next,cnt++);//翻转
        p.next.next=cur;//翻转之后的最后一个结点连接到接下来的结点
        p.next=pre;//p连接到翻转之后的第一个结点
        return dummy.next;
    }


    public static void main(String[] args) {
        ListNode listNode=new ListNode(1);
        listNode.next=new ListNode(2);
        listNode.next.next=new ListNode(3);
        listNode.next.next.next=new ListNode(4);
        listNode.next.next.next.next=new ListNode(5);
        listNode.next.next.next.next.next=new ListNode(6);
        listNode.next.next.next.next.next.next=new ListNode(7);
        ListNode reverse = reverseBetween(listNode,1,7);
        for (;reverse!=null;reverse=reverse.next){
            System.out.println(reverse.val);
        }

    }
}
