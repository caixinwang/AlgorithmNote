package BasicLearning.Class03_LinkedList;

import java.util.HashMap;

public class Code06_CopyListWithRandom {//复制一个Node带有Random域的单链表

    public static class Node {
        public int value;
        public Node next;
        public Node rand;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 思路：遍历一遍head链表可以顺便复制出结点。利用map来建立游离结点和链表中结点和对应关系，map就是一座桥。
     * @param head 要复制的链表的头节点
     * @return 返回复制成功之后的链表的头节点
     */
    public static Node copyListWithRand1(Node head){
        Node p=head;
        HashMap<Node,Node> map=new HashMap<>();
        while(p!=null){
            map.put(p,new Node(p.value));
            p=p.next;
        }
        p=head;
        Node res=map.get(head);
        while(p!=null){
            map.get(p).next=map.get(p.next);
            map.get(p).rand=map.get(p.rand);
            p=p.next;
        }
        return res;
    }

    public static Node copyListWithRand2(Node head){
        if (head==null) return null;
        Node p=head;
        while(p!=null){//新节点串在老结点后面，形成一个2N长度的新串
            Node next=p.next;
            p.next=new Node(p.value);
            p.next.next=next;
            p=next;
        }
        p=head;
        while(p!=null){//处理新节点的random域
            p.next.rand=p.rand!=null?p.rand.next:null;//注意.next之前判断是否为空
            p=p.next.next;
        }
        p=head;
        Node res=p.next;
        while(p!=null){//分离新老结点，拆分出两个链表
            Node next=p.next.next;
            p.next.next=next!=null?next.next:null;//注意判空
            p.next=next;
            p=next;
        }
        return res;
    }

    public static void printRandLinkedList(Node head) {
        Node cur = head;
        System.out.print("order: ");
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
        cur = head;
        System.out.print("rand:  ");
        while (cur != null) {
            System.out.print(cur.rand == null ? "- " : cur.rand.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = null;
        Node res1 = null;
        Node res2 = null;
        printRandLinkedList(head);
        res1 = copyListWithRand1(head);
        printRandLinkedList(res1);
        res2 = copyListWithRand2(head);
        printRandLinkedList(res2);
        printRandLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        head.rand = head.next.next.next.next.next; // 1 -> 6
        head.next.rand = head.next.next.next.next.next; // 2 -> 6
        head.next.next.rand = head.next.next.next.next; // 3 -> 5
        head.next.next.next.rand = head.next.next; // 4 -> 3
        head.next.next.next.next.rand = null; // 5 -> null
        head.next.next.next.next.next.rand = head.next.next.next; // 6 -> 4

        printRandLinkedList(head);
        res1 = copyListWithRand1(head);
        printRandLinkedList(res1);
        res2 = copyListWithRand2(head);
        printRandLinkedList(res2);
        printRandLinkedList(head);
        System.out.println("=========================");

    }
}
