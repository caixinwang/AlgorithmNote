package BasicLearning.Class03_LinkedList;

public class Code01_ReverseList {//反转链表

    private static class Node {//单向链表
        public int value;
        public Node next;

        public Node(int data) {
            value = data;
        }
    }

    /**
     * 翻转单链表
     * @param head:单链表的头节点。head为空，只有一个结点，全部都包含下处理逻辑中，不需要单独处理。
     */
    private static Node reverseList(Node head){
        Node pre=null,next=null;
        while(head!=null){//从第一个结点遍历到最后一个结点
            next=head.next;//保存下一个结点，以便于next迭代
            head.next=pre;//改变当前结点的next指针的指向，指向前一个结点pre.
            pre=head;//当前结点head在一次迭代之后就变成了pre。pre一开始是null
            head=next;//head迭代
        }
        return pre;//head迭代成null之后pre正好指向的就是原来链表最后一个结点，也就是现在的头节点
    }

    private static class DoubleNode {//双向链表结点
        public int value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(int data) {
            value = data;
        }
    }

    /**
     * 翻转双向链表
     * @param head :双向链表的头节点
     */
    private static DoubleNode reverseList(DoubleNode head){
        DoubleNode pre=null,next=null;//两个辅助指针，分别保存下一个结点和前一个结点
        while(head!=null){//从第一个结点遍历到最后一个结点
            next=head.next;//保存下一个结点，以便于next迭代
            //翻转当前结点的两个指针
            head.next=pre;
            head.last=next;

            pre=head;//迭代pre
            head=next;//迭代head
        }
        return pre;
    }

    private static void printLinkedList(Node head){
        System.out.print("LinkedList: ");
        while(head!=null){
            System.out.print(head.value+" ");
            head=head.next;
        }
        System.out.println();
    }

    private  static void printDoubleLinkedList(DoubleNode head){
        System.out.print("Double LinkedList: ");
        DoubleNode end=null;
        while(head!=null){//退出这个while的时候head为空，end为尾结点
            System.out.print(head.value+" ");
            end=head;
            head=head.next;
        }
        System.out.print("| ");
        while(end!=null){
            System.out.print(end.value+" ");
            end=end.last;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        printLinkedList(head1);
        head1 = reverseList(head1);
        printLinkedList(head1);

        DoubleNode head2 = new DoubleNode(1);
        head2.next = new DoubleNode(2);
        head2.next.last = head2;
        head2.next.next = new DoubleNode(3);
        head2.next.next.last = head2.next;
        head2.next.next.next = new DoubleNode(4);
        head2.next.next.next.last = head2.next.next;
        printDoubleLinkedList(head2);
        printDoubleLinkedList(reverseList(head2));

    }
}
