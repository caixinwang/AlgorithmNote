package BasicLearning.Class03_LinkedList;

import java.util.Stack;

public class Code04_IsPalindromeList {//判断一个链表是不是回文序列

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    private static boolean isPalindrome1(Node head){//暴力解法。利用栈逆序
        Stack<Node> stack=new Stack<>();
        Node temp=head;
        while(temp!=null){//压栈实现逆序
            stack.push(temp);
            temp= temp.next;
        }
        while(!stack.isEmpty()){//从栈中弹出比对
            if (stack.pop().value!=head.value)
                return false;
            head= head.next;
        }
        return true;
    }

    private static boolean isPalindrome2(Node head){
        if (head==null||head.next==null)// 确定至少有两个元素
            return true;
        Node n1=head.next;//定制快慢指针返回中和中右(2,2)
        Node n2=head.next;
        while(n2.next!=null&&n2.next.next!=null){//返回中和右中
            n2=n2.next.next;
            n1=n1.next;
        }
        //出循环，现在n1在对称轴右边
        Stack<Node>stack=new Stack<>();
        while(n1!=null){
            stack.push(n1);
            n1=n1.next;
        }
        while(!stack.isEmpty()){
            if (stack.pop().value!=head.value)
                return false;
            head=head.next;
        }
        return true;
    }

    private static boolean isPalindrome3(Node head){
        if (head==null||head.next==null)//0~1个结点直接返回true
            return true;
        Node fast=head.next;
        Node slow= head.next;//返回中和中右(2,2)
        while(fast.next!=null&&fast.next.next!=null){
            fast=fast.next.next;
            slow=slow.next;
        }
        Node tail=reverse(slow);//从尾巴出发，副作用为翻转slow以后的链表
        Node tail2=tail;
        Node h=head;//从头出发
        boolean res=true;
        while(tail!=null){//从n1这里开始因为偶数的时候右边比较短
            if (tail.value!= h.value){
                res= false;
                break;
            }
            tail=tail.next;
            h=h.next;
        }
        //恢复链表
        reverse(tail2);
        return res;
    }

    /**
     * 翻转以head为头节点的链表，并最终返回新的链表的头节点
     * @param head
     * @return
     */
    private static Node reverse(Node head){
        if (head==null||head.next==null) return head;
        Node pre=null,next=null;
        while(head!=null){
            next=head.next;
            head.next=pre;
            pre=head;
            head=next;
        }
        return pre;
    }


    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {

        Node head = null;
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(2);
        head.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(2);
        head.next.next.next.next = new Node(1);
        printLinkedList(head);
        System.out.print(isPalindrome1(head) + " | ");
        System.out.print(isPalindrome2(head) + " | ");
        System.out.println(isPalindrome3(head) + " | ");
        printLinkedList(head);
        System.out.println("=========================");

    }
}
