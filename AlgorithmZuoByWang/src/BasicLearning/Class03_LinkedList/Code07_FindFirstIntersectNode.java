package BasicLearning.Class03_LinkedList;

public class Code07_FindFirstIntersectNode {//找到两个链表第一个相交的结点,链表可能是循环链表

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 传进来两个链表要判断他们两个是否相交，由于这两个链表可能是循环链表也可能不是。所以我们要写一个函数来判断
     * 他们的类型。由于无环链表只能和无环链表相交，循环链表只能和循环链表相交，其中无环链表和循环链表不可能相交。
     * 所以我们这里就分成两种情况就行。
     * @param head1:链表1的头节点
     * @param head2:链表2的头节点
     * @return :
     */
    public static Node getIntersectNode(Node head1, Node head2) {
        Node in1=getLoopNode(head1);//入环结点1
        Node in2=getLoopNode(head2);//入环结点2
        if (in1==null&&in2==null){
            return noLoop(head1,head2);
        }else if (in1!=null&&in2!=null){
            return bothLoop(head1,in1,head2,in2);
        }else {
            return null;
        }
    }

    /**
     * @param head:判断以head为头的链表是不是循环链表
     * @return :是循环链表返回第一个入环结点，否则返回null
     */
    public static Node getLoopNode(Node head) {
        if (head==null||head.next==null){//可能有单节点自己循环,head.next==head
            return null;
        }
        Node fast=head.next.next;//快指针。这里不能设置成head，因为有判断fast!=slow
        Node slow=head.next;//慢指针。
        while(fast.next!=null&&fast.next.next!=null&&fast!=slow){//非循环链表走前两个条件，循环链表走最后一个条件
            fast=fast.next.next;
            slow=slow.next;
        }
        if (fast==slow){//是循环链表
            fast=head;//快指针从头开始
            while(fast!=slow){//快慢指针一人走一步，相遇的结点就是第一个入环结点
                fast=fast.next;
                slow=slow.next;
            }
            return fast;
        }else{//不是循环链表
            return null;
        }
    }

    /**
     * @param head1:链表1，不是循环链表
     * @param head2:链表2，不是循环链表
     * @return :如果两个都是非循环链表，如果相交返回相交结点，如果不相交，返回null
     */
    public static Node noLoop(Node head1, Node head2) {
        if (head1==null||head2==null) return null;
        Node p1=head1,p2=head2;
        int subtract=0;//统计相差的节点数
        while(p1.next!=null) {
            p1 = p1.next;
            subtract++;
        }
        while(p2.next!=null) {
            p2 = p2.next;
            subtract--;
        }
        if (p1==p2){
            int step=Math.abs(subtract);
            p1=subtract>0?head1:head2;//p1此时指向结点个数多的链表
            p2=p1==head1?head2:head1;
            while(step--!=0) p1=p1.next;
            while(p1!=p2){
                p1=p1.next;
                p2=p2.next;
            }
            return p1;
        }else {
            return null;
        }
    }

    /**
     *
     * @param head1:链表1的头节点，是循环链表
     * @param loop1:链表1的第一个入环结点
     * @param head2:链表2的头节点，是循环链表
     * @param loop2:链表2的第一个入环结点
     * @return ：如果两链表有相交则返回相交结点，否则返回null。
     */
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node p1=loop1;
        Node p2=loop2;
        if (p1==p2){//转化成两个单链的情况
            p2=p1.next;//记住p1的后继结点，方便后面复原
            p1.next=null;//把循环的部分拆掉最后再复原
            Node res=noLoop(head1,head2);
            p1.next=p2;//恢复后面的循环部分
            return res;
        }else{
            p1=loop1.next;
            while(p1!=loop1&&p1!=loop2){
                p1=p1.next;
            }
            if (p1==loop2){//说明相交了
                return loop1;
            }else{//说明没有相交
                return null;
            }
        }

    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

    }
}
