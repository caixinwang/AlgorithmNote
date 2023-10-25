package BasicLearning.Class03_LinkedList;

/**
 * 给定两个链表的头节点head1和head2,认为从左到右是某个数字从低位到高位，返回相加之后的链表。
 * 例如：1549：9->4->5->1
 */
public class Code10_AddTwoNumbers {
    public static class ListNode {
        public int val;
        public ListNode next;
        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

    }

    public static ListNode addTwoNumbers(ListNode head1, ListNode head2) {
        if (head1==null||head2==null) return head1==null?head2:head1;
        int len1 = listLength(head1);
        int len2 = listLength(head2);
        ListNode longList = len1 >= len2 ? head1 : head2;//longList为head1和head2中比较长的那个
        ListNode shortList = longList == head1 ? head2 : head1;//shortList为另外一个比较短的
        ListNode head = longList;//返回它
        ListNode longPre = longList;//指向长链表指针的前一个位置，最后可能因为进位需要创建一个新的结点

        int carry = 0;
        int sum = 0;
        while (shortList != null) {
            sum = shortList.val + longList.val + carry;
            longList.val = sum % 10;
            carry = sum / 10;
            longPre = longList;
            longList = longList.next;
            shortList = shortList.next;
        }

        while (longList != null) {
            sum = longList.val + carry;
            longList.val = sum % 10;
            carry = sum / 10;
            longPre = longList;
            longList = longList.next;
        }

        if (carry != 0) {
            longPre.next = new ListNode(1);
        }

        return head;
    }

    // 求链表长度
    public static int listLength(ListNode head) {
        int len = 0;
        while (head != null) {
            len++;
            head = head.next;
        }
        return len;
    }

    public static ListNode addTwoNumbers2(ListNode head1, ListNode head2) {
        if (head1==null||head2==null) return head1==null?head2:head1;
        int subtract=0,carry=0,sum=0;
        ListNode p1=head1,p2=head2,res=null,pre=null;
        for (;p1!=null;p1=p1.next) subtract++;
        for (;p2!=null;p2=p2.next) subtract--;
        p1=subtract>=0?head1:head2;
        p2=p1==head1?head2:head1;
        res=p1;//res为长链
        for (;p2!=null;pre=p1,p1=p1.next,p2=p2.next){
            sum=p1.val+p2.val+carry;
            carry=sum/10;
            p1.val=sum%10;
        }
        for (;p1!=null;pre=p1,p1=p1.next){
            sum=p1.val+carry;
            carry=sum/10;
            p1.val=sum-carry*10;
        }
        if (carry>0) pre.next=new ListNode(1);
        return res;
    }

}
