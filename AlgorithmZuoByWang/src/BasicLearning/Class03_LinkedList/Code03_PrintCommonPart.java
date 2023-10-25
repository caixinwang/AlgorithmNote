package BasicLearning.Class03_LinkedList;

public class Code03_PrintCommonPart {//打印两个链表的相同值

    public static class Node {
        public int value;
        public Node next;
        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 打印两个链表的相同值
     * @param head1：链表1的头节点
     * @param head2：链表2的头节点
     */
    private static void printCommonPart(Node head1,Node head2){
        System.out.print("common part is ");
        while(head1!=null&&head2!=null){
            if (head1.value<head2.value)
                head1= head1.next;//谁小谁动
            else if (head2.value< head1.value)
                head2= head2.next;//谁小谁动
            else {//相等一起动
                System.out.print(head1.value+" ");
                head1= head1.next;
                head2= head2.next;
            }
        }
        System.out.println();
    }

    private static void printLinkedList(Node head){
        System.out.print("Linked List is ");
        while(head!=null){
            System.out.print(head.value+" ");
            head= head.next;
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Node node1 = new Node(2);
        node1.next = new Node(3);
        node1.next.next = new Node(5);
        node1.next.next.next = new Node(6);

        Node node2 = new Node(1);
        node2.next = new Node(2);
        node2.next.next = new Node(5);
        node2.next.next.next = new Node(7);
        node2.next.next.next.next = new Node(8);

        printLinkedList(node1);
        printLinkedList(node2);
        printCommonPart(node1, node2);

    }
}
