package Leetcode.SwordToOffer;

import Leetcode.LeetClass.ListNode;

public class SwordToOffer_035_ReversePrint {
    class Node{
        Node next;
        Node random;
        int val;

        public Node(int val) {
            this.val=val;
        }
    }

    //1.先把新串和老串交织起来，这样做的目的是为了建立新结点的random的映射。新结点可以通过老结点的next指针找到
    //2.通过映射关系把random指针串起来
    //3.将新串和老串分开。
    //注意：空结点的处理
    public Node copyRandomList(Node head) {
        if(head==null) return null;
        Node cur,next;
        for(cur=head;cur!=null;cur=next){
            next=cur.next;
            cur.next=new Node(cur.val);
            cur.next.next=next;
        }
        for(cur=head;cur!=null;cur=cur.next.next){
            Node ncur=cur.next;
            ncur.random=cur.random!=null?cur.random.next:null;
        }
        Node ans=head.next;
        for(cur=head;cur!=null;cur=cur.next){
            Node ncur=cur.next;
            cur.next=ncur.next;
            ncur.next=cur.next!=null?cur.next.next:null;
        }
        return ans;
    }
}
