package Leetcode.AllQuestions;
public class Leetcode_0025_ReverseNodesInKGroup {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * 技巧，先手动搞一次，再
     * @param head 头
     * @param k k个一组reverse
     * @return 返回新头
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode cur=head;
        ListNode[] reverse = reverse(cur,k);
        ListNode newHead=reverse[0];
        ListNode nextCur=reverse[1];
        ListNode preTail=cur;
        head=newHead;
        cur=nextCur;
        while(cur!=null){
            reverse = reverse(cur,k);
            newHead=reverse[0];
            nextCur=reverse[1];
            preTail.next=newHead;
            preTail=cur;
            cur=nextCur;
        }
        return head;
    }

    /**
     * 不单独处理一次的写法。一般来说如果需要一组一组操作链表，需要考虑链表对头的影响，以及第一组的处理和最后一组的处理是不一样的。
     * 所以最好是单独处理一次。如果逻辑复杂就单独处理一次
     */
    public ListNode reverseKGroup2(ListNode head, int k) {
        ListNode cur=head, newHead=null,nextCur=null,preTail=null;
        boolean headChanged=false;
        while(cur!=null){
            ListNode[] reverse = reverse(cur,k);
            newHead=reverse[0];
            nextCur=reverse[1];
            if (!headChanged){
                head=newHead;
                headChanged=true;
            }else {
                preTail.next=newHead;
            }
            preTail=cur;
            cur=nextCur;
        }
        return head;
    }


    /**
     * 1->2->3->4 如果3个一组，那么返回[3,4]。如果4个一组返回[4,null]。
     * @param head 将以head为头的链表的前k个reverse,把新头和原本的第k+1个结点返回，
     * @param k 不满k个就不reverse了
     * @return 返回reverse之后的新头，以及后面一组的头
     */
    public static ListNode[] reverse(ListNode head ,int k){
        ListNode next=null,pre=null;
        int n=k;
        for (ListNode cur=head;n!=0&&cur!=null;n--,cur=cur.next);
        if (n>0) return new ListNode[]{head,null};//不够k个就不reverse，原路返回
        while(k!=0){//1->2->3->4,如果k为3，那么head最终将停在4,pre在3。因为k偏移了3。
            next=head.next;
            head.next=pre;
            pre=head;
            head=next;
            k--;
        }
        return new ListNode[]{pre,head};//reverse之后，pre->...->cur |  head
    }


    //哨兵！这种方法很妙
    public ListNode reverseKGroup3(ListNode head, int k) {
        ListNode dummy=new ListNode(0,head),p0,pre,cur,next;//P0为上一组的最后一个
        int n=0;
        for (cur=head;cur!=null;n++,cur=cur.next);//统计结点个数
        for (p0=dummy,cur=p0.next,next=pre=null;n>=k;n-=k){//个数>=k才去翻转
            for (int i = 0; i < k; i++) {//k个一组翻转
                next=cur.next;
                cur.next=pre;
                pre=cur;
                cur=next;
            }
            next=p0.next;
            p0.next=pre;
            p0=next;
            p0.next=cur;
        }
        return dummy.next;
    }
}
