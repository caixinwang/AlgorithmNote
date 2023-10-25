package BasicLearning.Class03_LinkedList;
//题目：给定两个有序链表的头节点head1和head2,返回合并之后的大链表，要求依然有序
public class Code11_MergeTwoSortedLinkedList {

    // 不要提交这个类
    public static class ListNode {
        public int val;
        public ListNode next;
    }

    /**
     * 思路：选择链表1和链表2中头节点的比较小的作为head，p1或p2要对应后移一位。head每次连接p1和p2中比较小的那个。
     * @param head1:
     * @param head2:
     * @return :
     */
    public static ListNode mergeTwoLists(ListNode head1, ListNode head2) {
        if (head1==null||head2==null) return head1!=null?head1:head2;
        ListNode head=null,cur=null;
        if (head1.val<= head2.val){//选择一个合适的头部
            head=head1;
            head1= head1.next;
        }else {
            head=head2;
            head2= head2.next;
        }
        cur=head;//记录从p1和p2的遍历过程中收集起来的链表的最后一个结点。
        while(head1!=null&& head2!=null){
            if (head1.val<= head2.val){
                cur.next=head1;
                head1= head1.next;
            }else {
                cur.next=head2;
                head2= head2.next;
            }
            cur= cur.next;
        }
        cur.next=head1!=null?head1:head2;
        return head;
    }


}
