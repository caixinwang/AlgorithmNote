package Leetcode.AllQuestions;

public class Leetcode_0024_SwapNodesInPairs {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static ListNode swapPairs(ListNode head) {
        ListNode cur=head;
        ListNode[] reverse = reverse(cur,2);
        ListNode newHead=reverse[0];
        ListNode nextCur=reverse[1];
        ListNode preTail=cur;
        head=newHead;
        cur=nextCur;
        while(cur!=null){
            reverse = reverse(cur,2);
            newHead=reverse[0];
            nextCur=reverse[1];
            preTail.next=newHead;
            preTail=cur;
            cur=nextCur;
        }
        return head;
    }

    /**
     * 1->2->3->4 如果3个一组，那么返回[3,4]。如果4个一组返回[4,null]。
     * @param head 将以head为头的链表的前k个reverse,把新头和原本的第k+1个结点返回，
     * @param k 无论满不满k个都reverse
     * @return 返回reverse之后的新头
     */
    public static ListNode[] reverse(ListNode head ,int k){
        ListNode next=null,pre=null;
        while(k!=0&&head!=null){//1->2->3->4,如果k为3，那么head最终将停在4,pre在3。因为k偏移了3。
            next=head.next;
            head.next=pre;
            pre=head;
            head=next;
            k--;
        }
        return new ListNode[]{pre,head};//reverse之后，pre->...->cur |  head
    }

    public static void test(){
        ListNode listNode=new ListNode(1);
        listNode.next=new ListNode(2);
        listNode.next.next=new ListNode(3);
        listNode.next.next.next=new ListNode(4);
        listNode.next.next.next.next=new ListNode(5);
        ListNode[] reverse = reverse(listNode, 3);
        ListNode cur=reverse[0];
        ListNode cur2=reverse[1];
        System.out.printf("cur:");
        while(cur!=null){
            System.out.printf("%s ",cur.val);
            cur=cur.next;
        }
        System.out.println();
        System.out.println("==========");
        System.out.printf("cur2:");
        while(cur2!=null){
            System.out.printf("%s ",cur2.val);
            cur2=cur2.next;
        }
    }

    public static void test2(){
        ListNode listNode=new ListNode(1);
        listNode.next=new ListNode(2);
        listNode.next.next=new ListNode(3);
        listNode.next.next.next=new ListNode(4);
        listNode.next.next.next.next=new ListNode(5);
        ListNode cur = swapPairs(listNode);
        System.out.printf("cur:");
        while(cur!=null){
            System.out.printf("%s ",cur.val);
            cur=cur.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        test2();
    }


}
