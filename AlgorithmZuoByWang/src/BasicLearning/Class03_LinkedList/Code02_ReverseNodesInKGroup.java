package BasicLearning.Class03_LinkedList;
//测试链接：https://leetcode.com/problems/reverse-nodes-in-k-group/

/**
 * 题目：链表分组，每组k个节点，组内逆序，不够k个的不管。
 * 也就是1->2->3->4->5->6->7->8->9->10，如果k为3，结果为3->2->1->6->5->4->9->8->7->10。
 * 可以看到，这个函数一定是一个换头函数。因为头变了，变成了3。
 */
public class Code02_ReverseNodesInKGroup {
    // 不要提交这个类
    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int value){
            this.val=value;
            this.next=null;
        }
    }

    public static ListNode reverseKGroup(ListNode head, int k) {//是一个换头函数
        ListNode resHead=null,p1=head,p2=null,p3=null;
        p2=countK(p1,k);//看看第一组是否满k个
        if (p2==null) return p1;//不满k个直接返回
        resHead=p2;//第一组满k个，头节点应该换成第一组的最后一个结点，也就是此时的p2
        while(true){
            p3=p2.next;//隔壁组的第一个---下一次的p1
            ListNode nextGroupTail = countK(p3, k);//隔壁组的最后一个结点---下一次的p2
            reverse(p1,p2);
            if (nextGroupTail==null) break;//是否成组---满k个。不满足k个的情况特殊处理，break
            p1.next=nextGroupTail;
            p1=p3;
            p2=nextGroupTail;
        }
        p1.next=p3;//最后一组做特殊处理，通过break出来
        return resHead;
    }

    /**
     * @param start：开始结点
     * @param k：第k个节点
     * @return ：这个函数的功能就是从start结点开始（第一个结点），返回第k个节点。如果不足k个则返回空。
     */
    public static ListNode countK(ListNode start, int k) {
        while(start!=null&&k!=1){//走了k-1步
            start= start.next;
            k--;
        }
        return start;
    }

    public static void reverse(ListNode start, ListNode end) {
        ListNode pre=end.next;//让翻转之后的最后一个结点start指向原本end结点的下一个接地那
        end=end.next;//作为终止条件
        ListNode next=null;//作为中间变量，方便迭代
        while(start!=end){
            next=start.next;
            start.next=pre;
            pre=start;
            start=next;
        }
    }


    //for test
    public static ListNode generateNLenList(int n){//>=1
        ListNode head=new ListNode(1),p=head;
        for (int i = 2; i <= n; i++) {
            p.next=new ListNode(i);
            p=p.next;
        }
        return head;
    }

    //for Test
    private static void printLinkedList(ListNode head){
        System.out.print("LinkedList: ");
        while(head!=null){
            System.out.print(head.val+" ");
            head=head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ListNode head = generateNLenList(22);
        printLinkedList(head);
        head= reverseKGroup(head, 4);
        printLinkedList(head);
    }
}
