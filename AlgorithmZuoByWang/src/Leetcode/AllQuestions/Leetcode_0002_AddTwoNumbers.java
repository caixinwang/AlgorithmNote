package Leetcode.AllQuestions;

public class Leetcode_0002_AddTwoNumbers {//两数相加--链表
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {
        }
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int len=0;
        ListNode cur,head,pre;
        for(cur=l1;cur!=null;cur=cur.next,len++);
        for(cur=l2;cur!=null;cur=cur.next,len--);
        if(len<0){//l1做长的
            cur=l1;
            l1=l2;
            l2=cur;
        }
        head=l1;
        int carry=0,sum=0;
        for(pre=null;l1!=null;pre=l1,l1=l1.next){
            sum=carry+(l1==null?0:l1.val)+(l2==null?0:l2.val);
            carry=sum/10;
            l1.val=sum%10;
            if(l2!=null) l2=l2.next;
        }
        if(carry!=0) pre.next=new ListNode(carry);
        return head;
    }
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode dummy=new ListNode(),head=dummy;
        int c=0;
        while(l1!=null||l2!=null||c!=0){//加起来还有值
            int n1=l1==null?0:l1.val,n2=l2==null?0:l2.val;
            head.next=new ListNode((n1+n2+c)%10);
            c=(n1+n2+c)/10;
            head=head.next;
            if(l1!=null)l1=l1.next;
            if(l2!=null)l2=l2.next;
        }
        return dummy.next;
    }

}
