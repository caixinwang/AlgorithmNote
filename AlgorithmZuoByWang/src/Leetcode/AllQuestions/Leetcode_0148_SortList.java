package Leetcode.AllQuestions;

import Leetcode.LeetClass.ListNode;

public class Leetcode_0148_SortList {
    /**
     * 使用自底向上的归并排序。
     * 1.实现merge，并且返回头尾
     * 2.实现获取两个长度为k的链，并且断联，返回h2以及h3
     * 3.开始归并
     */
    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode dummy=new ListNode(0,head),cur,head1;
        ListNode[] nodes,merge;
        int step,size;
        for(size=0,cur=head;cur!=null;cur=cur.next,size++);
        for(step=1;step<=size;step<<=1){
            for(cur=dummy,head1=dummy.next;head1!=null;){//换头了，这里不能写成head1=head
                nodes=getHead2KGroup(head1,step);
                merge=merge(head1,nodes[0]);
                cur.next=merge[0];
                cur=merge[1];
                cur.next=nodes[1];
                head1=nodes[1];
            }
        }
        return dummy.next;
    }
    /**
     * @param head1 第一组的第一个结点
     * @param k     k个一组，让你返回第二组的第一个结点，并且第一组和第二组断联，第二组的尾和后面的组断联
     * @return 返回第二组的第一个结点以及第三组的第一个结点, [head2, head3]
     */
    public static ListNode[] getHead2KGroup(ListNode head1, int k) {
        ListNode cur,head2=null,head3=null,pre;
        int cnt=0;
        for(cur=head1,pre=null;cur!=null&&cnt<=k<<1;pre=cur,cur=cur.next,cnt++){//注意是cnt<=
            if(cnt==k) {
                pre.next=null;
                head2=cur;
            }
            if(cnt==k<<1) {
                pre.next=null;
                head3=cur;
            }
        }
        return new ListNode[]{head2,head3};
    }
    /**
     * 合并两个有序数组
     * 技巧1：巧用dummy，可以使得我们收集答案的时候不用管一开始答案链为空的情况。
     * @param head1 head1和head2已经断联，也就是最后一个结点都指向空
     * @param head2 -
     * @return 返回排序后的头和尾, [head, tail]
     */
    public static ListNode[] merge(ListNode head1, ListNode head2) {
        ListNode dummy=new ListNode(0),p1,p2,cur;
        for(p1=head1,p2=head2,cur=dummy;p1!=null&&p2!=null;cur=cur.next){
            if(p1.val<=p2.val){
                cur.next=p1;
                p1=p1.next;
            }else{
                cur.next=p2;
                p2=p2.next;
            }
        }
        cur.next=p1!=null?p1:p2;
        for(;cur.next!=null;cur=cur.next);//找到倒数第一个结点
        return new ListNode[]{dummy.next,cur};
    }

    public static void main(String[] args) {
        ListNode head=new ListNode(4);
        head.next=new ListNode(2);
        head.next.next=new ListNode(1);
        head.next.next.next=new ListNode(3);
        sortList(head);
    }
}
